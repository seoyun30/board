package com.example.boardproj.dto;

import com.example.boardproj.entity.Board;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {


    private Long rno;

    private Long bno;


    private String replyText;

    private String writer ;     //이것도 나중에 게시판도 나중에 회원을 참조하자

    private BoardDTO boardDTO;    //dto는 dto를


    //날짜

    private LocalDate regdate;

    private LocalDate updatedate;

    public ReplyDTO setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
        return this;
    }
}
