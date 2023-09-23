package com.seulseul.seulseul.dto.firebase;

import lombok.*;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class FCMDto {
    private String token;
}
