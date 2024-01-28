package com.ewmservice.comment.storage;

import com.ewmservice.comment.Comment;
import com.ewmservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentStorage {
    @Autowired
    CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment is not found"));
    }

    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getMyComment(Integer userId) {
        return commentRepository.findAllByCommentatorId(userId);
    }

    public List<Comment> getEventComments(Integer eventId) {
        return commentRepository.findAllByEventId(eventId);
    }
}
