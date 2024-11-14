package com.example.boardproj.repository;

import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.entity.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;


    @Test
    public void searchTest(){
        //type t, c, w   if(type== tc )
        // t와 c 분리하고 하나하나 다 찾음 t라면 and t
        // c라면 and c를 붙여주고 처음에는 and를 뺀다
        // t = keyword and c = keyword라는 쿼리문을
        //만들고 싶은것
        // String type = "tcw"
        // 를 String[] types = {"t", "c", "w"}

        String[] types = {"f"};
        String keyword = "제목";
        LocalDate localDate = LocalDate.now().minusDays(1L);
        Pageable pageable = PageRequest
                .of(0, 10, Sort.by("bno").descending());


        Page<Board> boardPage =
        boardRepository.searchAll(types , keyword, pageable);

        boardPage.getContent().forEach(a -> log.info(a));






    }



    @Test
    public void dummy() {

        for (int i = 0; i < 300; i++) {
            Board board = new Board();
            board.setTitle("제목" + i);
            board.setContent("내용" + i);
            board.setWriter("작성자" + i);

            boardRepository.save(board);
        }
    }

    @Test
    public void listpagetest() {
        int page = 1;       //우리가 보는 페이지
        int size = 10;

        Pageable pageable = PageRequest
                .of(page - 1, size, Sort.by("bno").descending());


        Page<Board> boardList =
        boardRepository.findAll(pageable);

        // 페이지 총개수
        log.info(boardList.getTotalPages());

        //총 게시물 수
        Long ecount =
                boardList.getTotalElements();
        log.info("총 게시물 수 : " + ecount);

        //그렇게 페이징처리된 정보 가져오기 List<e>
        //이거 지금 Page<Board>
        List<Board> list =
                boardList.getContent();

        list.forEach(board -> log.info(board));

    }

}