package paragraphBookmarkExtension.transfer.bookmark;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class GetBookmarkRequest extends GenericBookmarkRequest {

    private String documentUrl;

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
