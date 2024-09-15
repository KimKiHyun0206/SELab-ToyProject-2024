package com.example.project.solution.dto.request.record;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.solution.domain.SolutionRecord;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolutionRecordRegisterRequest implements RegisterRequest<SolutionRecord> {
    @NotNull
    private Long userId;
    @NotNull
    private Long solutionId;
    @NotNull
    private String code;
    @NotNull
    private Boolean successOrNot;

    @Override
    public SolutionRecord toEntity() {
        return SolutionRecord.builder()
                .userId(userId)
                .solutionId(solutionId)
                .code(code)
                .successOrNot(successOrNot)
                .build();
    }
}