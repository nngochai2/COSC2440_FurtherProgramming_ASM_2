package org.nikisurance.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;

public class PasswordHash {
    // Number of iterations for PBKDF2
    private static final int ITERATIONS = 10000; // Ideal number (Increase this makes the hash more secure but slower)

    // Length of the derived key in bits
    private static final int KEY_LENGTH = 256;

    // PBKDF2 algorithm with HMAC using SHA-256
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Method to hash a password with PBKDF2
     * @param password the password to hash
     * @param salt the salt used in hashing
     * @return hashed password
     * @throws NoSuchAlgorithmException if the specified hashing algorithm is not available
     * @throws InvalidKeySpecException if the provided key specification is inappropriate for this algorithm
     */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Convert the password and salt to character and byte arrays
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        // Create a PBEKeySpec object with the password, salt, iterations, and key length
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);

        // Get the SecretKeyFactory instance for PBKDF2
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

        // Generate a secret key from the PBEKeySpec
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Encode the hash as a Base64 string and return it
        return Base64.getEncoder().encodeToString(hash);
    }

    // Test the hashing method
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String salt = "randomSalt123";
            System.out.print("Enter password (enter 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                System.out.println("Hashed password:" + hashPassword(input, salt));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                System.out.println("Error hashing password: " + e.getMessage());
            }

        }
    }
}


