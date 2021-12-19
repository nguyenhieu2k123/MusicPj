package Encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private static final String ALGORITHM = "AES";
	private String keyAES;
	private SecretKey secretKey;
	
	public AES() {
		super();
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance(ALGORITHM);
			keyGen.init(256);
			secretKey = keyGen.generateKey();
			keyAES = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public AES(String keyAES) {
		super();
		this.keyAES = keyAES;
		byte[] decodedKey = Base64.getDecoder().decode(keyAES);
		secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM); 
	}

	public String getKeyAES() {
		return keyAES;
	}

	public void setKeyAES(String keyAES) {
		this.keyAES = keyAES;
	}

	public String encrypt(String msg) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        byte[] encrypted = cipher.doFinal(msg.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}   
	}
	
	public String decrypt(String msgEncrypted) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        byte[] encrypted = Base64.getDecoder().decode(msgEncrypted);
	        byte[] decrypted = cipher.doFinal(encrypted);
			return new String(decrypted);
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}   
	}
}
