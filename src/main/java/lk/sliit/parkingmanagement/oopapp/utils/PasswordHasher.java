package lk.sliit.parkingmanagement.oopapp.utils;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    // The number of log rounds used to generate the salt
    private static final int LOG_ROUNDS = 10;
    // Hashes the plain text password using BCrypt
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        // Generate salt and return the hashed password
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        // If the plain password is null or empty, return false
        if (plainPassword == null || plainPassword.isEmpty()) {return false;}
        // Use BCrypt to compare the plain and hashed passwords.
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
