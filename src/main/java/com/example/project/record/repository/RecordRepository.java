package com.example.project.record.repository;

import com.example.project.record.domain.Record;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.record.dto.RecordResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUserId(Long userId);
    List<Record> findByUserIdAndSolutionId(Long userId, Long solutionId);

    @Query("SELECT new com.example.project.record.dto.RecordResponse(sr.id, u.id, s.id, sr.codeLanguage ,sr.code, sr.successOrNot) " +
            "FROM Record sr " +
            "JOIN User u ON u.id = sr.userId " +
            "JOIN Solution s ON s.id = sr.solutionId")
    List<RecordResponse> findSolutionRecord();

    @Query("SELECT new com.example.project.solution.dto.response.list.AuthSolutionListResponse(s.difficulty, s.title, s.solved, sr.successOrNot)" +
            "FROM Record sr " +
            "JOIN Solution s on s.id = sr.solutionId " +
            "WHERE sr.userId = :id")
    List<AuthSolutionListResponse> eachUserSolutionResponse(@Param("id") Long id);
}
