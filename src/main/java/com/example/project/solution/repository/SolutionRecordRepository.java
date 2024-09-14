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

    @Query("SELECT new com.example.project.solution.dto.response.SolutionRecordResponse(sr.id, u.id, s.id, sr.code, sr.successOrNot) FROM SolutionRecord sr JOIN User u ON u.id = sr.userId JOIN Solution s ON s.id = sr.solutionId")
    List<SolutionRecordResponse> findSolutionRecord(@Param("id") Long id,
                                                    @Param("solutionId") Long solutionId);


}
