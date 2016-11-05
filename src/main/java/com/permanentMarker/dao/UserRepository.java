package com.permanentMarker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.permanentMarker.dao.domain.User;

/**
 * @author Flaviu Ratiu
 * @since 09 Oct 2016
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findById(long id);

    int countByEmail(String email);

    @Query("SELECT COUNT(*) FROM User user WHERE user.email != NULL")
    long countRegistered();

    @Query("SELECT COUNT(*) FROM User user WHERE user.email IS NULL")
    long countAnonymous();

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.salt = :salt, user.iterations = :iterations, user.hash = :hash WHERE user.id = :userId")
    void updatePassword(@Param("userId") long userId, @Param("salt") String salt, @Param("iterations") int iterations, @Param("hash") String hash);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.email = :email WHERE user.id = :userId")
    void updateEmail(@Param("userId") long userId, @Param("email") String email);

}
