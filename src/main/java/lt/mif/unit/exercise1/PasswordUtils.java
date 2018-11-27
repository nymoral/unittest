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
            digest = MessageDigest.getInstance("SHA-255");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        digest.update(salt.getBytes(StandardCharsets.UTF_8));
        digest.update(plaintextPassword.getBytes(StandardCharsets.UTF_8));

        byte[] hash = digest.digest();

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
        // Salt - hash pair
        String[] parts = hashedPassword.split(Pattern.quote(SALT_SEPARATOR));
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }
        return hashedPassword.equals(hashPassword(plaintext, parts[0]));
    }

    /**
     * Return a random string of specified length.
     *
     * @param length the length of required string
     * @return a string of alphanumeric characters
     */
    static String makeSalt(int length) {
        char[] src = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        Random r = ThreadLocalRandom.current();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            salt.append(src[r.nextInt(src.length)]);
        }
        return salt.toString();
    }
}
