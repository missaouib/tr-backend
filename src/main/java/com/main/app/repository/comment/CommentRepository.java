package com.main.app.repository.comment;


import com.main.app.domain.model.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByUserId(Long userId);

    Page<Comment> findAllByUserIdAndDeletedFalse(Long user_id, Pageable pageable);

    Page<Comment> findAllByIdInAndDeletedFalse(List<Long> ids, Pageable mySqlPaging);

    Page<Comment> findAllByUserIdAndVerifiedFalse(Long user_id, Pageable pageable);

    Page<Comment> findAllByUserIdAndVerifiedTrue(Long user_id, Pageable pageable);

    Page<Comment> findAllByVerifiedFalse(Pageable pageable);

    Page<Comment> findAllByVariationIdAndVerifiedFalse(Long variation_id, Pageable pageable);

    Page<Comment> findAllByVariationIdAndVerifiedTrue(Long variation_id, Pageable pageable);

    Page<Comment> findAllByVariationId(Long variation_id, Pageable pageable);
}
