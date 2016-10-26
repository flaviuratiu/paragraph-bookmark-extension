package paragraphBookmarkExtension.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import paragraphBookmarkExtension.dao.domain.Bookmark;
import paragraphBookmarkExtension.service.BookmarkService;
import paragraphBookmarkExtension.transfer.bookmark.*;
import paragraphBookmarkExtension.transfer.exception.GenericException;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Bookmark add(AddBookmarkRequest request) throws GenericException {
        validateAddBookmarkRequest(request);
        return bookmarkService.addBookmark(request.getUserId(), request.getDocumentUrl(), request.getTextFirstPart(), request.getTextLastPart());
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<Bookmark> get(GetBookmarkRequest request) throws GenericException {
        validateGetBookmarkRequest(request);
        return bookmarkService.findByUserIdAndDocumentUrl(request.getUserId(), request.getDocumentUrl());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Page<Bookmark> getAll(GetAllBookmarksRequest request) throws GenericException {
        validateUserId(request);
        return bookmarkService.findByUserId(request.getUserId(), request.getPageable());
    }

    @RequestMapping(value = "/{bookmarkId}/delete", method = RequestMethod.GET)
    public void delete(long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void batchDelete(BatchDeleteBookmarksRequest request) throws GenericException {
        if (!request.getBookmarkIds().isEmpty()) {
            validateUserId(request);
            bookmarkService.batchDeleteBookmarks(request.getBookmarkIds());
        }
    }

    @RequestMapping(value = "/{userId}/delete", method = RequestMethod.GET)
    public void deleteAll(long userId) {
        bookmarkService.deleteAllBookmarksOfOneUser(userId);
    }

    private void validateUserId(GenericBookmarkRequest request) throws GenericException {
        if (request.getUserId() == 0) {
            throw new GenericException("Invalid user ID.", 422);
        }
    }

    private boolean isEmptyString(String string) {
        return string == null || string.equals("");
    }

    private void validateAddBookmarkRequest(AddBookmarkRequest request) throws GenericException {
        validateGetBookmarkRequest(request);
        if (isEmptyString(request.getTextFirstPart()) && isEmptyString(request.getTextLastPart())) {
            throw new GenericException("Bookmark delimiters missing", 422);
        }
    }

    private void validateGetBookmarkRequest(GetBookmarkRequest request) throws GenericException {
        validateUserId(request);
        if (isEmptyString(request.getDocumentUrl())) {
            throw new GenericException("Document URL is missing.", 422);
        }
    }

}
