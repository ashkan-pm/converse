package tech.aspm.converse.services;

import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;
import java.util.Random;

import javax.crypto.spec.DHParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.aspm.converse.controllers.ChatController;
import tech.aspm.converse.helpers.CryptoHelper;
import tech.aspm.converse.models.Message;

@Service
public class SecretService {
  private BigInteger generator;
  private BigInteger prime;
  private BigInteger publicKey;
  private BigInteger privateKey;
  private BigInteger secret;

  @Autowired
  private ChatController chatController;

  public void setGenerator(String generator) {
    this.generator = new BigInteger(generator);
  }

  public void setPrime(String prime) {
    this.prime = new BigInteger(prime);
  }

  public String getGenerator() {
    return generator.toString();
  }

  public String getPrime() {
    return prime.toString();
  }

  public String getPublicKey() {
    return publicKey.toString();
  }

  public SecretService() {
    try {
      Random randomGenerator = new Random();
      privateKey = new BigInteger(1024, randomGenerator);

      AlgorithmParameterGenerator paramGen;
      paramGen = AlgorithmParameterGenerator.getInstance("DH");
      paramGen.init(1024);
      AlgorithmParameters params = paramGen.generateParameters();
      DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);

      generator = dhSpec.getG();
      prime = dhSpec.getP();

      generatePublicKey();
    } catch (NoSuchAlgorithmException | InvalidParameterSpecException e) {
      System.out.println("Could not generate keys");
    }
  }

  public String generatePublicKey() {
    publicKey = generator.modPow(privateKey, prime);

    return publicKey.toString();
  }

  public void calculateSecret(String targetPublicKey) {
    secret = new BigInteger(targetPublicKey).modPow(privateKey, prime);
    // logKeys(targetPublicKey);
  }

  public String encrypt(String message) {
    return CryptoHelper.encrypt(message, secret.toString());
  }

  public String decrypt(String message) {
    return CryptoHelper.decrypt(message, secret.toString());
  }

  @SuppressWarnings("unused")
  private void logKeys(String targetPublicKey) {
    Message generatorMessage = new Message();
    generatorMessage.setUsername("System");
    generatorMessage.setCreatedAt(new Date());
    generatorMessage.setBody("Generator -> " + generator.toString());
    Message primeMessage = new Message();
    primeMessage.setUsername("System");
    primeMessage.setCreatedAt(new Date());
    primeMessage.setBody("Prime -> " + prime.toString());
    Message privateKeyMessage = new Message();
    privateKeyMessage.setUsername("System");
    privateKeyMessage.setCreatedAt(new Date());
    privateKeyMessage.setBody("Private key -> " + privateKey.toString());
    Message publicKeyMessage = new Message();
    publicKeyMessage.setUsername("System");
    publicKeyMessage.setCreatedAt(new Date());
    publicKeyMessage.setBody("Public key -> " + publicKey.toString());
    Message secretMessage = new Message();
    secretMessage.setUsername("System");
    secretMessage.setCreatedAt(new Date());
    secretMessage.setBody("Secret -> " + secret.toString());
    Message targetPublicKeyMessage = new Message();
    targetPublicKeyMessage.setUsername("System");
    targetPublicKeyMessage.setCreatedAt(new Date());
    targetPublicKeyMessage.setBody("Target public key -> " + targetPublicKey);
    chatController.pushMessage(generatorMessage);
    chatController.pushMessage(primeMessage);
    chatController.pushMessage(privateKeyMessage);
    chatController.pushMessage(secretMessage);
    chatController.pushMessage(publicKeyMessage);
    chatController.pushMessage(targetPublicKeyMessage);
  }
}
