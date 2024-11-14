package com.example.boardproj.controller;

import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import com.example.boardproj.service.ReplyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/reply")
public class RestReplyController {

    private final ReplyService replyService;

    @PostMapping("/register")
    public ResponseEntity replyregister(@Valid  ReplyDTO replyDTO, BindingResult bindingResult)  {

        log.info("댓글 들어온 값 :" + replyDTO);

        if (bindingResult.hasErrors()){
            log.info("에러가 있음");
            log.info("에러가 있음");
            log.info("에러가 있음");
            log.info("에러가 있음");
            log.info("에러가 있음");
            log.info(bindingResult.getAllErrors());
            log.info(bindingResult.getAllErrors());

//            List<ObjectError> objectErrors = bindingResult.getAllErrors();
//            log.info("--------------------------------------");
//            objectErrors.forEach(objectError -> log.info(objectError));

            List<FieldError> fieldErrors=
            bindingResult.getFieldErrors();
            log.info("--------------------------------------");
            fieldErrors.forEach(a -> log.info(a));

            return new ResponseEntity<String>(fieldErrors.get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        if (replyDTO.getRno() == null || replyDTO.getRno().equals("")) {
            new ResponseEntity<String>("댓글값들이 안들어옴", HttpStatus.BAD_REQUEST);

        }
        //댓글저장
        try {
            replyService.register(replyDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<String>("입력하신 글에는 댓글이 없습니다.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<String>("저장되었습니다.", HttpStatus.OK);

    }

    @GetMapping("/list")
    public ResponseEntity list(Long bno, PageRequestDTO pageRequestDTO){

        log.info("댓글리스트"+bno);
        log.info(pageRequestDTO);

        if (bno == null || bno.equals("")){
            return new ResponseEntity<String>("요청값을 확인해주세요", HttpStatus.BAD_REQUEST);
        }

        PageResponseDTO<ReplyDTO> responseDTO =
        replyService.pageList(pageRequestDTO, bno);

        log.info(responseDTO);
        return new ResponseEntity<PageResponseDTO<ReplyDTO>>(responseDTO, HttpStatus.OK);


    }

    @GetMapping("/read/{rno}")
    public ResponseEntity list(@PathVariable("rno") Long rno){

        ReplyDTO replyDTO=
       replyService.read(rno);

        return new ResponseEntity<ReplyDTO>(replyDTO, HttpStatus.OK);


    }
    @PostMapping("/update")
    public ResponseEntity update(ReplyDTO replyDTO){

        try {
            replyService.update(replyDTO);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<String>("댓글 못 찾음", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("수정됨", HttpStatus.OK);


    }
    @PostMapping("/del")
    public ResponseEntity del(Long rno){
        log.info(rno);

       replyService.remove(rno);

        return new ResponseEntity<String>("삭제됨", HttpStatus.OK);
    }





}
