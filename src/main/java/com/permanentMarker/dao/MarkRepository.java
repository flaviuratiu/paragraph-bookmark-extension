package com.permanentMarker.dao;

import com.permanentMarker.dao.domain.Mark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Mark findById(long id);

    Page<Mark> findByUserId(long userId, Pageable pageable);

    List<Mark> findByUserId(long userId);

    List<Mark> findByUserIdAndDocumentUrl(long userId, String documentUrl);

    int countByUserId(long userId);

    @Query("SELECT COUNT(*) FROM Mark mark WHERE mark.userId IS :userId AND mark.documentUrl IS :documentUrl AND mark.text IS :text AND mark.precedingText IS :precedingText AND mark.trailingText IS :trailingText")
    int countIdenticalMarks(@Param("userId") long userId, @Param("documentUrl") String documentUrl, @Param("text") String text, @Param("precedingText") String precedingText, @Param("trailingText") String trailingText);

    @Transactional
    @Modifying
    @Query("UPDATE Mark mark SET mark.emailReminderEnabled = :enabled WHERE mark.id = :markId")
    void updateEmailReminderFlag(@Param("markId") long markId, @Param("enabled") boolean enabled);

}
