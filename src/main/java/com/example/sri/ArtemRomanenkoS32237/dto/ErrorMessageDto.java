package com.example.sri.ArtemRomanenkoS32237.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessageDto {
    private HttpStatus httpStatus;
    private LocalDateTime occurredOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
