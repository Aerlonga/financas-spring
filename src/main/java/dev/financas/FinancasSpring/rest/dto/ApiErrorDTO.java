package dev.financas.FinancasSpring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ApiErrorDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private List<String> validationErrors;
}