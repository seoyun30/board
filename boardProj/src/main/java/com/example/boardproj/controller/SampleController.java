package com.example.boardproj.controller;

import com.example.boardproj.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
public class SampleController {

    @GetMapping("/sample")
    public String sample(){
        return "sample";
    }

    @PostMapping("/g")
    public @ResponseBody String f(@RequestBody BoardDTO boardDTO){
        log.info(boardDTO);

        return boardDTO.getContent();
    }

    @PostMapping("/h")
    public @ResponseBody BoardDTO h(@RequestBody BoardDTO boardDTO){
        log.info(boardDTO);

        return boardDTO;
    }
    @PostMapping("/i")
    public ResponseEntity i( BoardDTO boardDTO){
        log.info(boardDTO);

        if(boardDTO.getTitle().equals("신짱구")){
            return new ResponseEntity<String>("너님 잘못함", HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<BoardDTO>(boardDTO, HttpStatus.OK);
    }


}
