package com.permanentMarker.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.permanentMarker.dao.domain.User;
import com.permanentMarker.service.UserService;
import com.permanentMarker.transfer.exception.GenericException;
import com.permanentMarker.transfer.user.UserTO;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * @author Flaviu Ratiu
 * @since 18 Oct 2016
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findNewlyAddedUser() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);

        assertThat("Failed to create user.", user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.getCreationDate(), notNullValue());

        User createdUser = userService.findByEmail(email);
        assertThat(createdUser.getId(), notNullValue());
        assertThat(createdUser.getEmail(), is(email));
        assertThat(createdUser.getCreationDate(), notNullValue());
    }

    @Test
    public void loginWithNewlyAddedUser() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);

        assertThat("Failed to create user.", user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.getCreationDate(), notNullValue());

        UserTO loggedInUser = userService.logIn(email, password);
        assertThat(loggedInUser.getId(), notNullValue());
        assertThat(loggedInUser.getEmail(), is(email));
        assertThat(loggedInUser.getCreationDate(), notNullValue());
    }

    @Test
    public void tryToCreateDuplicateUser() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);

        assertThat("Failed to create user.", user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.getCreationDate(), notNullValue());

        try {
            UserTO duplicateUser = userService.createUser(email, password);
            assertThat("Unexpectedly created duplicate user.", duplicateUser, nullValue());
        } catch (GenericException e) {
            assertThat(e.getMessage(), is("Email " + email + " is already in use."));
            assertThat(e.getStatus(), is(422));
        }
    }

    @Test
    public void updateEmailOfAnonymousUser() throws GenericException {
        UserTO user = userService.createUser(null, null);
        assertThat("Failed to create user.", user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), nullValue());
        assertThat(user.getCreationDate(), notNullValue());

        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        userService.updateEmail(user.getId(), email);

        User updatedUser = userService.findByEmail(email);
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getEmail(), is(email));
        // TODO
//        assertThat(updatedUser.getHash(), notNullValue());
        assertThat(updatedUser.getCreationDate(), notNullValue());
    }

    @Test
    public void updateEmailOfRegisteredUser() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);

        assertThat("Failed to create user.", user, notNullValue());
        assertThat(user.getId(), notNullValue());
        assertThat(user.getEmail(), is(email));
        assertThat(user.getCreationDate(), notNullValue());

        String newEmail = "test" + System.currentTimeMillis() + "@example.com";
        userService.updateEmail(user.getId(), newEmail);

        User updatedUser = userService.findById(user.getId());
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getEmail(), is(newEmail));
        // TODO
//        assertThat(updatedUser.getHash(), notNullValue());
        assertThat(updatedUser.getCreationDate(), notNullValue());
    }

    @Test
    public void countAnonymousUsers() throws GenericException {
        UserTO user = userService.createUser(null, null);
        assertThat("Failed to create user.", user, notNullValue());

        int count = (int) userService.countAnonymous();
        assertThat(count, greaterThanOrEqualTo(1));
    }

    @Test
    public void countRegisteredUsers() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        int count = (int) userService.countAnonymous();
        assertThat(count, greaterThanOrEqualTo(1));
    }
}
