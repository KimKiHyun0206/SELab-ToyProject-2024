package com.example.project.solution.repository;

import com.example.project.solution.domain.SolutionRecord;
import com.example.project.solution.dto.response.SolutionRecordResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRecordRepository extends JpaRepository<SolutionRecord, Long> {
    List<SolutionRecord> findByUserId(Long userId);
    List<SolutionRecord> findByUserIdAndSolutionId(Long userId, Long solutionId);

    @Query("SELECT new com.example.project.solution.dto.response" +
            ".SolutionRecordResponse(sr.id, sr.userId, sr.solutionId, sr.code, sr.successOrNot) " +
            "FROM SolutionRecord sr " +
            "JOIN User u ON sr.userId = u.id " +
            "WHERE sr.userId = :id AND sr.solutionId = :solutionId")
    List<SolutionRecordResponse> findSolutionRecord(@Param("id") Long id,
                                                    @Param("solutionId") Long solutionId);

}
