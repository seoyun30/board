package com.example.boardproj.repository;

import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.Reply;
import com.example.boardproj.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, BoardSearch {


    public Page<Reply> findByBoardBno (Long bno, Pageable pageable);
    public List<Reply> findByBoardBno (Long bno);


}
