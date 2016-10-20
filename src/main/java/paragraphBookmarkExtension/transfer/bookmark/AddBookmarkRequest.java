package paragraphBookmarkExtension.transfer.bookmark;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class AddBookmarkRequest extends GetBookmarkRequest {

    private String textFirstPart;
    private String textLastPart;

    public String getTextFirstPart() {
        return textFirstPart;
    }

    public void setTextFirstPart(String textFirstPart) {
        this.textFirstPart = textFirstPart;
    }

    public String getTextLastPart() {
        return textLastPart;
    }

    public void setTextLastPart(String textLastPart) {
        this.textLastPart = textLastPart;
    }
}
