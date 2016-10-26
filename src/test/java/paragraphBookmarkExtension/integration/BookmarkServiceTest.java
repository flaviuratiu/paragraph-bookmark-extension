package paragraphBookmarkExtension.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import paragraphBookmarkExtension.dao.domain.Bookmark;
import paragraphBookmarkExtension.service.BookmarkService;
import paragraphBookmarkExtension.service.UserService;
import paragraphBookmarkExtension.transfer.exception.GenericException;
import paragraphBookmarkExtension.transfer.user.UserTO;

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
public class BookmarkServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    public void findNewlyAddedBookmarkById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password, false);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        Bookmark bookmark = bookmarkService.addBookmark(user.getId(), documentUrl, textFirstPart, textLastPart);
        assertThat("Failed to create bookmark.", bookmark, notNullValue());
        assertThat(bookmark.getId(), notNullValue());
        assertThat(bookmark.getUserId(), is(user.getId()));
        assertThat(bookmark.getCreationDate(), notNullValue());
        assertThat(bookmark.getDocumentUrl(), is(documentUrl));
        assertThat(bookmark.getTextFirstPart(), is(textFirstPart));
        assertThat(bookmark.getTextLastPart(), is(textLastPart));

        Bookmark createdBookmark = bookmarkService.getBookmark(bookmark.getId());
        assertThat("Failed to retrieve bookmark.", createdBookmark, notNullValue());
        assertThat(createdBookmark.getId(), is(bookmark.getId()));
        assertThat(createdBookmark.getUserId(), is(bookmark.getUserId()));
        assertThat(createdBookmark.getCreationDate(), notNullValue());
        assertThat(createdBookmark.getDocumentUrl(), is(bookmark.getDocumentUrl()));
        assertThat(createdBookmark.getTextFirstPart(), is(bookmark.getTextFirstPart()));
        assertThat(createdBookmark.getTextLastPart(), is(bookmark.getTextLastPart()));
    }

    @Test
    public void findNewlyAddedBookmarkByUserIdAndDocumentUrl() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password, false);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        Bookmark bookmark = bookmarkService.addBookmark(user.getId(), documentUrl, textFirstPart, textLastPart);
        assertThat("Failed to create bookmark.", bookmark, notNullValue());

        List<Bookmark> searchedBookmarks = bookmarkService.findByUserIdAndDocumentUrl(user.getId(), bookmark.getDocumentUrl());
        assertThat("Failed to retrieve bookmark.", searchedBookmarks, notNullValue());
        assertThat("No bookmarks found.", !searchedBookmarks.isEmpty());
        for (Bookmark searchedBookmark : searchedBookmarks) {
            assertThat(searchedBookmark.getUserId(), is(bookmark.getUserId()));
            assertThat(searchedBookmark.getDocumentUrl(), is(bookmark.getDocumentUrl()));
        }
    }

    @Test
    public void deleteBookmarkById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password, false);
        assertThat("Failed to create user.", user, notNullValue());

        String documentUrl = "http://example.com";
        String textFirstPart = System.currentTimeMillis() + "";
        String textLastPart = System.currentTimeMillis() + "";
        Bookmark bookmark = bookmarkService.addBookmark(user.getId(), documentUrl, textFirstPart, textLastPart);
        assertThat("Failed to create bookmark.", bookmark, notNullValue());

        bookmarkService.deleteBookmark(bookmark.getId());

        Bookmark deletedBookmark = bookmarkService.getBookmark(bookmark.getId());
        assertThat("Deleted bookmark unexpectedly retrieved from DB.", deletedBookmark, nullValue());
    }

    @Test
    public void batchDeleteBookmarksById() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password, false);
        assertThat("Failed to create user.", user, notNullValue());

        List<Long> bookmarkIdsToBeDeleted = new ArrayList<>();
        List<Long> bookmarkIdsToBeSaved = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String documentUrl = "http://example" + System.currentTimeMillis() + ".com";
            String textFirstPart = System.currentTimeMillis() + "";
            String textLastPart = System.currentTimeMillis() + "";
            Bookmark bookmark = bookmarkService.addBookmark(user.getId(), documentUrl, textFirstPart, textLastPart);
            assertThat("Failed to create bookmark.", bookmark, notNullValue());
            if (i < 3) {
                bookmarkIdsToBeDeleted.add(bookmark.getId());
            } else {
                bookmarkIdsToBeSaved.add(bookmark.getId());
            }
        }

        bookmarkService.batchDeleteBookmarks(bookmarkIdsToBeDeleted);

        for (Long bookmarkId : bookmarkIdsToBeDeleted) {
            Bookmark deletedBookmark = bookmarkService.getBookmark(bookmarkId);
            assertThat("Deleted bookmark unexpectedly retrieved from DB.", deletedBookmark, nullValue());
        }
        for (Long bookmarkId : bookmarkIdsToBeSaved) {
            Bookmark remainingBookmark = bookmarkService.getBookmark(bookmarkId);
            assertThat("Bookmark unexpectedly deleted.", remainingBookmark, notNullValue());
        }
    }

    @Test
    public void batchDeleteAllBookmarksByUserId() throws GenericException {
        String email = "test" + System.currentTimeMillis() + "@example.com";
        String password = "password";
        UserTO user = userService.createUser(email, password, false);
        assertThat("Failed to create user.", user, notNullValue());

        List<Long> bookmarkIds = new ArrayList<>();
        int bookmarksToBeCreated = 3;
        for (int i = 0; i < bookmarksToBeCreated; i++) {
            String documentUrl = "http://example" + System.currentTimeMillis() + ".com";
            String textFirstPart = System.currentTimeMillis() + "";
            String textLastPart = System.currentTimeMillis() + "";
            Bookmark bookmark = bookmarkService.addBookmark(user.getId(), documentUrl, textFirstPart, textLastPart);
            assertThat("Failed to create bookmark.", bookmark, notNullValue());
            bookmarkIds.add(bookmark.getId());
        }

        int bookmarksCount = bookmarkService.countByUserId(user.getId());
        assertThat(bookmarksCount, is(bookmarksToBeCreated));

        bookmarkService.deleteAllBookmarksOfOneUser(user.getId());
        bookmarksCount = bookmarkService.countByUserId(user.getId());
        assertThat(bookmarksCount, is(0));
    }
}
