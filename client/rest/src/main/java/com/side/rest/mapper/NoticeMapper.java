package com.side.rest.mapper;

import com.side.domain.model.Notice;
import com.side.rest.dto.request.NoticeRequestDto;
import com.side.rest.dto.response.NoticeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeMapper {

    NoticeMapper NoticeMapper = Mappers.getMapper(NoticeMapper.class);

    Notice toDomain(NoticeRequestDto dto);

    NoticeResponseDto toResponse(Notice notice);
}