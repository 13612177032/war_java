package com.chale.java.base;

/**
 * Created by liangchaolei on 2016/6/22.
 */
public class StringReduce {
    private static StringReduce ourInstance = new StringReduce();

    public static StringReduce getInstance() {
        return ourInstance;
    }

    private StringReduce() {
    }

    public static String tempString = "{\n" +
            "\t\" eventList \" : [{\n" +
            "\t\t\t\" createTime \" : \" 2016 - 06 - 21 10 : 42 : 15 \",\n" +
            "\t\t\t\" eventId \" : \" 下一步 \"\n" +
            "\t\t}\n" +
            "\t],\n" +
            "\t\" indenfier \" : \" 198058855361466476834586 \",\n" +
            "\t\" appName \" : \" com.jdpay.example.jdpaydemo \",\n" +
            "\t\" appVersion \" : \" 1.3.0 \",\n" +
            "\t\" clientVersion \" : \" 1.4.0 \",\n" +
            "\t\" jdPin \" : \" liufangyua_m \",\n" +
            "\t\" orderNum \" : \" 19805885536 \",\n" +
            "\t\" verifySign \" : \" \",\n" +
            "\t\" clientName \" : \" JDPaySDK \",\n" +
            "\t\" deviceId \" : \" 867905025520620 \",\n" +
            "\t\" deviceName \" : \" PRO 5 \",\n" +
            "\t\" imsi \" : \" \",\n" +
            "\t\" macAddress \" : \" 68 - 3e-34 - 1c - 18 - de \",\n" +
            "\t\" netType \" : \" wifi \",\n" +
            "\t\" osVersion \" : \" 5.1 \",\n" +
            "\t\" protocolVersion \" : \" 1.0.0 \",\n" +
            "\t\" provider \" : \" Android \"\n" +
            "}\n" +
            "\n";


    /**
     * 利用字节码的空间去进行压缩
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("压缩前字符串内容："+tempString);
        System.out.println("压缩前字符串大小:"+tempString.length());
        StringReduce sr = getInstance();
        String resultString = sr.compactString(tempString);
        System.out.println("压缩后字符串内容："+resultString);
        System.out.println("压缩后字符串大小："+resultString.length());

        String convertString = sr.decompressionString(resultString);
        System.out.println("解压后字符串内容："+convertString);
        System.out.println("解压后字符串大小："+convertString.length());
    }

    /**
     * 通过接口compactString()的压缩方式进行解压
     * @param tempString
     * @return
     */
    public  String decompressionString(String tempString){
        char[] tempBytes = tempString.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tempBytes.length; i++) {
            char c = tempBytes[i];
            char firstCharacter = (char) (c >>> 8);
            char secondCharacter = (char) ((byte)c);
            sb.append(firstCharacter);
            if(secondCharacter != 0)
                sb.append(secondCharacter);
        }
        return sb.toString();
    }


    /**
     * 对需要进行压缩的字符串进行压缩，返回一个相对较小的字符串
     * @param tempString
     * @return
     */
    public  String compactString(String tempString) {
        StringBuffer sb = new StringBuffer();
        byte[] tempBytes = tempString.getBytes();
        for (int i = 0; i < tempBytes.length; i+=2) {
            char firstCharacter = (char)tempBytes[i];
            char secondCharacter = 0;
            if(i+1<tempBytes.length)
                secondCharacter = (char)tempBytes[i+1];
            firstCharacter <<= 8;
            sb.append((char)(firstCharacter+secondCharacter));
        }
        return sb.toString();
    }
    }
