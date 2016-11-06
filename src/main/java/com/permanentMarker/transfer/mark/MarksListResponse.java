package com.permanentMarker.transfer.mark;

import com.permanentMarker.dao.domain.Mark;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 06 Nov 2016
 */
public class MarksListResponse {

    private List<Mark> marks;

    public MarksListResponse(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

}
