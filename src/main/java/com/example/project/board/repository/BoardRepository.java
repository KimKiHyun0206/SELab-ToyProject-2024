package com.example.project.board.repository;

import com.example.project.board.domain.Board;
import com.example.project.board.dto.SolutionBoardResponse;
import com.example.project.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitle(String title);

    List<Board> findBoardsBySolutionId(Long SolutionId);

    @Query(
            "SELECT new com.example.project.board.dto.SolutionBoardResponse(b.title, b.context, u.name, b.solutionId) " +
                    "FROM Board b " +
                    "JOIN User u on u.id = b.userId"

    )
    SolutionBoardResponse readBoards(@Param("boardId") Long boardId);
}