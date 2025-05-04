package com.side.infrastructure.jooq.repository;

import com.side.domain.model.Notice;
import com.side.domain.repository.NoticeRepository;
import com.side.domain.repository.NoticeRepositoryManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.side.domain.enums.RepositoryTypeEnum.JOOQ;
import static com.side.infrastructure.jooq.generated.Tables.NOTICE;


@Repository
@RequiredArgsConstructor
public class NoticeJooqRepository implements NoticeRepository {

    private final DSLContext dsl;

    @PostConstruct
    public void init() {
        NoticeRepositoryManager.addNoticeRepository(JOOQ, this);
    }

    @Override
    public void create(Notice notice) {

        dsl.insertInto(NOTICE)
           .columns(NOTICE.TITLE)
           .values(String.valueOf(notice.id()))
           .execute();
    }

    @Override
    public Notice find(Notice notice) {

        return dsl.select(NOTICE.ID, NOTICE.TITLE.as("article.title"))
                  .from(NOTICE)
                  .where(NOTICE.TITLE.eq(String.valueOf(notice.id())))
                  .fetchOneInto(Notice.class);
    }
}
