package common;

import java.security.*;

public class Encrypt {
	public static String MD5(String password) throws NoSuchAlgorithmException {
		String HashedPass;
		// Small snippet to hash the pass so it isnt stored as cleartext in the database. Note that the algorithm used (MD5) is cryptographically weak, but we will implement better encryption in PHASE 2 of the project.
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        HashedPass = sb.toString();
        return HashedPass;
	}
	public static String SHA512(String password) throws NoSuchAlgorithmException{
		String HashedPass;
		MessageDigest sha = MessageDigest.getInstance("SHA-512");
		sha.update(password.getBytes());
		byte[] bytes = sha.digest();
		StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        HashedPass = sb.toString();
        return HashedPass;
	}
	public static String encrypt(String pass) {
		try {
			return SHA512(pass);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	static String generateToken( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	public static String generateToken() {
		return generateToken(100);
	}
}
