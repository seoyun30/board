package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {
    //등록
    public void register(BoardDTO boardDTO);

    //읽기
    public BoardDTO read(Long bno);
    //목록
    public List<BoardDTO> list();

    //페이징처리된 목록
    public PageResponseDTO<BoardDTO> pagelist(PageRequestDTO pageRequestDTO);

    //페이징처리 ok 검색 동적처리 ok
    public PageResponseDTO<BoardDTO> pageListsearchdsl(PageRequestDTO pageRequestDTO);

    //수정
    public void update(BoardDTO boardDTO);

    //삭제
    public void del(Long bno);
}
