package com.permanentMarker.integration;

import com.permanentMarker.dao.domain.Mark;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.permanentMarker.service.MarkService;
import com.permanentMarker.service.UserService;
import com.permanentMarker.transfer.exception.GenericException;
import com.permanentMarker.transfer.user.UserTO;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Flaviu Ratiu
 * @since 20 Oct 2016
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MarkServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MarkService markService;

    @Test
    public void findNewlyAddedMarkById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String text = System.currentTimeMillis() + "";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        boolean emailReminder = true;
        Mark mark = markService.addMark(user.getId(), documentUrl, text, textFirstPart, textLastPart, emailReminder);
        assertThat("Failed to create mark.", mark, notNullValue());
        assertThat(mark.getId(), notNullValue());
        assertThat(mark.getUserId(), is(user.getId()));
        assertThat(mark.getCreationDate(), notNullValue());
        assertThat(mark.getDocumentUrl(), is(documentUrl));
        assertThat(mark.getPrecedingText(), is(textFirstPart));
        assertThat(mark.getTrailingText(), is(textLastPart));
        assertThat(mark.isEmailReminderEnabled(), is(emailReminder));

        Mark createdMark = markService.getMark(mark.getId());
        assertThat("Failed to retrieve mark.", createdMark, notNullValue());
        assertThat(createdMark.getId(), is(mark.getId()));
        assertThat(createdMark.getUserId(), is(mark.getUserId()));
        assertThat(createdMark.getCreationDate(), notNullValue());
        assertThat(createdMark.getDocumentUrl(), is(mark.getDocumentUrl()));
        assertThat(createdMark.getPrecedingText(), is(mark.getPrecedingText()));
        assertThat(createdMark.getTrailingText(), is(mark.getTrailingText()));
        assertThat(createdMark.isEmailReminderEnabled(), is(mark.isEmailReminderEnabled()));
    }

    @Test
    public void findNewlyAddedMarkByUserIdAndDocumentUrl() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String text = System.currentTimeMillis() + "";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        Mark mark = markService.addMark(user.getId(), documentUrl, text, textFirstPart, textLastPart, false);
        assertThat("Failed to create mark.", mark, notNullValue());

        List<Mark> searchedMarks = markService.findByUserIdAndDocumentUrl(user.getId(), mark.getDocumentUrl());
        assertThat("Failed to retrieve mark.", searchedMarks, notNullValue());
        assertThat("No marks found.", !searchedMarks.isEmpty());
        for (Mark searchedMark : searchedMarks) {
            assertThat(searchedMark.getUserId(), is(mark.getUserId()));
            assertThat(searchedMark.getDocumentUrl(), is(mark.getDocumentUrl()));
        }
    }

    @Test
    public void deleteMarkById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String text = System.currentTimeMillis() + "";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        Mark mark = markService.addMark(user.getId(), documentUrl, text, textFirstPart, textLastPart, false);
        assertThat("Failed to create mark.", mark, notNullValue());

        markService.deleteMark(mark.getId());

        Mark deletedMark = markService.getMark(mark.getId());
        assertThat("Deleted mark unexpectedly retrieved from DB.", deletedMark, nullValue());
    }

    @Test
    public void batchDeleteMarksById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        List<Long> markIdsToBeDeleted = new ArrayList<>();
        List<Long> markIdsToBeSaved = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Mark mark = getMark(user);
            assertThat("Failed to create mark.", mark, notNullValue());
            if (i < 3) {
                markIdsToBeDeleted.add(mark.getId());
            } else {
                markIdsToBeSaved.add(mark.getId());
            }
        }

        markService.batchDeleteMarks(markIdsToBeDeleted);

        for (Long markId : markIdsToBeDeleted) {
            Mark deletedMark = markService.getMark(markId);
            assertThat("Deleted mark unexpectedly retrieved from DB.", deletedMark, nullValue());
        }
        for (Long markId : markIdsToBeSaved) {
            Mark remainingMark = markService.getMark(markId);
            assertThat("Mark unexpectedly deleted.", remainingMark, notNullValue());
        }
    }

    @Test
    public void batchDeleteAllMarksByUserId() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password);
        assertThat("Failed to create user.", user, notNullValue());

        int marksToBeCreated = 3;
        for (int i = 0; i < marksToBeCreated; i++) {
            Mark mark = getMark(user);
            assertThat("Failed to create mark.", mark, notNullValue());
        }

        int marksCount = markService.countByUserId(user.getId());
        assertThat(marksCount, is(marksToBeCreated));

        markService.deleteAllMarksOfOneUser(user.getId());
        marksCount = markService.countByUserId(user.getId());
        assertThat(marksCount, is(0));
    }

    private Mark getMark(UserTO user) throws GenericException {
        String documentUrl = "http://example" + System.currentTimeMillis() + ".com";
        String text = System.currentTimeMillis() + "";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        return markService.addMark(user.getId(), documentUrl, text, textFirstPart, textLastPart, false);
    }
}
