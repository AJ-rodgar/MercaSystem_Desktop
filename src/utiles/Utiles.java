package utiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utiles {

    public static String codePassword(String password) throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance("MD5");
	 
        byte[] array = md.digest(password.getBytes());
        StringBuffer hash = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
        	hash.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                    .substring(1, 3));
        }
        
        return hash.toString();
    }
    
}
