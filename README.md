# JSP_Team_Project

이 프로젝트는 현재 서울시에서 진행되고 있거나 예정인 문화 행사를

빠르고 편리하게 예약, 검색을 하는 목적으로 만들어져있습니다.

일반유저는 행사 예약, QnA질문이 주 기능이며,

관리자는 일반유저의 예약행사 관리, QnA질문 답변 등 관리가 주 목적이 됩니다.

프로그램을 실행 전에 반드시 Mairdb 세팅과 DB연결 설정을 해주셔야 원활한 실행이 가능합니다.

--------------------------------
DB연결을 위한 설정 변경

DB연결 설정 파일 위치
Project 폴더 -> src -> main -> java -> com -> example -> common -> config -> HikariDsConfig.java

(DB 연결 설정을 밑의 SQL문을 입력한 계정정보와 알맞게 설정해주세요.)

![image](https://github.com/byeongseokim/JSP_Team_Project/assets/130523521/41d35a93-c881-4006-98ff-cd8152534a93)

public final class HikariDsConfig {<br>
    private final String CLASSNAME = "org.mariadb.jdbc.Driver";<br>
    private final String JDBC_URL = "jdbc:mariadb://localhost:3306/culture_db";<br>
    private final String USERNAME = "사용자 ID";<br>
    private final String PASSWORD = "사용자 PW";<br>
    private final String CACHE_PREP_STMTS = "true";<br>
    private HikariDataSource ds;<br>
    private HikariConfig config;<br>

--------------------------------
작동에 필요한 SQL문(Mariadb 기준)

//DB 생성
create database culture_db;<br>
use culture_db;<br>
set SQL_SAFE_UPDATES = 0;<br>

//유저테이블 생성
<pre>create table user_basic
(
    id      varchar(30)        not null,
    pwd     varchar(40)        not null,
    name    varchar(30)        not null,
    regDate datetime default now() null,
    constraint user_basic_pk
        primary key (id)
);</pre>


create table user_res
(
    id       varchar(30) not null,
    email    varchar(100) not null,
    phone    varchar(100) not null,
    payment_amount  int default 0 null,
    constraint user_res_pk
        primary key (id)
);


//행사테이블 작성
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


//리뷰테이블 생성
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


//예약테이블 생성
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


//예약_행사 테블
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


//QnA테이블
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

--------------------------------

시연 영상
(오마이갓 크기 커서 안올라감 이건 넣지 말까요)
