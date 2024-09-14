package com.example.project.solution.repository;

import com.example.project.solution.domain.vo.Difficulty;
import com.example.project.solution.domain.Solution;
import com.example.project.solution.dto.response.list.NonAuthSolutionListResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, JpaSpecificationExecutor<Solution> {
    //Page<Solution> findByDifficulty(Difficulty difficulty);
    @Query("SELECT new com.example.project.solution.dto.response.list.NonAuthSolutionListResponse(s.difficulty, s.title, s.solved) " +
            "FROM Solution s")
    List<NonAuthSolutionListResponse> getSolutions();
}
