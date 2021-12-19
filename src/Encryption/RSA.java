package Encryption;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private PublicKey pub_k ;
    private PrivateKey prv_k;
    private String publicKey;
    private static final String ALGORITHM = "RSA";
    
	public RSA() {
		super();
		SecureRandom sr = new SecureRandom();
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(ALGORITHM);
			kpg.initialize(2048, sr);

			KeyPair kp = kpg.genKeyPair();
			pub_k = kp.getPublic();
			prv_k = kp.getPrivate();
			publicKey = Base64.getEncoder().encodeToString(pub_k.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public RSA(String publicKey) {
		super();
		byte[] decodedKey = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
		KeyFactory factory;
		try {
			factory = KeyFactory.getInstance(ALGORITHM);
			pub_k = factory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getPublicKey() {
		return publicKey;
	}

	public String encrypt(String msg) {
		try {
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, pub_k);
			return Base64.getEncoder().encodeToString(c.doFinal(msg.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String decrypt(String enc) {
		Cipher c;
		try {
			c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, prv_k);
			byte[] arr = Base64.getDecoder().decode(enc);
			byte decryptOut[] = c.doFinal(arr);
			return new String(decryptOut);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
