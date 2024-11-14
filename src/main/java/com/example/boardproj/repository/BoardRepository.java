package com.example.boardproj.repository;

import com.example.boardproj.entity.Board;
import com.example.boardproj.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository  extends JpaRepository<Board , Long>, BoardSearch {

    //제목으로 검색
    // select * from board where title like '%파라미터%'
    public Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    //내용으로 검색   // 쿼리메소드

    public Page<Board> findByContentContaining(String keyword, Pageable pageable);

    //작성자 검색
    @Query("select b from Board b where b.writer like concat('%', :str, '%') ")
    public Page<Board> selectlikeWriter(String str, Pageable pageable);

    @Query("select b from Board b where b.title like concat('%', :str, '%') or b.content like concat('%', :str, '%') ")
    public Page<Board> titleOrCon(String str, Pageable pageable);

    public Page<Board> findByContentContainingOrWriterContaining(String str, String str1, Pageable pageable);

   public Page<Board> findByTitleContainingOrWriterContaining(String str, String str1, Pageable pageable);

    @Query("select b from Board b where b.title like concat('%', :str, '%') or b.content like concat('%', :str, '%') or b.writer like concat('%', :str, '%') ")
    public Page<Board> titleOrConOrWr(String str, Pageable pageable);
}
