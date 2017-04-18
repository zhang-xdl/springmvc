package com.redis.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class StringUtil {
    /**
     * 判断字符串是否为空，空返回true，
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str){

        if(str == null || "".equals(str.trim()) || "null".equals(str.trim())){
            return true;
        }
        return false;
    }

    /**
     * 判断两个字符串的值是否相等
     * @param str
     * @param compareStr
     * @return
     */
    public static Boolean isEqual(String str,String compareStr){
        if(isEmpty(str) == true && isEmpty(compareStr) == true){
            return true;
        }
        if(str.trim().equals(compareStr.trim())){
            return true;
        }
        return false;
    }

    /**
     * String To BigDecimal( if that fails,return 0)
     * @param strBigDecimal
     * @return
     */
    public static BigDecimal toBigDecimal(String strBigDecimal){

        if(isEmpty(strBigDecimal)){
            return new BigDecimal(0);
        }

        BigDecimal bigDecimal = new BigDecimal(0);

        try {
            bigDecimal = new BigDecimal(strBigDecimal);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return bigDecimal;
    }

    /**
     * String To BigDecimal( if that fails,return 0)
     * @param strBigDecimal
     * @return
     */
    public static BigDecimal toBigDecimal(String strBigDecimal,int scale){

        if(isEmpty(strBigDecimal)){
            return new BigDecimal(0);
        }

        BigDecimal bigDecimal = new BigDecimal(0);

        try {
            bigDecimal = new BigDecimal(strBigDecimal).setScale(2, RoundingMode.HALF_UP);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return bigDecimal;
    }

    /**
     * String to int (if that fails,return 0)
     * @param strInt
     * @return
     */
    public static int toInt(String strInt){

        if(isEmpty(strInt)){
            return 0;
        }

        int returnInt = 0 ;

        try {
            returnInt = new Integer(strInt);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return returnInt;
    }

    /**
     * String to InputStream
     * @param strInputStream
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream toInputStream(String strInputStream) throws UnsupportedEncodingException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(strInputStream.getBytes("ISO-8859-1"));
        return byteArrayInputStream;
    }

    /**
     * 替换商品描述中的图片路径
     * @param desc 商品描述
     * @param path 图片路径
     * @return 新商品描述
     */
    public static String descReplace(String desc,String path){

        String imgStrArray[] = desc.split("<img");
        for (String imgstr : imgStrArray){
            if(imgstr.contains("src") && imgstr.contains("title")){
                int firstIndex = imgstr.indexOf("\"", 1);
                int secondIndex = imgstr.indexOf("\"", firstIndex + 2);
                String oldStr = imgstr.substring(firstIndex + 1, secondIndex);
                //取titile = picName
                int first = imgstr.indexOf("\"",secondIndex + 2);
                int second = imgstr.indexOf("\"", first + 2);
                String picName = imgstr.substring(first + 1, second);
                desc = desc.replace(oldStr,path.concat(picName));
            }
        }
        return desc;
    }
}
