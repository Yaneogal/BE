package com.sparta.finalproject6.dto.responseDto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private Integer statusCode;
    private String message;
    private HttpStatus status;
    private Long KakaoId;
}
