package com.permanentMarker.service;

import com.permanentMarker.transfer.user.EncryptedPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.permanentMarker.dao.UserRepository;
import com.permanentMarker.dao.domain.User;
import com.permanentMarker.transfer.exception.GenericException;
import com.permanentMarker.transfer.user.UserTO;
import com.permanentMarker.util.password.PasswordEncryptionUtil;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;
import java.util.Date;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserRepository userRepository;

    public User findByEmail(String email) {
        LOGGER.info("Searching user by email: {}", email);
        try {
            return userRepository.findByEmail(email);
        } catch (NonUniqueResultException exception) {
            // TODO: catch this exception properly
            if (email != null) {
                LOGGER.error("Found multiple users with email {}", email);
            }
            return null;
        }
    }

    public User findById(long id) {
        LOGGER.info("Searching user by id: {}", id);
        return userRepository.findById(id);
    }

    protected int countByEmail(String email) {
        LOGGER.debug("Counting users with email: {}", email);
        return userRepository.countByEmail(email);
    }

    public long countRegistered() {
        LOGGER.debug("Counting registered users");
        return userRepository.countRegistered();
    }

    public long countAnonymous() {
        LOGGER.debug("Counting anonymous users");
        return userRepository.countAnonymous();
    }

    public UserTO createUser(String email, String password) throws GenericException {
        User user = new User();
        user.setCreationDate(new Date());
        if (email != null) {
            LOGGER.info("Creating user: {}", email);
            validateCredentialsForNewUser(email, password);
            user.setEmail(email);

            EncryptedPassword encryptedPassword = PasswordEncryptionUtil.getEncryptedPassword(password);
            user.setIterations(encryptedPassword.getIterations());
            user.setSalt(encryptedPassword.getSalt());
            user.setHash(encryptedPassword.getHash());
        } else {
            LOGGER.info("Creating anonymous user.");
        }
        User createdUser = userRepository.save(user);
        LOGGER.info("Created new user.");
        return createdUser.asUserTO();
    }

    private void validateCredentialsForNewUser(String email, String password) throws GenericException {
        int usersWithRequestedEmail = countByEmail(email);
        if (usersWithRequestedEmail > 0) {
            throw new GenericException("Email " + email + " is already in use.", 422);
        }
        if (password == null || password.equals("")) {
            throw new GenericException("Password is required for registering.", 422);
        }
    }

    public UserTO logIn(String email, String password) throws GenericException {
        User user = findByEmail(email);
        return validateCredentialsForExistingUser(user, password);
    }

    private UserTO validateCredentialsForExistingUser(User user, String password) throws GenericException {
        if (user == null) {
            throw new GenericException("Invalid username or password", 401);
        }
        EncryptedPassword encryptedPassword = PasswordEncryptionUtil.validateEncryptedPassword(password, user.getSalt(), user.getIterations());
        if (encryptedPassword.getHash().equals(user.getHash())) {
            LOGGER.info("Login data validated.");
            return user.asUserTO();
        } else {
            throw new GenericException("Invalid username or password", 401);
        }
    }

    public void updateEmail(long userId, String email) {
        userRepository.updateEmail(userId, email);
    }

    public void updatePassword(long userId, String oldPassword, String newPassword) throws GenericException {
        User user = findById(userId);
        UserTO validatedUser = validateCredentialsForExistingUser(user, oldPassword);
        if (validatedUser != null) {
            overridePassword(userId, newPassword);
        }
    }

    public void resetPassword(long userId, String newPassword, String token) {
        // TODO: validate token
        overridePassword(userId, newPassword);
    }

    private void overridePassword(long userId, String newPassword) {
        EncryptedPassword encryptedPassword = PasswordEncryptionUtil.getEncryptedPassword(newPassword);
        userRepository.updatePassword(userId, encryptedPassword.getSalt(), encryptedPassword.getIterations(), encryptedPassword.getHash());
    }
}
