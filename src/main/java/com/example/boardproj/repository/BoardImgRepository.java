package com.example.boardproj.repository;

import com.example.boardproj.entity.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {

    public List<BoardImg> findByBoardBno (Long bno);


}