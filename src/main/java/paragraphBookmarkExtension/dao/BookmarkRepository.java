package paragraphBookmarkExtension.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import paragraphBookmarkExtension.dao.domain.Bookmark;

import java.util.List;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findById(long id);

    Page<Bookmark> findByUserId(long userId, Pageable pageable);

    List<Bookmark> findByUserId(long userId);

    List<Bookmark> findByUserIdAndDocumentUrl(long userId, String documentUrl);

    int countByUserId(long userId);

    @Query("SELECT COUNT(*) FROM Bookmark bookmark WHERE bookmark.userId IS :userId AND bookmark.documentUrl IS :documentUrl AND bookmark.textFirstPart IS :textFirstPart AND bookmark.textLastPart IS :textLastPart")
    int countIdenticalBookmarks(@Param("userId") long userId, @Param("documentUrl") String documentUrl, @Param("textFirstPart") String textFirstPart, @Param("textLastPart") String textLastPart);

}
