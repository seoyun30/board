package com.example.boardproj.service;

import com.example.boardproj.dto.BoardImgDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.BoardImg;
import com.example.boardproj.repository.BoardImgRepository;
import com.example.boardproj.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardImgService {

    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final ModelMapper modelMapper;

    private final FileService fileService;

    @Value("${imgLocation}")
    private String imgLocation;

    public void boardImgregister(Long bno, MultipartFile multipartFile){
        //원래 이름 가져오기 // edge
        String originalFilename=
        multipartFile.getOriginalFilename();
        //파일명에 담긴 경로 삭제
        //"file:///C:/uplodad/a.png" 예시

        //파일이름을 앞에서 자를때
        // originalFilename.substring(originalFilename.indexOf(":"));

        String imgName= //파일이름을 뒤에서 자를대
        originalFilename.substring(originalFilename.lastIndexOf("\\")+1);

        //특별한 난수
        UUID uuid = UUID.randomUUID();
        log.info("생성된 uuid"+uuid);
        //중돕되지 않도록 파일이름을 변경해서 저장 DB, 물리적 파일 둘다
        String newImgName=
                uuid + "_" + imgName;

        Board board =
                boardRepository.findById(bno).orElseThrow(EntityNotFoundException::new);
        //참조 대상인 board를 가지고 boardImg를 만들어 저장

        BoardImg boardImg = new BoardImg();
        boardImg.setBoard(board); //참조하는 글번호
                                        //C:/upload + "//"+ "board" 불러올때 사용

        boardImg.setImgPath(imgLocation);  //저장 경로 //각 경로board notic member등은
                                //fileservice를 만들때부터 파라미터로 받도록한다.
        boardImg.setNewImgName(newImgName); //새로운 이름
        boardImg.setImgName(imgName);  //사진 이름

        //db저장
        boardImgRepository.save(boardImg);

        //물리적인 파일 저장
        fileService.register(multipartFile, newImgName);

    }
    public List<BoardImgDTO> boardImgread(Long bno){
       //select*from img whrer img.board.bno=:bno
        List<BoardImg> boardImgList=
        boardImgRepository.findByBoardBno(bno);

        List<BoardImgDTO> boardImgDTOList=
                boardImgList.stream().map(
                boardImg -> modelMapper.map(boardImg, BoardImgDTO.class)
        ).collect(Collectors.toList());

        return boardImgDTOList;
    }

    public void del(Long ino){

        //db정보 찾아오기//사진정보찾아오기
        BoardImg boardImg=
        boardImgRepository.findById(ino).get();

        //경로가져오기
        String path=
        imgLocation +"\\"+ boardImg.getNewImgName();
        fileService.delFile(path); //물리적 데이터 삭제

        boardImgRepository.deleteById(ino); //디비삭제
    }


}
