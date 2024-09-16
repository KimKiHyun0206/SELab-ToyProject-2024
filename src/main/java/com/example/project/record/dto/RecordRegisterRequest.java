package com.example.project.record.dto;

import com.example.project.restrictions.RegisterRequest;
import com.example.project.record.domain.Record;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecordRegisterRequest implements RegisterRequest<Record> {
    @NotNull
    private Long userId;
    @NotNull
    private Long solutionId;
    @NotNull
    private String code;
    @NotNull
    private Boolean successOrNot;

    @Override
    public Record toEntity() {
        return Record.builder()
                .userId(userId)
                .solutionId(solutionId)
                .code(code)
                .successOrNot(successOrNot)
                .build();
    }
}