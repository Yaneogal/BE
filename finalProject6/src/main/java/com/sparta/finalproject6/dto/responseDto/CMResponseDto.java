package com.sparta.finalproject6.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CMResponseDto {

    private String message;
    private Map<String, String> errorMap;

    public CMResponseDto(String message) {
        this.message = message;
    }
}
