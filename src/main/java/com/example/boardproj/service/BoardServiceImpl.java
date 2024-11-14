package com.example.boardproj.service;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.repository.BoardImgRepository;
import com.example.boardproj.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private FileService fileService = new FileService();
    private final BoardImgService boardImgService;


    @Override
    public void register(BoardDTO boardDTO) {
        log.info("등록 서비스 들어온값: "+boardDTO);
        //글을 컨트롤러로부터 받아서 entity변환해서 저장한다.
        Board board =
                modelMapper.map(boardDTO, Board.class);

        boardRepository.save(board);

    }

    @Override
    public void register(BoardDTO boardDTO, MultipartFile[] multipartFile) {

        log.info("등록 서비스 들어온값: "+boardDTO);
        log.info("등록 서비스 들어온값: "+multipartFile);
        //글을 컨트롤러로부터 받아서 entity변환해서 저장한다.
        Board board =
                modelMapper.map(boardDTO, Board.class);
        log.info("저장전에 boardDTO를 board로 변경함"+board);

        board=
                boardRepository.save(board);
        log.info("저장후에 결과를 가지고 있는 board"+ board);
        // 본문을 저장하고 나서 사진을 저장합니다.
        // FileService 저장
        // 저장할 때 필요한 내용은 multipartFile

        if (multipartFile != null){
            for (MultipartFile file: multipartFile){
                if (!file.isEmpty()){

                    log.info("사진이 저장됩니다.");
                    boardImgService.boardImgregister(board.getBno(), file);
                }

            }

        }


    }

    @Override
    public BoardDTO read(Long bno) {
        log.info("서비스 읽기로 들어온값 : " +  bno);
        Optional<Board> optionalBoard =
                boardRepository.findById(bno);
        Board board = optionalBoard.orElseThrow(EntityNotFoundException::new);

        BoardDTO boardDTO =
                modelMapper.map( board, BoardDTO.class );

        log.info("서비스에서 컨트롤러로 나간값 :  " +boardDTO);

        return boardDTO;
    }

    @Override
    public List<BoardDTO> list() {

        List<Board> boardList =
                boardRepository.findAll();
        boardList.forEach(  board -> log.info(board));

        List<BoardDTO> boardDTOList =
                boardList.stream().map( board -> modelMapper.map(board, BoardDTO.class)  )
                        .collect(Collectors.toList());

        boardDTOList.forEach(boardDTO -> log.info(boardDTO));


        return boardDTOList;
    }

    @Override
    public PageResponseDTO<BoardDTO> pagelist(PageRequestDTO pageRequestDTO) {

//        Pageable pageable = PageRequest
//                .of(page-1, size, Sort.by("bno").descending());


        log.info(pageRequestDTO);
        Page<Board> boardPage = null;
        Pageable pageable = pageRequestDTO.getPageable("bno");  //위의 주석 내용과 같은 개념(PageRequestDTO에 사용)
        log.info(pageRequestDTO);

        if(pageRequestDTO.getType() == null || pageRequestDTO.getKeyword()==null || pageRequestDTO.getKeyword().equals("")){
            boardPage =  boardRepository.findAll(pageable);

        }else if(pageRequestDTO.getType().equals("t")){
            log.info( "제목으로 검색 검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.findByTitleContaining(pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("c")){
            log.info( "내용으로 검색 검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.findByContentContaining(pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("w")){
            log.info( "작성자로 검색으로  검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.selectlikeWriter(pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("tc")){
            log.info( "제목 + 내용중에 검색  검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.titleOrCon(pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("cw")){
            log.info( "내용 + 작성자으로  검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.findByContentContainingOrWriterContaining(pageRequestDTO.getKeyword(),pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("tw")){
            log.info( "제목 + 작성자 검색 검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.findByTitleContainingOrWriterContaining(pageRequestDTO.getKeyword(),pageRequestDTO.getKeyword(), pageable);

        }else if(pageRequestDTO.getType().equals("tcw")){
            log.info( "제목 + 내용 + 작성자으로 검색 검색키워드는"  +pageRequestDTO.getKeyword() );
            boardPage =  boardRepository.titleOrConOrWr(pageRequestDTO.getKeyword(), pageable);

        }

        //변환
        List<Board> boardList = boardPage.getContent();
        //dto변환
        List<BoardDTO> boardDTOList=
                boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                        .collect(Collectors.toList());

        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO
                = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total((int)boardPage.getTotalElements())
                .build();


        //문제발생
        //페이징 처리는 내부적으로 되었으나
        //사용자에게 보여주려면 1~10까지 버튼과 혹은
        //21~30까지의 버튼을 알아야한다. 그 값 또한 같이 넘겨야해서
        //RequestPageDTO를 이용해서 사용


        return boardDTOPageResponseDTO;
    }

    @Override
    public PageResponseDTO<BoardDTO> pageListsearchdsl(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Pageable pageable = pageRequestDTO.getPageable("bno");  //위의 주석 내용과 같은 개념(PageRequestDTO에 사용)
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();

        Page<Board> boardPage = boardRepository.searchAll(types, keyword, pageable);

        //변환
        List<Board> boardList = boardPage.getContent();
        //dto변환
        List<BoardDTO> boardDTOList=
                boardList.stream().map(board -> modelMapper.map(board, BoardDTO.class))
                        .collect(Collectors.toList());


        log.info(pageRequestDTO);


        PageResponseDTO<BoardDTO> boardDTOPageResponseDTO
                = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(boardDTOList)
                .total((int)boardPage.getTotalElements())
                .build();

        return boardDTOPageResponseDTO;
    }


    @Override
    public void update(BoardDTO boardDTO, MultipartFile[] multipartFiles, Long[] delino) {
        log.info("수정 서비스 들어온값 : " + boardDTO);
        //들어온값중에 수정할 데이터의 pk번호가 있다.

        Optional<Board> optionalBoard =
                boardRepository.findById(boardDTO.getBno());

        Board board =
                optionalBoard.orElseThrow(EntityNotFoundException::new);

        log.info(board);

        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());


        //파일 삭제
        if(delino != null && !delino[0].equals("")){
            log.info("업데이트포스 " + Arrays.toString(delino));
            for (Long ino : delino) {
                boardImgService.del(ino);
            }
        }

        //파일등록
        if(multipartFiles != null && multipartFiles.length > 0){

            for(MultipartFile multipartFile : multipartFiles ){
                if(multipartFile != null && !multipartFile.getOriginalFilename().equals("")){
                    log.info(" 여기 사진이 있어요");
                    log.info("업데이트 서비스 새로등록하는 파일 " + multipartFile.getOriginalFilename());

                    boardImgService.boardImgregister(boardDTO.getBno(), multipartFile);
                }

            }


        }
//
//            if(delino !=null && !delino[0].equals("") ){
//                log.info("업데이트포스 " + Arrays.toString(delino));
//
//                //사진삭제
//                for (Long ino  : delino){
//                    boardImgService.del(ino);
//                }
//            }

    }

    @Override
    public void del(Long bno) {

        log.info("삭제로 들어온 값 :" + bno);


        boardRepository.deleteById(bno);

    }
}

