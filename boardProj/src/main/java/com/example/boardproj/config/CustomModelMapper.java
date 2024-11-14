package com.example.boardproj.config;

import com.example.boardproj.dto.BoardDTO;
import org.modelmapper.ModelMapper;

public class CustomModelMapper extends ModelMapper {

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
//        BoardDTO boardDTO =
//        modelMapper.map(board, BoardDTO.class);
        if (source == null) {
            return null;
        }

        return super.map(source, destinationType);

    }
}
