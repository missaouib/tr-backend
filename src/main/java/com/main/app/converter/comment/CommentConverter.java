package com.main.app.converter.comment;

import com.main.app.domain.dto.comment.CommentDTO;
import com.main.app.domain.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {


    public static CommentDTO entityToDto(Comment comment){
        return CommentDTO
                .builder()
                .id(comment.getId())
                .user_id(comment.getUser().getId())
                .variation_id(comment.getVariation().getId())
                .variation_name(comment.getVariation().getName())
                .commentDescription(comment.getCommentDescription())
                .dateCreated(comment.getDateCreated().toString())
                .verified(comment.getVerified() ? true : false)
                .deleted(comment.isDeleted() ? true : false)
                .primaryImageUrl(comment.getVariation().getPrimaryImageUrl())
                .productName(comment.getVariation().getProduct().getName())
                .userName(comment.getUser().getName() + " " +  comment.getUser().getSurname())
                .userEmail(comment.getUser().getEmail())
                .build();
    }

    public static List<CommentDTO> listToDtoList(List<Comment> comments){
        return comments
                .stream()
                .map(comment -> entityToDto(comment))
                .collect(Collectors.toList());
    }

}
