package com.ewmservice.comment;


import com.ewmservice.comment.dto.CommentDto;
import com.ewmservice.comment.dto.MapperComment;
import com.ewmservice.comment.storage.CommentStorage;
import com.ewmservice.event.storage.EventStorage;
import com.ewmservice.exception.UpdateEntityException;
import com.ewmservice.user.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PrivateCommentServiceImpl implements PrivateCommentService {
    @Autowired
    CommentStorage commentStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    EventStorage eventStorage;

    public ResponseEntity<CommentDto> createComment(Integer userId, Integer eventId, Comment comment) {
        comment.setCommentator(userStorage.getUser(userId));
        comment.setEvent(eventStorage.getEvent(eventId));
        comment.setCreated(LocalDateTime.now());
        return new ResponseEntity<>(MapperComment.mapToCommentDto(commentStorage.createComment(comment)),
                HttpStatus.CREATED);
    }

    public ResponseEntity<CommentDto> updateComment(Integer userId, Integer commentId, Comment newComment) {
        Comment comment = commentStorage.getComment(commentId);
        if (!Objects.equals(comment.getCommentator().getId(), userId)) {
            throw new UpdateEntityException("You don't change this comment.");
        }
        comment.setText(newComment.getText());
        return new ResponseEntity<>(MapperComment.mapToCommentDto(commentStorage.updateComment(comment)),
                HttpStatus.OK);

    }

    public ResponseEntity<CommentDto> getComment(Integer commentId) {
        Comment comment = commentStorage.getComment(commentId);
        return new ResponseEntity<>(MapperComment.mapToCommentDto(comment),
                HttpStatus.OK);
    }

    public ResponseEntity<List<CommentDto>> getMyComments(Integer userId) {
        List<Comment> comments = commentStorage.getMyComment(userId);
        return new ResponseEntity<>(comments.stream().map(MapperComment::mapToCommentDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    public ResponseEntity<List<CommentDto>> getEventComments(Integer eventId) {
        List<Comment> comments = commentStorage.getEventComments(eventId);
        return new ResponseEntity<>(comments.stream().map(MapperComment::mapToCommentDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
