package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardServiceImplTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void pagelistTest2() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        //필요한 값은 size 없어도 10 page 없어도 1, type, keyword
        // keyword가 없을 때, 있을 때,
        // type가 t일때 제목을 검색하고 // 내부에서는 like를 쓴다.
        // 현재 페이지를 적용하지 않는다면 1페이지
        // 적용한다면 해당페이지
        pageRequestDTO.setPage(3);

        PageResponseDTO<BoardDTO> pageResponseDTO =
                boardService.pagelist(pageRequestDTO);

        //html에 출력할때 값을
        for(BoardDTO boardDTO : pageResponseDTO.getDtoList()) {

            log.info(boardDTO);

        }

        //현재페이지
        int page =
                pageResponseDTO.getPage();
        log.info("현재 페이지 : " + page);
        //시작페이지, 끝페이지, 이전여부, 다음여부
        log.info("끝 페이지 : " + pageResponseDTO.getEnd());
        log.info("시작 페이지 : " + pageResponseDTO.getStart());
        log.info("이전여부 : " + pageResponseDTO.isPrev());
        log.info("다음여부 : " + pageResponseDTO.isNext());


    }

    @Test
    public void pagelistTest() {
        // pageRequestDTO에는
        // 컨트롤러에서 파라미터 수집으로 page를 받는다면
        // page를 받을것이고 못받는다면 기본값 page = 1
        // 컨트롤러에서 받음
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(17);

        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO =
                boardService.pagelist(pageRequestDTO);

        log.info("페이지리스폰스DTO에 있는 page : " + boardDTOPageResponseDTO.getPage());
        log.info("엔드페이지 : " + boardDTOPageResponseDTO.getEnd());
        log.info("시작페이지 : " + boardDTOPageResponseDTO.getStart());
        log.info("이전페이지여부 : " + boardDTOPageResponseDTO.isPrev());
        log.info("다음페이지여부 : " + boardDTOPageResponseDTO.isNext());
        log.info("데이터-------------------------------");
        boardDTOPageResponseDTO.getDtoList().forEach(boardDTO -> log.info(boardDTO));

        List<BoardDTO> boardDTOList =
                boardDTOPageResponseDTO.getDtoList();


    }

    @Test
    public void testest() {

        //먼저 가져올값 boardRepository
        // 그냥 다 가져오기

        List<Board> boardList =
        boardRepository.findAll();

        log.info("레포지토리 findAll 페이징 미처리");
        boardList.forEach(board -> log.info(board));

        //페이징처리
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno"));

        Page<Board> boardPage =
        boardRepository.findAll(pageable);

        log.info("레포지토리 findAll 페이징 처리 정렬처리");
        int totalpages = boardPage.getTotalPages(); //총페이지수
        long total = boardPage.getTotalElements();  //총 게시물 수
        log.info(totalpages);
        log.info(total);


    }

    @Test
    public void test1() {
        Pageable pageable = PageRequest
                .of(1, 10, Sort.by("bno").descending());

        Page<Board> boardPage =
                boardRepository.findAll(pageable);
        //페이지어블을 파라미터로 넣은 findAll 은
        // select * from board order by bno desc
        // limit 10, 10;
        List<Board> boardList =
        boardPage.getContent(); //쿼리문의 수행결과 받는 타입은 List<Board>

        log.info("엔티티의 결과");
        boardList.forEach(board -> log.info(board));

        long total =
                boardPage.getTotalElements();   // 게시물 총수

        int totalpage =
                boardPage.getTotalPages();  // 페이지 총수

        ModelMapper modelMapper = new ModelMapper();
        //entityToDto
        List<BoardDTO> boardDTOList =
        boardList.stream()
                .map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        //dto 확인
        boardDTOList.forEach(boardDTO -> log.info(boardDTO));

        int page = 1;
        int size = 10;

        //long total 총 게시물수는 위에 있다.
        int end = (int)(Math.ceil(page / 10.0)) * 10;   //페이지 마지막 출력번호
        log.info(" 현재 " + page + "페이지 10개 사이즈 그래서 마지막 값은 : ? " + end);


        int start = end -9;
        // 만약에 마지막 페이지가 32페이지라고 하면
        // 현재 페이지가 31이다 그럼 지금까지 나온 end 페이지는 40이 될 것이다.
        // 그런데 실제로는 32까지밖에 없으니 그 두 값을 비교한다. 실제 마지막 페이지를 계산한다.
        int last = (int) Math.ceil(total / (double) size);
        log.info("실제 마지막 페이지 : " + last);

        if (end > last) {
            end = last;
        } else  {
            end = end;
        }
        log.info("실제값과 비교해서 나온 진짜 마지막 페이지는 ? : " + end);

        boolean prev =  start > 1;

        boolean next = total > end * size;

        log.info("출력");
        if (prev) {
            System.out.print("[[이전페이지로]]");
        }

        for (int i = start; i <= end; i++) {
            System.out.print("  [[" + i + "]]");
        }

        if (next) {
            System.out.print("[다음페이지로]");
        }

    }

}