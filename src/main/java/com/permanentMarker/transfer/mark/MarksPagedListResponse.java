package com.permanentMarker.transfer.mark;

import com.permanentMarker.dao.domain.Mark;
import org.springframework.data.domain.Page;

/**
 * @author Flaviu Ratiu
 * @since 06 Nov 2016
 */
public class MarksPagedListResponse {

    private Page<Mark> marks;

    public MarksPagedListResponse(Page<Mark> marks) {
        this.marks = marks;
    }

    public Page<Mark> getMarks() {
        return marks;
    }

}
