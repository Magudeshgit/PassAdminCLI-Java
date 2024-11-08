package internals.crypto;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import internals.formatters.dataformatter;

public class cryptohandlers {
    private String SECRETHEX = "A0DBA814F5B10A87876C3C57D58E2BC306B3EFF5B2E3D087B356E0CF168EE007";
    private SecretKey KEY = new SecretKeySpec(dataformatter.hexStringToByte(SECRETHEX), "AES");
    public String encrypt(String plainText) throws Exception
    {
        System.out.println("\nEncrypting Password");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        IvParameterSpec ivp = new IvParameterSpec(ivGenerator());
        cipher.init(Cipher.ENCRYPT_MODE, KEY, ivp);
        byte[] encb = cipher.doFinal(plainText.getBytes());
        return dataformatter.ByteToHexadecimal(encb);
    }   

    public String decrypt(byte[] encryptedText) throws Exception
    {
        System.out.println("Decrypting String");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        IvParameterSpec ivp = new IvParameterSpec(ivGenerator());
        cipher.init(Cipher.DECRYPT_MODE, KEY, ivp);
        byte[] res = cipher.doFinal(encryptedText);

        return new String(res);
    }

    byte[] ivGenerator()
    {
        byte[] iv = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        return iv;
    }
}