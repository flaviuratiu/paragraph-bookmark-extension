package paragraphBookmarkExtension.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import paragraphBookmarkExtension.dao.BookmarkRepository;
import paragraphBookmarkExtension.dao.domain.Bookmark;
import paragraphBookmarkExtension.transfer.exception.GenericException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 11 Oct 2016
 */
@Service
public class BookmarkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkService.class);

    @Resource
    private BookmarkRepository bookmarkRepository;

    public Page<Bookmark> findByUserId(long userId, Pageable pageable) {
        LOGGER.info("Searching bookmark by user id: {}", userId);
        return bookmarkRepository.findByUserId(userId, pageable);
    }

    protected List<Bookmark> findAllByUserId(long userId) {
        LOGGER.info("Getting all bookmarks of user with id: {}", userId);
        return bookmarkRepository.findByUserId(userId);
    }

    public List<Bookmark> findByUserIdAndDocumentUrl(long userId, String documentUrl) {
        LOGGER.info("Searching bookmark by user id " + userId + " and document URL: " + documentUrl);
        return bookmarkRepository.findByUserIdAndDocumentUrl(userId, documentUrl);
    }

    public int countByUserId(long userId) {
        LOGGER.info("Counting bookmarks belonging to user with id: {}", userId);
        return bookmarkRepository.countByUserId(userId);
    }

    public Bookmark addBookmark(long userId, String documentUrl, String textFirstPart, String textLastPart) throws GenericException {
        LOGGER.info("Adding bookmark for user with id: " + userId);
        boolean duplicateBookmark = bookmarkRepository.countIdenticalBookmarks(userId, documentUrl, textFirstPart, textLastPart) > 0;
        if (duplicateBookmark) {
            throw new GenericException("User has already placed a bookmark in the same location.", 422);
        }
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(userId);
        bookmark.setCreationDate(new Date());
        bookmark.setDocumentUrl(documentUrl);
        bookmark.setTextFirstPart(textFirstPart);
        bookmark.setTextLastPart(textLastPart);
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark getBookmark(long bookmarkId) {
        LOGGER.info("Finding bookmark with id: " + bookmarkId);
        return bookmarkRepository.findById(bookmarkId);
    }

    public void deleteBookmark(long bookmarkId) {
        Bookmark bookmark = getBookmark(bookmarkId);
        LOGGER.info("Deleting bookmark with id: " + bookmarkId);
        bookmarkRepository.delete(bookmark);
    }

    public void batchDeleteBookmarks(List<Long> bookmarkIds) {
        LOGGER.info("Deleting bookmarks in batch");
        List<Bookmark> bookmarks = bookmarkRepository.findAll(bookmarkIds);
        bookmarkRepository.deleteInBatch(bookmarks);
    }

    public void deleteAllBookmarksOfOneUser(long userId) {
        List<Bookmark> bookmarks = findAllByUserId(userId);
        LOGGER.info("Deleting all bookmarks of user with id: {}", userId);
        bookmarkRepository.deleteInBatch(bookmarks);
    }

}
