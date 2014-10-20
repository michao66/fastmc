package cn.fastmc.core;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

public class RSAUtils {
	 private static final Provider provider = new BouncyCastleProvider();
	  private static final int KEY_SIZE = 1024;
	  public static KeyPair generateKeyPair()
	  {
	    try
	    {
	      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", provider);
	      keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
	      return keyPairGenerator.generateKeyPair();
	    }
	    catch (NoSuchAlgorithmException ex)
	    {
	    	ex.printStackTrace();
	    }
	    return null;
	  }

	  public static byte[] encrypt(PublicKey publicKey, byte[] data)
	  {
	    Assert.notNull(publicKey);
	    Assert.notNull(data);
	    try
	    {
	      Cipher cipher = Cipher.getInstance("RSA", provider);
	      cipher.init(1, publicKey);
	      return cipher.doFinal(data);
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    return null;
	  }

	  public static String encrypt(PublicKey publicKey, String text)
	  {
	    Assert.notNull(publicKey);
	    Assert.notNull(text);
	    byte[] contents = encrypt(publicKey, text.getBytes());
	    return contents != null ? Base64.encodeBase64String(contents) : null;
	  }

	  public static byte[] decrypt(PrivateKey privateKey, byte[] data)
	  {
	    Assert.notNull(privateKey);
	    Assert.notNull(data);
	    try
	    {
	      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
	      cipher.init(2, privateKey);
	      return cipher.doFinal(data);
	    }
	    catch (Exception localException1)
	    {
	    }
	    return null;
	  }

	  public static String decrypt(PrivateKey privateKey, String text)
	  {
	    Assert.notNull(privateKey);
	    Assert.notNull(text);
	    byte[] contents  = decrypt(privateKey, Base64.decodeBase64(text));
	    return contents  != null ? new String(contents) : null;
	  }
}