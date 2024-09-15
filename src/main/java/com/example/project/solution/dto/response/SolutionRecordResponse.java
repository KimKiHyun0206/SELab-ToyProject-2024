package com.example.project.solution.dto.response;

import com.example.project.restrictions.ResponseDto;
import com.example.project.solution.domain.SolutionRecord;
import com.example.project.solution.domain.vo.CodeLanguage;
import lombok.Builder;
import lombok.Data;

@Data
public class SolutionRecordResponse implements ResponseDto<SolutionRecord> {
    private Long id;
    private Long userId;
    private Long solutionId;
    private CodeLanguage codeLanguage;
    private String code;
    private Boolean successOrNot;

    @Builder
    public SolutionRecordResponse(Long id, Long userId, Long solutionId,CodeLanguage codeLanguage, String code, Boolean successOrNot) {
        this.id = id;
        this.userId = userId;
        this.solutionId = solutionId;
        this.code = code;
        this.successOrNot = successOrNot;
        this.codeLanguage = codeLanguage;
    }

    @Override
    public SolutionRecord toEntity() {
        return new SolutionRecord(
                this.id,
                this.userId,
                this.solutionId,
                this.codeLanguage,
                this.code,
                this.successOrNot
        );
    }
}
