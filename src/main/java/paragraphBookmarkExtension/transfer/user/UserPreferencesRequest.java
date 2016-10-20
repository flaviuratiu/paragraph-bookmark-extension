package paragraphBookmarkExtension.transfer.user;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class UserPreferencesRequest {

    private long userId;
    private boolean bookmarkEmailReminder;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isBookmarkEmailReminder() {
        return bookmarkEmailReminder;
    }

    public void setBookmarkEmailReminder(boolean bookmarkEmailReminder) {
        this.bookmarkEmailReminder = bookmarkEmailReminder;
    }
}
