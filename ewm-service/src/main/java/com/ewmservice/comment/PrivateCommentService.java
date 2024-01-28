package com.ewmservice.comment;

import com.ewmservice.comment.dto.CommentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PrivateCommentService {
    ResponseEntity<CommentDto> createComment(Integer userId, Integer eventId, Comment comment);

    ResponseEntity<CommentDto> updateComment(Integer userId, Integer commentId, Comment newComment);

    ResponseEntity<List<CommentDto>> getMyComments(Integer userId);

    ResponseEntity<List<CommentDto>> getEventComments(Integer eventId);
}
