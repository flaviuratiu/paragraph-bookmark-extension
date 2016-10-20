package paragraphBookmarkExtension.transfer.bookmark;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class BatchDeleteBookmarksRequest extends GenericBookmarkRequest {

    private List<Long> bookmarkIds;

    public List<Long> getBookmarkIds() {
        return bookmarkIds;
    }

    public void setBookmarkIds(List<Long> bookmarkIds) {
        this.bookmarkIds = bookmarkIds;
    }
}
