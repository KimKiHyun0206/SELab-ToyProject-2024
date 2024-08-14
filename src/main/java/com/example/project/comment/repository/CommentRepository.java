package com.example.project.comment.repository;

import com.example.project.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //boardId 통해서 찾고 코멘트를 먼저생긴 순으로 찾는다.
    List<Comment> findByBoardIdOrderByCreatedAt(Long boardId);
}
