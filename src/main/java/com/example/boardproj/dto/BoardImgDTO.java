package com.example.boardproj.dto;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BoardImgDTO {

    private Long ino;

    private String imgPath;

    private String imgName;

    private String newImgName;

    private BoardDTO boardDTO;


}
