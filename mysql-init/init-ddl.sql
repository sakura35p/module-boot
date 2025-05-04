create table if not exists comment
(
    dislikes int          null,
    likes    int          null,
    board_id bigint       null,
    id       bigint auto_increment
        primary key,
    contents varchar(500) null
);

create table if not exists notice
(
    dislikes    int         null,
    likes       int         null,
    created_at  datetime(6) null,
    id          bigint auto_increment
        primary key,
    modified_at datetime(6) null,
    created_by  varchar(30) null,
    modified_by varchar(30) null,
    title       varchar(30) null,
    contents    tinytext    null
);

create table if not exists role
(
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    created_by  varchar(30)  null,
    modified_by varchar(30)  null,
    description varchar(255) null comment '설명',
    roleId      varchar(255) not null
        primary key,
    roleName    varchar(255) null comment '역할 명'
);

create table if not exists role_hierarchy
(
    higher_role varchar(255) not null,
    lower_role  varchar(255) not null,
    primary key (higher_role, lower_role)
);

create table if not exists user
(
    locked       bit                                             null comment '잠금여부',
    created_at   datetime(6)                                     null,
    lockedAt     datetime(6)                                     null comment '잠금시간',
    modified_at  datetime(6)                                     null,
    unlockedAt   datetime(6)                                     null comment '잠금해제시간',
    created_by   varchar(30)                                     null,
    modified_by  varchar(30)                                     null,
    email        varchar(255)                                    null comment '이메일',
    failureCount varchar(255)                                    null comment '로그인실패횟수',
    mobile       varchar(255)                                    null comment '핸드폰번호',
    password     varchar(255)                                    null comment '비밀번호',
    userId       varchar(255)                                    not null
        primary key,
    userName     varchar(255)                                    null comment '이름',
    description  longtext                                        null comment '설명',
    status       enum ('ACTIVE', 'DELETED', 'LOCKED', 'PENDING') null comment '상태',
    userType     enum ('ADMIN', 'NORMAL')                        null comment '타입'
);

create table if not exists user_role
(
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    created_by  varchar(30)  null,
    modified_by varchar(30)  null,
    role_id     varchar(255) not null,
    user_id     varchar(255) not null,
    primary key (role_id, user_id)
);

