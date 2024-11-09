package internals.crypto;

import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import internals.formatters.dataformatter;

public class cryptohandlers {
    private String SECRETHEX = "";
    private String IV = "";

    private SecretKey KEY = new SecretKeySpec(dataformatter.hexStringToByte(SECRETHEX), "AES");
    private IvParameterSpec IVKEY = new IvParameterSpec(dataformatter.hexStringToByte(IV));

    public void generateKey() throws  Exception
    {
        KeyGenerator k = KeyGenerator.getInstance("AES");
        k.init(128);
        byte[] sk = k.generateKey().getEncoded();
        System.out.printf(dataformatter.ByteToHexadecimal(sk));
    }

    // Here String means HexaDecimal as input
    public String encrypt(String plainText) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, KEY, IVKEY);

        System.out.println("\nEncrypting Password");
        byte[] encb = cipher.doFinal(plainText.getBytes());

        return dataformatter.ByteToHexadecimal(encb);
    }   
    
    // Here String means HexaDecimal as input
    public String decrypt(String encryptedText) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, KEY, IVKEY);

        try
        {
            byte[] res = cipher.doFinal(dataformatter.hexStringToByte(encryptedText));
            return new String(res);
        }
        catch (BadPaddingException e)
        {
            System.out.println("\nInvalid Key: The Encryption key provided was not able to decrypt the data.");
            return "ERROR";
        }
    }

    byte[] ivGenerator()
    {
        byte[] iv = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        System.out.println(dataformatter.ByteToHexadecimal(iv));
        return iv;
    }
    public static void main(String[] args) throws Exception
    {
        cryptohandlers cp = new cryptohandlers();

        String txt = cp.encrypt("Magudesh");  
        System.out.println("Encrypted"+txt);
        
        
        String tx = cp.decrypt(txt);
        System.out.printf("Decrypted"+tx);

    }
}