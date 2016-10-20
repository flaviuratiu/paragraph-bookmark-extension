package paragraphBookmarkExtension.transfer.bookmark;

import org.springframework.data.domain.Pageable;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class GetAllBookmarksRequest extends GenericBookmarkRequest {

    private Pageable pageable;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
