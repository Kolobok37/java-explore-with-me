package com.ewmservice.comment.storage;

import com.ewmservice.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findAllByCommentatorId(Integer userId);

    List<Comment> findAllByEventId(Integer eventId);
}
