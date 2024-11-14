package com.example.boardproj.controller;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.entity.Board;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class RestSampleController {

    @GetMapping("/a")
    public String a(String a){

        log.info(a);

        return "홍길동";
    }

    @PostMapping("/c")
    public String c(String title){

        log.info(title);
        title = title + "환영합니다.";

        return title;
    }

    @PutMapping("/d")
    public String d(String title){

        log.info(title);
        title = title+ 33333;

        return title;
    }

    @GetMapping ("/e")
    public String e(BoardDTO boardDTO){
        log.info(boardDTO);

        return boardDTO.getContent();
    }

    @PostMapping("/f")
    public String f(BoardDTO boardDTO){
        log.info(boardDTO);

        return boardDTO.getContent();
    }

    @PostMapping("/z")
    public ResponseEntity z( BoardDTO boardDTO){
        log.info(boardDTO);

        if(boardDTO.getTitle().equals("신짱구")){
            return new ResponseEntity<String>("너님 잘못함", HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<BoardDTO>(boardDTO, HttpStatus.OK);
    }

}
