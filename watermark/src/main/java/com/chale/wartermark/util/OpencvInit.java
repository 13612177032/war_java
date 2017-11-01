package com.chale.wartermark.util;

import org.opencv.core.Core;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by liangchaolei on 2017/6/20.
 */
public class OpencvInit {

    private static boolean isInit=false;

    private static void addDirToPath(String s){
        try {
            //获取系统path变量对象
            Field field=ClassLoader.class.getDeclaredField("sys_paths");
            //设置此变量对象可访问
            field.setAccessible(true);
            //获取此变量对象的值
            String[] path=(String[])field.get(null);
            //创建字符串数组，在原来的数组长度上增加一个，用于存放增加的目录
            String[] tem=new String[path.length+1];
            //将原来的path变量复制到tem中
            System.arraycopy(path,0,tem,0,path.length);
            //将增加的目录存入新的变量数组中
            tem[path.length]=s;
            //将增加目录后的数组赋给path变量对象
            field.set(null,tem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //"riskArsenalWeb.root"
    public static void init(String path,String fileName){
        try {
            if(isInit) return ;

            System.out.println("######## 路径为 : " + path + " ########");
            //将此目录添加到系统环境变量中
            addDirToPath(path);
            //加载相应的dll文件，注意要将'\'替换为'/'
            System.load(path+ File.separator+fileName);

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("######## Opencv加载完毕 ########");
            isInit=true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkWin() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }
}
