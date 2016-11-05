package com.permanentMarker.transfer.mark;

import org.springframework.data.domain.Pageable;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
public class GetAllMarksRequest extends GenericMarkRequest {

    private Pageable pageable;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
