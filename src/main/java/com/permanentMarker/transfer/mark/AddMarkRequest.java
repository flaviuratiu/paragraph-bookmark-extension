package com.permanentMarker.transfer.mark;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class AddMarkRequest extends GetMarkRequest {

    private String text;
    private String precedingText;
    private String trailingText;
    private boolean emailReminderEnabled;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrecedingText() {
        return precedingText;
    }

    public void setPrecedingText(String precedingText) {
        this.precedingText = precedingText;
    }

    public String getTrailingText() {
        return trailingText;
    }

    public void setTrailingText(String trailingText) {
        this.trailingText = trailingText;
    }

    public boolean isEmailReminderEnabled() {
        return emailReminderEnabled;
    }

    public void setEmailReminderEnabled(boolean emailReminderEnabled) {
        this.emailReminderEnabled = emailReminderEnabled;
    }
}
