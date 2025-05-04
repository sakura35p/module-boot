package com.side.domain.repository;

import com.side.domain.enums.RepositoryTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.side.domain.enums.RepositoryTypeEnum.JPA;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeRepositoryManager {

    private static Map<RepositoryTypeEnum, NoticeRepository> noticeRepositoryMap = new ConcurrentHashMap<>();

    public static Map<RepositoryTypeEnum, NoticeRepository> getNoticeRepositoryMap() {
        return new ConcurrentHashMap<>(noticeRepositoryMap);
    }

    public static void setNoticeRepositoryMap(Map<RepositoryTypeEnum, NoticeRepository> noticeRepositoryMap) {
        NoticeRepositoryManager.noticeRepositoryMap = noticeRepositoryMap != null ? new ConcurrentHashMap<>(noticeRepositoryMap) : null;
    }

    public static NoticeRepository getDefaultNoticeRepository() {
        return noticeRepositoryMap.get(JPA);
    }

    public static NoticeRepository getNoticeRepository(RepositoryTypeEnum repositoryType) {
        return noticeRepositoryMap.get(repositoryType);
    }

    public static void addNoticeRepository(RepositoryTypeEnum repositoryType, NoticeRepository noticeRepository) {
        noticeRepositoryMap.put(repositoryType, noticeRepository);
    }
}
