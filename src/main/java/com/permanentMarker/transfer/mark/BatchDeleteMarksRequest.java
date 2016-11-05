package com.permanentMarker.transfer.mark;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class BatchDeleteMarksRequest extends GenericMarkRequest {

    private List<Long> markIds;

    public List<Long> getMarkIds() {
        return markIds;
    }

    public void setMarkIds(List<Long> markIds) {
        this.markIds = markIds;
    }
}
