package com.permanentMarker.web;

import com.permanentMarker.dao.domain.Mark;
import com.permanentMarker.transfer.mark.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.permanentMarker.service.MarkService;
import com.permanentMarker.transfer.exception.GenericException;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 15 Oct 2016
 */
@RestController
@RequestMapping("/marks")
public class MarkController {

    @Autowired
    private MarkService markService;

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Mark add(AddMarkRequest request) throws GenericException {
        validateAddMarkRequest(request);
        return markService.addMark(request.getUserId(), request.getDocumentUrl(), request.getText(), request.getPrecedingText(), request.getTrailingText(), request.isEmailReminderEnabled());
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public List<Mark> get(GetMarkRequest request) throws GenericException {
        validateGetMarkRequest(request);
        return markService.findByUserIdAndDocumentUrl(request.getUserId(), request.getDocumentUrl());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Page<Mark> getAll(GetAllMarksRequest request) throws GenericException {
        validateUserId(request);
        return markService.findByUserId(request.getUserId(), request.getPageable());
    }

    @RequestMapping(value = "/{markId}/delete", method = RequestMethod.GET)
    public void delete(long markId) {
        markService.deleteMark(markId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void batchDelete(BatchDeleteMarksRequest request) throws GenericException {
        if (!request.getMarkIds().isEmpty()) {
            validateUserId(request);
            markService.batchDeleteMarks(request.getMarkIds());
        }
    }

    @RequestMapping(value = "/{userId}/delete", method = RequestMethod.GET)
    public void deleteAll(long userId) {
        markService.deleteAllMarksOfOneUser(userId);
    }

    private void validateUserId(GenericMarkRequest request) throws GenericException {
        if (request.getUserId() == 0) {
            throw new GenericException("Invalid user ID.", 422);
        }
    }

    private boolean isEmptyString(String string) {
        return string == null || string.equals("");
    }

    private void validateAddMarkRequest(AddMarkRequest request) throws GenericException {
        validateGetMarkRequest(request);
        if (isEmptyString(request.getPrecedingText()) && isEmptyString(request.getTrailingText())) {
            throw new GenericException("Mark delimiters missing", 422);
        }
    }

    private void validateGetMarkRequest(GetMarkRequest request) throws GenericException {
        validateUserId(request);
        if (isEmptyString(request.getDocumentUrl())) {
            throw new GenericException("Document URL is missing.", 422);
        }
    }

}
