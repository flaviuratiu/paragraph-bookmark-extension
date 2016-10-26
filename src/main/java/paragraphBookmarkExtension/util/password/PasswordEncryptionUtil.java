package paragraphBookmarkExtension.util.password;

import org.springframework.util.DigestUtils;
import paragraphBookmarkExtension.transfer.user.EncryptedPassword;

import java.util.UUID;

/**
 * @author Flaviu Ratiu
 * @since 13 Oct 2016
 */
public class PasswordEncryptionUtil {
    private static final int ITERATIONS = 200000;

    public static EncryptedPassword validateEncryptedPassword(String password, String salt, int iterations) {
        String hash = getHash(password, salt, iterations);
        EncryptedPassword encryptedPassword = new EncryptedPassword();
        encryptedPassword.setSalt(salt);
        encryptedPassword.setHash(hash);
        encryptedPassword.setIterations(iterations);
        return encryptedPassword;
    }

    public static EncryptedPassword getEncryptedPassword(String password) {
        String salt = UUID.randomUUID().toString();
        String hash = getHash(password, salt, ITERATIONS);
        EncryptedPassword encryptedPassword = new EncryptedPassword();
        encryptedPassword.setSalt(salt);
        encryptedPassword.setHash(hash);
        encryptedPassword.setIterations(ITERATIONS);
        return encryptedPassword;
    }

    private static String getHash(String password, String salt, int iterations) {
        String hash = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        while (iterations > 0) {
            hash = DigestUtils.md5DigestAsHex(hash.getBytes());
            iterations--;
        }
        return hash;
    }

}
