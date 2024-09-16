package com.example.project.record.domain;

import com.example.project.common.BaseEntity;
import com.example.project.restrictions.Domain;
import com.example.project.record.domain.vo.CodeLanguage;
import com.example.project.record.dto.RecordResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Record extends BaseEntity implements Domain<RecordResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "solution_id")
    private Long solutionId;
    @Enumerated
    private CodeLanguage codeLanguage;
    private String code;
    private Boolean successOrNot;

    @Builder
    public Record(Long userId, Long solutionId, String code, Boolean successOrNot, CodeLanguage codeLanguage) {
        this.userId = userId;
        this.solutionId = solutionId;
        this.code = code;
        this.successOrNot = successOrNot;
        this.codeLanguage = codeLanguage;
    }

    @Override
    public RecordResponse toResponseDto() {
        return new RecordResponse(
                this.id,
                this.userId,
                this.solutionId,
                this.codeLanguage,
                this.code,
                this.successOrNot
        );
    }
}
