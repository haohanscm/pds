package com.haohan.platform.service.sys.modules.thirdplatform.smartpay.apmp2.util;

import org.bouncycastle.util.encoders.Base64;

import javax.management.openmbean.InvalidKeyException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ApmpSecurityUtil {

	public static boolean verify(byte[] data, String sign, String key) {
		boolean result = false;
		PublicKey publicKey = getPublicKey(key);

		Signature sig;
		try {
			sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(publicKey);
			sig.update(data);
			result = sig.verify(CodeUtil.hexString2Byte(sign));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static PublicKey getPublicKey(String publicKeyBase64) {
		byte[] keyBytes = Base64.decode(publicKeyBase64);
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PrivateKey getPrivateKey(String privateKeyBase64) {
		byte[] keyBytes = Base64.decode(privateKeyBase64);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

			return privateKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sign(byte[] data, String privateKeyBase64) throws Exception {
		String signStr = "";
		try {
			PrivateKey privateKey = getPrivateKey(privateKeyBase64);
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(privateKey);
			signature.update(data);
			signStr = CodeUtil.byte2HexString(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signStr;
	}

}
