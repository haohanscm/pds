package com.haohan.platform.service.sys.modules.ldap.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class PasswordUtil {
	
	public static String MD5(String password){
 		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
 		md.update(password.getBytes());
 		byte[] bs = md.digest();
 		byte[] base64MD5Password = Base64.encodeBase64(bs);
 		return "{MD5}"+new String(base64MD5Password);
     }
	
	public static String SSHA(String password) {
		Random rd = new Random();
		StringBuffer salt = new StringBuffer();
		for (int i=1; i<=4; i++){
			salt.append("0123456789abcdef".charAt(rd.nextInt(16)));
		}
		byte[] saltByte = salt.toString().getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
 		md.update(password.getBytes());
 		md.update(saltByte);
 		byte[] bs = md.digest();
 		bs = Arrays.copyOf(bs, bs.length+saltByte.length);
 		System.arraycopy(saltByte,0,bs, 20, saltByte.length);
 		byte[] base64MD5Password = Base64.encodeBase64(bs);
 		return "{SSHA}"+new String(base64MD5Password);
	}
	
	public static String SHA(String password){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
 		md.update(password.getBytes());
 		byte[] bs = md.digest();
 		byte[] base64MD5Password = Base64.encodeBase64(bs);
 		return "{SHA}"+new String(base64MD5Password);
	}
	
	public static boolean verify(String ldappw, String inputpw)  throws NoSuchAlgorithmException {

        // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
        MessageDigest md = null;

        // 取出加密字符
        if(ldappw.startsWith("{MD5}")){
        	md = MessageDigest.getInstance("MD5");
            ldappw = ldappw.substring(5);
        }else if (ldappw.startsWith("{SSHA}")) {
        	md = MessageDigest.getInstance("SHA-1");
            ldappw = ldappw.substring(6);
        } else if (ldappw.startsWith("{SHA}")) {
        	md = MessageDigest.getInstance("SHA-1");
            ldappw = ldappw.substring(5);
        }else{
        	return false;
        }

        // 解码BASE64
        byte[] ldappwbyte = Base64.decodeBase64(ldappw);
        byte[] shacode;
        byte[] salt;

        if(ldappw.startsWith("{MD5}")){
        	shacode = ldappwbyte;
        	salt = new byte[0];
        }else if (ldappwbyte.length <= 20) {  // 前20位是SHA-1加密段，20位后是最初加密时的随机明文
            shacode = ldappwbyte;
            salt = new byte[0];
        } else {
            shacode = new byte[20];
            salt = new byte[ldappwbyte.length - 20];
            System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
            System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
        }

        // 把用户输入的密码添加到摘要计算信息
        md.update(inputpw.getBytes());
        // 把随机明文添加到摘要计算信息
        md.update(salt);

        // 按SSHA把当前用户密码进行计算
        byte[] inputpwbyte = md.digest();

        // 返回校验结果
        return MessageDigest.isEqual(shacode, inputpwbyte);
    }
}
