package com.example.boardproj.repository.search;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class BoardSearchImpl  extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;        // q 도메인 객체 entity를 Qboard로 바꾼것

        JPQLQuery<Board>  query = from(board);  //select * from board

        System.out.println(query); //select * from board
        System.out.println("-------------------------");
        // types에 있는 값을 검색하는데 있을때 없을때에 따라 동적으로 쿼리문을
        // 작성하고 싶다.
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if( types != null && types.length > 0 && keyword != null){
            for(String str  : types){
//                if(str.equals("t"))
                switch (str){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;

                }// swich
                //만약에 tc가 들어왔다면 where문 이후  title like %키워드% or content like %키워드%

            }//for문

        }// it문
        //검색조건까지
        query.where(booleanBuilder); //검색조건완료
        System.out.println(query);
        System.out.println("----------------------------");

        query.where(board.bno.gt(0L));   // select * from board //   // board.bno > 0

        System.out.println(query);
        System.out.println("----------------------------");

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);
        //데이터 List<T>
        List<Board> boardList = query.fetch();

        // 총게시물수 row수
        long count =
        query.fetchCount();

        //return
        return new PageImpl<>(boardList, pageable, count);

    }

    public Page<BoardDTO> searchAll1(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;        // q 도메인 객체 entity를 Qboard로 바꾼것

        JPQLQuery<Board>  query = from(board);  //select * from board

        System.out.println(query); //select * from board
        System.out.println("-------------------------");
        // types에 있는 값을 검색하는데 있을때 없을때에 따라 동적으로 쿼리문을
        // 작성하고 싶다.
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if( types != null && types.length > 0 && keyword != null){
            for(String str  : types){
//                if(str.equals("t"))
                switch (str){
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;

                }// swich
                //만약에 tc가 들어왔다면 where문 이후  title like %키워드% or content like %키워드%

            }//for문

        }// it문
        //검색조건까지
        query.where(booleanBuilder); //검색조건완료
        System.out.println(query);
        System.out.println("----------------------------");

        query.where(board.bno.gt(0L));   // select * from board //   // board.bno > 0

        System.out.println(query);
        System.out.println("----------------------------");

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);
        //데이터 List<T>
        List<Board> boardList = query.fetch();


        // 총게시물수 row수
        long count =
                query.fetchCount();

        //return
        return new PageImpl<>(        boardList.stream().map( board1 -> new ModelMapper().map(board1, BoardDTO.class)).collect(Collectors.toList()), pageable, count);

    }
}
