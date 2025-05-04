package com.side.rest.usecase;

import com.side.domain.model.Notice;
import com.side.domain.service.NoticeService;
import com.side.rest.dto.request.NoticeRequestDto;
import com.side.rest.dto.response.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.side.rest.mapper.NoticeMapper.NoticeMapper;


@Service
@RequiredArgsConstructor
public class NoticeUseCase {

    private final NoticeService noticeService;

    @Transactional
    public void create(NoticeRequestDto dto) {
        noticeService.create(NoticeMapper.toDomain(dto));
    }

    @Transactional(readOnly = true)
    public NoticeResponseDto find(NoticeRequestDto dto) {

        Notice notice = noticeService.find(NoticeMapper.toDomain(dto));

        return NoticeMapper.toResponse(notice);
    }

    public Notice somethingDifficultLogic(NoticeRequestDto dto) {

        return noticeService.somethingDifficultLogic(NoticeMapper.toDomain(dto));
    }

}
