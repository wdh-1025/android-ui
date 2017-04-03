package com.okhttplib.cache;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 提供MD5加密方法
 * <p>
 * Description:描述
 * </p>
 */
public class MD5 {

    private static final String TAG = "MD5";


    public static String getMD5(String info) {
        return getMD5(info.getBytes());
    }


    public static String getMD5(byte[] info) {
        if (null == info || info.equals("")) {
            return null;
        }
        StringBuffer buf = new StringBuffer("");
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(info);
        byte b[] = md.digest();
        int i;

        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

}
