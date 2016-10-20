package paragraphBookmarkExtension.transfer.exception;

/**
 * @author Flaviu Ratiu
 * @since 14 Oct 2016
 */
public class GenericException extends Exception{

    private String message;
    private int statusCode;

    public GenericException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
