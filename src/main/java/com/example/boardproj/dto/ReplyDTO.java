package com.example.boardproj.dto;

import com.example.boardproj.entity.Board;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "댓글은 공백일수 없습니다.")
    @Size(min=2, max = 255, message = "댓글은 2-225글자 사이여야합니다.")
    private String replyText;

    @NotBlank(message = "작성자는 공백일수 없습니다.")
    @Size(min=2, max = 255, message = "댓글은 2-225글자 사이여야합니다.")
    private String writer; //이것도 나중에 게시판도 나중에 회원을 참조하자

    //@NotNull
    //@NotEmpty


    private BoardDTO boardDTO;    //dto는 dto를
    //날짜

    private LocalDate regdate;

    private LocalDate updatedate;

    public ReplyDTO setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
        return this;
    }
}
