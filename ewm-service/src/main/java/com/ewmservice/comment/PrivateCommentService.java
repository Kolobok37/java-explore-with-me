package com.ewmservice.comment;

import com.ewmservice.comment.dto.CommentDto;
import com.ewmservice.comment.dto.MapperComment;
import com.ewmservice.exception.UpdateEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface PrivateCommentService {
     ResponseEntity<CommentDto> createComment(Integer userId, Integer eventId, Comment comment) ;

     ResponseEntity<CommentDto> updateComment(Integer userId, Integer commentId, Comment newComment) ;

    ResponseEntity<List<CommentDto>> getMyComments(Integer userId) ;

     ResponseEntity<List<CommentDto>> getEventComments(Integer eventId) ;
}
