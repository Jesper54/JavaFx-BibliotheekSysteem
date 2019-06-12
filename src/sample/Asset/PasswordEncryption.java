package sample.Asset;

import java.math.*;
import java.security.*;

public class PasswordEncryption {

    public static String MD5(String Password) {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(Password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
