package tech.aspm.converse.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
  public static String encrypt(String message, String secret) {
    try {
      MessageDigest sha = null;
      byte[] key = secret.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }

    return null;
  }

  public static String decrypt(String message, String secret) {
    try {
      MessageDigest sha = null;
      byte[] key = secret.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);

      return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }

    return null;
  }

  public static BigInteger randomPrime(int numBits) {
    BigInteger max = BigInteger.ONE.shiftLeft(numBits);
    BigInteger prime;
    do {
      BigInteger integer = new BigInteger(numBits, ThreadLocalRandom.current());
      prime = integer.nextProbablePrime();
    } while (prime.compareTo(max) > 0);
    return prime;
  }
}
