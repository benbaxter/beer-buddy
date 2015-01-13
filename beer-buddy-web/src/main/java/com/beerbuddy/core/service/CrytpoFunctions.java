package com.beerbuddy.core.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;


public interface CrytpoFunctions {

	public default String encryptPassword(String password, String salt) {
	    //the next two lines will work if we upgrade java's security policy
//		TextEncryptor encryptor = Encryptors.text(SECRET_KEY, salt);
//		return encryptor.encrypt(password);
		return encrypt(password, generateSecretKeyWithSalt(salt));
	}

	public default SecretKeySpec generateSecretKeyWithSalt(String salt) {
		MessageDigest sha = null;
		byte[] key;
		try {
			key = salt.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			return new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public default String encrypt(String strToEncrypt, SecretKeySpec secretKey) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return new String(Base64.encode(cipher.doFinal(strToEncrypt
					.getBytes("UTF-8"))));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}
}
