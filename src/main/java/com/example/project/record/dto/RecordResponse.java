package com.example.project.record.dto;

import com.example.project.record.domain.vo.CodeLanguage;
import com.example.project.record.domain.Record;
import com.example.project.restrictions.ResponseDto;
import lombok.Builder;
import lombok.Data;

@Data
public class RecordResponse implements ResponseDto<Record> {
    private Long id;
    private Long userId;
    private Long solutionId;
    private CodeLanguage codeLanguage;
    private String code;
    private Boolean successOrNot;

    @Builder
    public RecordResponse(Long id, Long userId, Long solutionId, CodeLanguage codeLanguage, String code, Boolean successOrNot) {
        this.id = id;
        this.userId = userId;
        this.solutionId = solutionId;
        this.code = code;
        this.successOrNot = successOrNot;
        this.codeLanguage = codeLanguage;
    }

    @Override
    public Record toEntity() {
        return new Record(
                this.id,
                this.userId,
                this.solutionId,
                this.codeLanguage,
                this.code,
                this.successOrNot
        );
    }
}