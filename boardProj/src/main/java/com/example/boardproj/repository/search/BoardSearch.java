package com.example.boardproj.repository.search;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BoardSearch {
    //동적 검색기능을 추가한다.
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);


    Page<BoardDTO> searchAll1(String[] types, String keyword, Pageable pageable);
}
