package com.side.infrastructure.jpa.mapper;


import com.side.domain.model.Notice;
import com.side.infrastructure.jpa.entity.NoticeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeMapper {

    NoticeMapper NoticeMapper = Mappers.getMapper(NoticeMapper.class);

    Notice toDomain(NoticeEntity entity);

    NoticeEntity toEntity(Notice notice);
}
