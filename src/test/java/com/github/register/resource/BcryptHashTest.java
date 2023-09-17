package com.github.register.resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author sniper
 * @date 17 Sep 2023
 */
public class BcryptHashTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            testHashPW();
            testHashPWV1();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void testHashPWV1() {
        String password = "123456";
        String encodedPassword = hashPasswordV1(password);
        //BCrypt hash: $2a$10$VyQ57jmIB/JnGQS1lZhhwuhB10aGION7IisFRAkSnHq5QHWh13Mre
        System.out.println("BCrypt hash: " + encodedPassword);
    }

    public static String hashPasswordV1(String password) {
        // Create a new BCryptPasswordEncoder.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Encode the password.
        String encodedPassword = encoder.encode(password);

        return encodedPassword;
    }

    public static void testHashPW() throws NoSuchAlgorithmException {
        String password = "123456";
        String salt = "$2a$10$o5L.dWYEjZjaejOmN3x4Qu";

        String bcryptHash = hashPassword(password, salt);

        System.out.println("BCrypt hash: " + bcryptHash);
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        // Generate the MD5 hash of the password.
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Hash = md5.digest(password.getBytes());

        // Concatenate the MD5 hash with the salt.
        byte[] saltedPassword = new byte[md5Hash.length + salt.getBytes().length];
        System.arraycopy(md5Hash, 0, saltedPassword, 0, md5Hash.length);
        System.arraycopy(salt.getBytes(), 0, saltedPassword, md5Hash.length, salt.getBytes().length);

        // Generate the BCrypt hash of the salted password.
        String bcryptHash = BCrypt.hashpw(new String(saltedPassword), BCrypt.gensalt(10));
        return bcryptHash;
    }
}
