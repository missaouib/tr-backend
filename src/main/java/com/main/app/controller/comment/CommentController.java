package com.main.app.controller.comment;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.comment.CommentDTO;
import com.main.app.domain.model.comment.Comment;
import com.main.app.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.main.app.converter.comment.CommentConverter.entityToDto;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(entityToDto(commentService.createComment(commentDTO)), HttpStatus.OK);
    }

    @PostMapping("/{userId}/remove/{commentId}")
    public ResponseEntity<CommentDTO> removeComment(@PathVariable Long userId, @PathVariable Long commentId){
        return new ResponseEntity<>(entityToDto(commentService.removeComment(userId,commentId)),HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentDTO> editComment(@RequestBody CommentDTO commentDTO, @PathVariable Long id){
        return new ResponseEntity<>(entityToDto(commentService.updateComment(commentDTO,id)),HttpStatus.OK);
    }



    @PostMapping(path = "/{comment_id}/verify/{verify}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentDTO> verifyComment(@PathVariable Long comment_id, @PathVariable Boolean verify){
        return new ResponseEntity<>(entityToDto(commentService.verifyComment(comment_id,verify)),HttpStatus.OK);
    }

    @GetMapping(path = "/variation/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities> getAllCommentsForVariationId(@PathVariable Long id, Pageable pageable){
        return new ResponseEntity<>(commentService.getAllVariationUnVerifiedComments(id,pageable),HttpStatus.OK);
    }


    @GetMapping(path = "/verification")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entities> getAllUnverifiedComments(Pageable pageable){
        return new ResponseEntity<>(commentService.getCommentsForVerification(pageable),HttpStatus.OK);
    }





    @GetMapping(path = "/all/{id}/unverified")
    public ResponseEntity<Entities> getAllUnverifiedCommentsForUserId(Pageable pageable, @PathVariable Long id){
        return new ResponseEntity<>(commentService.getUsersUnverifiedComments(id,pageable),HttpStatus.OK);
    }

    @GetMapping(path = "/all/{id}/verified")
    public ResponseEntity<Entities> getAllVerifiedCommentsForUserId(Pageable pageable, @PathVariable Long id){
        return new ResponseEntity<>(commentService.getUsersVerifiedComments(id,pageable),HttpStatus.OK);
    }







}
