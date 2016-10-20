package paragraphBookmarkExtension.transfer.reports;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class GenericCountResponse {

    private String key;
    private long count;

    public GenericCountResponse(String key, long count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public long getCount() {
        return count;
    }
}
