drop database if exists culture_db;
create database culture_db;
use culture_db;
set SQL_SAFE_UPDATES = 0;
#유저테이블 생성
create table user_basic
(
    id      varchar(30)        not null,
    pwd     varchar(40)        not null,
    name    varchar(30)        not null,
    regDate datetime default now() null,
    constraint user_basic_pk
        primary key (id)
);

create table user_res
(
    id       varchar(30) not null,
    email    varchar(100) not null,
    phone    varchar(100) not null,
    payment_amount  int default 0 null,
    constraint user_res_pk
        primary key (id)
);

#행사테이블 작성
create table culture_basic
(
    cno      bigint auto_increment,
    svc_id      varchar(100) not null,
    svc_nm     varchar(200) not null,
    area_nm varchar(200) null,
    place_nm varchar(200) null,
    tel_no    varchar(100) null,
    regDate datetime default now() null,
    constraint culture_basic_pk
        primary key (cno)
);

create table culture_info
(
    cno        bigint      not null auto_increment,
    pay_ay_nm     varchar(100) null,
    use_tgt_info varchar(200) null,
    svc_url    varchar(200) null,
    img_url    varchar(200) null,
    dtlcont    MEDIUMTEXT null,
    constraint culture_info_pk
        primary key (cno)
);

create table culture_res
(
    cno      bigint not null auto_increment,
    capacity int    not null,
    price    int    null,
    revstd_day_nm varchar(200) null,
    revstd_day varchar(100) null,
    constraint culture_res_pk
        primary key (cno)
);

create table culture_schedule
(
    cno             bigint      not null auto_increment,
    svc_opn_bgn_dt varchar(100) not null,
    svc_opn_end_dt   varchar(100) not null,
    v_min   varchar(100) null,
    v_max   varchar(100) null,
    rcpt_bgn_dt varchar(100) not null,
    rcpt_end_dt   varchar(100) not null,
    constraint culture_schedule_pk
        primary key (cno)
);

#리뷰테이블 생성
create table review
(
    re_no   bigint auto_increment,
    id      varchar(30)        not null,
    cno     bigint             not null,
    content text               not null,
    grade   int                not null,
    regDate date default now()     null,
    upDate_time datetime null,
    constraint review_pk
        primary key (re_no),
    -- constraint review_culture_basic_null_fk
    --     foreign key (cno) references culture_basic (cno),
    constraint review_user_basic_null_fk
        foreign key (id) references user_basic (id)
);

#예약테이블 생성
create table reservation
(
    rno     bigint auto_increment,
    id      varchar(30)        not null,
    resDate date            not null,
    resCnt int default 0 null,
    constraint reservation_basic_pk
        primary key (rno),
    constraint reservation_user_res_null_fk
        foreign key (id) references user_res (id)
);

#예약_행사 테블
create table res_culture
(
    rc_no    bigint auto_increment,
    rno      bigint            not null,
    cno      bigint            not null,
    resPrice int default 0     null,
    resDate  date              not null,
    regDate  date default now() null,
    constraint res_culture_pk
        primary key (rc_no),
    constraint res_culture_reservation_null_fk
        foreign key (rno) references reservation (rno)
    -- constraint res_culture_culture_basic_null_fk
    --     foreign key (cno) references culture_basic (cno)
);


#QnA테이블
create table QnA_Q
(
    qqno    bigint             not null auto_increment,
    id      varchar(30)        not null,
    title   varchar(50)        not null,
    content  text               null,
    cnt     int default 0       null,
    commentCnt   int default 0  null,
    regDate date default now() not null,
    updateDate date null,
    constraint QnA_Q_pk
        primary key (qqno),
    constraint QnA_Q_user_basic_null_fk
        foreign key (id) references user_basic (id)
);

create table QnA_A
(
    qano    bigint             not null auto_increment,
    qqno    bigint             not null,
    content  text               null,
    regDate date default now() not null,
    updateDate date null,
    id      varchar(30)     not null,
    constraint QnA_A_pk
        primary key (qano),
    constraint QnA_A_QnA_Q_null_fk
        foreign key (qqno) references QnA_Q (qqno)
);

set SQL_SAFE_UPDATES = 1;