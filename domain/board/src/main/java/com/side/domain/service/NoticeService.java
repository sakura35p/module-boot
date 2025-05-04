package com.side.domain.service;

import com.side.domain.model.Notice;
import com.side.domain.repository.NoticeRepositoryManager;
import org.springframework.stereotype.Service;

import static com.side.domain.enums.RepositoryTypeEnum.JOOQ;
import static com.side.domain.enums.RepositoryTypeEnum.JPA;

@Service
public class NoticeService {

    public void create(Notice notice) {
        NoticeRepositoryManager.getNoticeRepository(JOOQ)
                               .create(notice);
    }

    public Notice find(Notice notice) {
        return NoticeRepositoryManager.getNoticeRepository(JPA)
                                      .find(notice);
    }


    public Notice somethingDifficultLogic(Notice notice) {

        NoticeRepositoryManager.getNoticeRepository(JOOQ)
                               .create(notice);

        return notice;
    }
}
