package com.example.project.solution.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.solution.dto.response.SolutionRecordResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRecord extends BaseEntity implements Domain<SolutionRecordResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "solution_id")
    private Long solutionId;

    private String code;
    private Boolean successOrNot;

    @Builder
    public SolutionRecord(Long userId, Long solutionId, String code, Boolean successOrNot) {
        this.userId = userId;
        this.solutionId = solutionId;
        this.code = code;
        this.successOrNot = successOrNot;
    }

    @Override
    public SolutionRecordResponse toResponseDto() {
        return new SolutionRecordResponse(
                this.id,
                this.userId,
                this.solutionId,
                this.code,
                this.successOrNot
        );
    }
}
