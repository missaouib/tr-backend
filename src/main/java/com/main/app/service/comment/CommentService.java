package com.main.app.service.comment;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.comment.CommentDTO;
import com.main.app.domain.model.comment.Comment;

import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment createComment(CommentDTO commentDTO);

    Comment removeComment(Long userId, Long comment_id);

    Comment updateComment(CommentDTO commentDTO, Long id);



    Entities getAllVariationVerifiedComments(Long variation_id, Pageable pageable);

    Entities getAllVariationUnVerifiedComments(Long variation_id, Pageable pageable);


    Entities getCommentsForVerification(Pageable pageable);

    Entities getUsersUnverifiedComments(Long user_id,Pageable pageable);

    Entities getUsersVerifiedComments(Long user_id,Pageable pageable);



    Comment verifyComment(Long comment_id, Boolean verify);

}


