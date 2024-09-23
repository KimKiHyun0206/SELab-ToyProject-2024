package com.example.project.solution.dto.response;

import lombok.Data;

@Data
class ExampleResponse {
    private String inputExample;
    private String outputExample;

    public ExampleResponse(String inputExample, String outputExample) {
        this.inputExample = inputExample;
        this.outputExample = outputExample;
    }
}