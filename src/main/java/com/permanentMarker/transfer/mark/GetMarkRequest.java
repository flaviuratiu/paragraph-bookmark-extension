package com.permanentMarker.transfer.mark;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class GetMarkRequest extends GenericMarkRequest {

    private String documentUrl;

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
