package paragraphBookmarkExtension.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class CreateUserRequest extends UserAuthenticationRequest {

    private boolean bookmarkEmailReminder;

    public boolean isBookmarkEmailReminder() {
        return bookmarkEmailReminder;
    }

    public void setBookmarkEmailReminder(boolean bookmarkEmailReminder) {
        this.bookmarkEmailReminder = bookmarkEmailReminder;
    }
}
