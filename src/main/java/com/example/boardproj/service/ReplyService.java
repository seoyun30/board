package com.example.boardproj.service;

import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyService {
    //등록
    public void register(ReplyDTO replyDTO);     //dto로 들어가서 저장할때 entity

    //읽기
    public ReplyDTO read(long rno);

    //목록
    public List<ReplyDTO> list();

    public Page<ReplyDTO> listpage(Long bno, Pageable pageable);

    // 목록 페이징 처리, 일반 컨트롤러 일때

    public int  totalEl();



    //목록 페이징 처리 restController 일때
    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long bno);
    //수정
    public ReplyDTO update(ReplyDTO replyDTO);

    //삭제
    public void remove(Long rno);









}
