package com.example.project.board.repository;

import com.example.project.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    //@Query("select a from board where a.solutionId = ?")
    //public Page<Board> findBySolutionId(Long solutionId);

    Optional<Board> findByTitle(String title);
}
