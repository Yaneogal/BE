package com.sparta.finalproject6.dto.responseDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

   private String message;
   private String path;
   private List<FieldError> errors;
   private LocalDateTime timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.path = "null";
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String path, List<FieldError> errors) {
        this.message = "유효성 검사 실패!";
        this.path = path;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

    public static ErrorResponse of(String path, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(f -> FieldError.builder()
                        .field(f.getField())
                        .value(f.getRejectedValue() == null ? "null" : f.getRejectedValue().toString())
                        .reason(f.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        return new ErrorResponse(path, fieldErrors);
    }

}
