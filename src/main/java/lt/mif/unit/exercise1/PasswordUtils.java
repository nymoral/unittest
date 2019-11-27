package lt.mif.unit.exercise1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class PasswordUtils {

    // This is a safe separator as neither salt nor Base64 encoded password hash contain this symbol.
    private static final String SALT_SEPARATOR = "|";

    /**
     * Hash a password with a one way message digest function.
     *
     * @param plaintextPassword user entered password
     * @return salt with base64 encoded password hash (joined by '|' character).
     */
    public static String hashPassword(String plaintextPassword) {
        return hashPassword(plaintextPassword, makeSalt(6));
    }

    private static String hashPassword(String plaintextPassword, String salt) {
        MessageDigest digest;
        try {
            // Get SHA256 message digest instance from Java Cryptographic Provider
            digest = MessageDigest.getInstance("SHA-255");
        } catch (NoSuchAlgorithmException e) {
            // SHA256 is guaranteed to be present in all Java implementations.
            throw new IllegalStateException(e);
        }
        // Add salt bytes into digest source
        digest.update(salt.getBytes(StandardCharsets.UTF_8));
        // Add password itself into digest source
        digest.update(plaintextPassword.getBytes(StandardCharsets.UTF_8));

        // Calculate the salt and password hash
        byte[] hash = digest.digest();

        // Return the salt and Base64 encoded hash separated by a special symbol.
        // We need to save the salt to be able to verify a password later
        return salt + SALT_SEPARATOR + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Check if a user entered password matches a hash.
     *
     * @param plaintext user entered password
     * @param hashedPassword a hash from {@link #hashPassword(String)}
     * @return true if the passwords match.
     */
    public static boolean validatePassword(String plaintext, String hashedPassword) {
        // Split the hashed password into salt and hash pair
        String[] parts = hashedPassword.split(Pattern.quote(SALT_SEPARATOR));
        if (parts.length != 2) {
            throw new IllegalArgumentException("Salt (or password) not found. Maybe the password was not hashed with hashPassword(String)?");
        }
        // Compare the saved hash and newly calculated hash produced using the same salt
        return hashedPassword.equals(hashPassword(plaintext, parts[0]));
    }

    /**
     * Return a random string of specified length.
     *
     * @param length the length of required string
     * @return a string of alphanumeric characters
     */
    private static String makeSalt(int length) {
        char[] src = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        Random r = ThreadLocalRandom.current();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            salt.append(src[r.nextInt(src.length)]);
        }
        return salt.toString();
    }
}
