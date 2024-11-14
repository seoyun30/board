package com.example.boardproj.repository.search;

import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Log4j2
class BoardSearchImplTest {

    @Autowired
    BoardRepository boardRepository;



    @Test
    public void bobobo(){


        boardRepository.searchAll1(new PageRequestDTO().getTypes(),new PageRequestDTO().getKeyword(),new PageRequestDTO().getPageable())
                .forEach(boardDTO ->  log.info(boardDTO));


    }


}