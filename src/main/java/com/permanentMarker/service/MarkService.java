package com.permanentMarker.service;

import com.permanentMarker.dao.MarkRepository;
import com.permanentMarker.dao.domain.Mark;
import com.permanentMarker.transfer.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Flaviu Ratiu
 * @since 11 Oct 2016
 */
@Service
public class MarkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarkService.class);

    @Resource
    private MarkRepository markRepository;

    public Page<Mark> findByUserId(long userId, Pageable pageable) {
        LOGGER.info("Searching mark by user id: {}", userId);
        return markRepository.findByUserId(userId, pageable);
    }

    protected List<Mark> findAllByUserId(long userId) {
        LOGGER.info("Getting all marks of user with id: {}", userId);
        return markRepository.findByUserId(userId);
    }

    public List<Mark> findByUserIdAndDocumentUrl(long userId, String documentUrl) {
        LOGGER.info("Searching marks by user id " + userId + " and document URL: " + documentUrl);
        List<Mark> marks = markRepository.findByUserIdAndDocumentUrl(userId, documentUrl);
        LOGGER.debug("Retrieved marks:\n" + marks.stream().map(Mark::toString).collect(Collectors.joining("\n")));
        return marks;
    }

    public int countByUserId(long userId) {
        LOGGER.info("Counting marks belonging to user with id: {}", userId);
        return markRepository.countByUserId(userId);
    }

    public Mark addMark(long userId, String documentUrl, String text, String precedingText, String trailingText, boolean emailReminderEnabled) throws GenericException {
        LOGGER.info("Adding mark for user with id: {}", userId);
        boolean duplicateMark = markRepository.countIdenticalMarks(userId, documentUrl, text, precedingText, trailingText) > 0;
        if (duplicateMark) {
            throw new GenericException("User has already placed a mark in the same location.", 422);
        }
        Mark mark = new Mark();
        mark.setUserId(userId);
        mark.setCreationDate(new Date());
        mark.setDocumentUrl(documentUrl);
        mark.setText(text);
        mark.setPrecedingText(precedingText);
        mark.setTrailingText(trailingText);
        mark.setEmailReminderEnabled(emailReminderEnabled);
        Mark createdMark = markRepository.save(mark);
        LOGGER.info("Added mark {}", createdMark.getId());
        return createdMark;
    }

    public Mark getMark(long markId) {
        LOGGER.info("Finding mark with id: {}", markId);
        return markRepository.findById(markId);
    }

    public void deleteMark(long markId) {
        Mark mark = getMark(markId);
        LOGGER.info("Deleting mark with id: {}", markId);
        markRepository.delete(mark);
    }

    public void batchDeleteMarks(List<Long> markIds) {
        LOGGER.info("Deleting marks in batch");
        List<Mark> marks = markRepository.findAll(markIds);
        markRepository.deleteInBatch(marks);
    }

    public void deleteAllMarksOfOneUser(long userId) {
        List<Mark> marks = findAllByUserId(userId);
        LOGGER.info("Deleting all marks of user with id: {}", userId);
        markRepository.deleteInBatch(marks);
    }

    public void updateEmailReminderFlag(long markId, boolean enabled) {
        markRepository.updateEmailReminderFlag(markId, enabled);
    }

}
