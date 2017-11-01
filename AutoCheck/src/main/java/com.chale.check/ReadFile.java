package com.chale.check;

import java.io.*;

/**
 * Created by liangchaolei on 2017/5/19.
 */
public class ReadFile {

    public static void main(String[] args) throws FileNotFoundException {
        readTxtFile("E:\\psa测试环境\\offline\\offline\\1-mv\\mv_to_work_dir.sh");
    }


    public static void readTxtFile(String filePath){
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
