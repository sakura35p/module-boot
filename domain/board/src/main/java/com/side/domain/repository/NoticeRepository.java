package com.side.domain.repository;

import com.side.domain.model.Notice;

public interface NoticeRepository {

    void create(Notice notice);

    Notice find(Notice notice);
}

