package com.main.app.service.comment;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.comment.CommentDTO;
import com.main.app.domain.model.comment.Comment;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.user_favourites.UserFavourites;
import com.main.app.domain.model.variation.Variation;
import com.main.app.repository.comment.CommentRepository;
import com.main.app.repository.user.UserRepository;
import com.main.app.repository.variation.VariationRepository;
import com.main.app.service.email.CommentRemovedEmailService;
import com.main.app.service.email.CommentVerifiedEmailService;
import com.main.app.static_data.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.comment.CommentConverter.listToDtoList;
import static com.main.app.converter.user_favourites.UserFavouritesConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.commentToIds;
import static com.main.app.util.Util.favouritesToIds;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${spring.deeplink}")
    private String deeplinkUrl;

    private final UserRepository userRepository;

    private final VariationRepository variationRepository;

    private final CommentRepository commentRepository;

    private final CommentVerifiedEmailService commentVerifiedEmailService;

    private final CommentRemovedEmailService commentRemovedEmailService;


    @Override
    public Comment createComment(CommentDTO commentDTO) {

        //TODO provjera da li je CurrentUser

        if(!userRepository.findOneByIdAndDeletedFalse(commentDTO.getUser_id()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }
        if(!variationRepository.findOneByIdAndDeletedFalse(commentDTO.getVariation_id()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST);
        }

        User user = userRepository.findOneByIdAndDeletedFalse(commentDTO.getUser_id()).get();
        Variation variation = variationRepository.findOneByIdAndDeletedFalse(commentDTO.getVariation_id()).get();

        List<Comment> allUsersComments = commentRepository.findAllByUserId(user.getId());

        for (Comment comment: allUsersComments) {
            if(comment.getCommentDescription().equals(commentDTO.getCommentDescription())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMMENT_DESCRIPTION_ALREADY_EXISTS);
            }
        }

        Comment comment = new Comment(user,variation,commentDTO.getCommentDescription(),false);

        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    @Override
    public Comment updateComment(CommentDTO commentDTO, Long id) {

        //TODO provjera da li je CurrentUser

        if(commentDTO.getUser_id() != null && commentDTO.getVariation_id() != null){

            if(!userRepository.findOneByIdAndDeletedFalse(commentDTO.getUser_id()).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
            }

            if(!variationRepository.findOneByIdAndDeletedFalse(commentDTO.getVariation_id()).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, VARIATION_NOT_EXIST);
            }
        }

        if(!commentRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMMENT_NOT_EXIST);
        }
        if(!commentRepository.findById(id).get().getVerified()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMMENT_NOT_VERIFIED);
        }

        Comment foundComment = commentRepository.findById(id).get();

       if(commentDTO.getCommentDescription() != null ){
           foundComment.setCommentDescription(commentDTO.getCommentDescription());
       }

        return commentRepository.save(foundComment);
    }

    @Override
    public Comment removeComment(Long user_id, Long comment_id) {

        //TODO provjera da li je CurrentUser

        if(!userRepository.findOneByIdAndDeletedFalse(user_id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_NOT_EXIST);
        }

        if(!commentRepository.findById(comment_id).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMMENT_NOT_EXIST);
        }

        List<Comment> allComments = commentRepository.findAllByUserId(user_id);
        Comment deletedComment = null;

        for (Comment comment:allComments) {
            if(comment.getId() == comment_id){
                comment.setDeleted(true);
                comment.setDateDeleted(Calendar.getInstance().toInstant());
                deletedComment = commentRepository.save(comment);
            }
        }

        if(deletedComment == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, COMMENT_TO_REMOVE_NOT_EXIST);
        }

        return deletedComment;
    }



    @Override
    public Entities getCommentsForVerification(Pageable pageable) {
        Page<Comment> pagedComments = commentRepository.findAllByVerifiedFalse(pageable);
        List<Long> ids = commentToIds(pagedComments);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Comment> comments = commentRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDtoList(comments));
        entities.setTotal(pagedComments.getTotalElements());

        return entities;
    }

    @Override
    public Entities getUsersUnverifiedComments(Long user_id, Pageable pageable) {

        Page<Comment> pagedComments = commentRepository.findAllByUserIdAndVerifiedFalse(user_id,pageable);
        List<Long> ids = commentToIds(pagedComments);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Comment> comments = commentRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDtoList(comments));
        entities.setTotal(pagedComments.getTotalElements());

        return entities;
    }

    @Override
    public Entities getUsersVerifiedComments(Long user_id, Pageable pageable) {
        Page<Comment> pagedComments = commentRepository.findAllByUserIdAndVerifiedTrue(user_id,pageable);
        List<Long> ids = commentToIds(pagedComments);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Comment> comments = commentRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDtoList(comments));
        entities.setTotal(pagedComments.getTotalElements());

        return entities;
    }

    @Override
    public Entities getAllVariationVerifiedComments(Long variation_id, Pageable pageable) {

        Page<Comment> pagedComments = commentRepository.findAllByVariationIdAndVerifiedTrue(variation_id,pageable);
        List<Long> ids = commentToIds(pagedComments);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Comment> comments = commentRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDtoList(comments));
        entities.setTotal(pagedComments.getTotalElements());

        return entities;

    }

    @Override
    public Entities getAllVariationUnVerifiedComments(Long variation_id, Pageable pageable) {

        Page<Comment> pagedComments = commentRepository.findAllByVariationId(variation_id,pageable);            //AndVerifiedFalse
        List<Long> ids = commentToIds(pagedComments);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<Comment> comments = commentRepository.findAllByIdInAndDeletedFalse(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDtoList(comments));
        entities.setTotal(pagedComments.getTotalElements());

        return entities;

    }



    @Override
    public Comment verifyComment(Long comment_id, Boolean verify) {

        Optional<Comment> commentOptional = commentRepository.findById(comment_id);

        if(!commentOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,COMMENT_NOT_EXIST);
        }

        Comment comment = commentOptional.get();
        Comment savedComment = null;

        if(verify){
            comment.setVerified(true);
            savedComment = commentRepository.save(comment);

            String urlPart = Constants.URL_COMMENT_ID;

            commentVerifiedEmailService.sendEmail(
                    deeplinkUrl,
                    "?commentId=" + comment_id,
                    emailFrom,
                    savedComment.getUser().getEmail(),
                    urlPart
            );
        }else{
            comment.setDeleted(true);
            comment.setDateDeleted(Calendar.getInstance().toInstant());
            comment.setVerified(false);
            savedComment = commentRepository.save(comment);

            commentRemovedEmailService.sendEmail(
                    emailFrom,
                    savedComment.getUser().getEmail(),
                    savedComment.getCommentDescription()
            );

        }

        return savedComment;
    }


}
