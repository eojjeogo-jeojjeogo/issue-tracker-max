DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS issue_comment;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS emoticon;
DROP TABLE IF EXISTS sign_in_type;
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS comment_emoticon;
DROP TABLE IF EXISTS issue_label;
DROP TABLE IF EXISTS issue_assignee;
DROP TABLE IF EXISTS organization;
DROP TABLE IF EXISTS organization_member;


CREATE TABLE issue
(
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    title           VARCHAR(20) NOT NULL,
    number          BIGINT      NOT NULL,
    is_closed       TINYINT(1) NOT NULL,
    created_time    DATETIME    NOT NULL,
    member_id       BIGINT      NOT NULL,
    milestone_id    BIGINT NULL,
    organization_id BIGINT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE issue_comment
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    content      VARCHAR(500) NOT NULL,
    created_time DATETIME     NOT NULL,
    issue_id     BIGINT       NOT NULL,
    member_id    BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE label
(
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    title            VARCHAR(20) NOT NULL,
    description      VARCHAR(100) NULL,
    background_color CHAR(7)     NOT NULL,
    is_dark          TINYINT(1) NOT NULL,
    organization_id  BIGINT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE milestone
(
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    title           VARCHAR(20) NOT NULL,
    description     VARCHAR(1000) NULL,
    due_date        DATE NULL,
    is_closed       TINYINT(1) NOT NULL,
    organization_id BIGINT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    email           VARCHAR(256) NOT NULL,
    password        VARCHAR(20) NULL,
    nickname        VARCHAR(20)  NOT NULL,
    created_time    TIMESTAMP    NOT NULL,
    profile_img_url VARCHAR(2000) NULL,
    sign_in_type_id BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE emoticon
(
    id      BIGINT  NOT NULL AUTO_INCREMENT,
    unicode CHAR(7) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE sign_in_type
(
    id       BIGINT      NOT NULL,
    provider VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE token
(
    member_id     BIGINT        NOT NULL,
    refresh_token VARCHAR(1000) NOT NULL
);

CREATE TABLE comment_emoticon
(
    comment_id  BIGINT NOT NULL,
    member_id   BIGINT NOT NULL,
    emoticon_id BIGINT NOT NULL
);

CREATE TABLE issue_label
(
    label_id BIGINT NOT NULL,
    issue_id BIGINT NOT NULL
);

CREATE TABLE issue_assignee
(
    issue_id  BIGINT NOT NULL,
    member_id BIGINT NOT NULL
);

CREATE TABLE organization
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    title VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE organization_member
(
    organization_id BIGINT NOT NULL,
    member_id       BIGINT NOT NULL
);

insert into sign_in_type
VALUES (1, "local");
insert into sign_in_type
VALUES (2, "github");

INSERT INTO emoticon (unicode)
VALUES ('U+1F600');
INSERT INTO emoticon (unicode)
VALUES ('U+1F603');
INSERT INTO emoticon (unicode)
VALUES ('U+1F604');
INSERT INTO emoticon (unicode)
VALUES ('U+1F601');
INSERT INTO emoticon (unicode)
VALUES ('U+1F606');

INSERT INTO organization (id, title)
VALUES
    (1, "백엔드"),
    (2, "프론트엔드");

INSERT INTO organization (title)
VALUES ('testOrganization');


INSERT INTO member(id, email, password, nickname, created_time, profile_img_url, sign_in_type_id)
VALUES
    (1, "admin@codesquad.kr","1234","어드민", "2023-07-25 10:36:00", 1, 1),
    (2, "user01@codesquad.kr","1234","박하", "2023-07-25 10:37:00", 1, 1),
    (3, "user02@codesquad.kr",NULL,"찰리", "2023-07-25 10:38:00", 1, 2),
    (4, "user03@codesquad.kr",NULL,"지니", "2023-07-25 10:39:00", 1, 2),
    (5, "user03@codesquad.kr",NULL,"앨버트", "2023-07-25 10:40:00", 1, 2);

INSERT INTO organization_member (organization_id, member_id)
VALUES
    (1, 3),
    (1, 4),
    (1, 5),
    (2, 1),
    (2, 2);


INSERT INTO label (id, title, description, background_color, is_dark, organization_id)
VALUES
    (1, 'BE', '백엔드 라벨', '#fbca04', 1, 1),
    (2, 'FE', '프론트엔드 라벨', '#d73a4a', 1, 1),
    (3, 'feat', '피처', '#0075ca', 1, 1),
    (4, 'docs', '문서', '#a2eeef', 0, 2),
    (5, 'bug', '버그', '#0315EB', 0, 2);


INSERT INTO milestone (id, title, description, due_date, is_closed, organization_id)
VALUES
    (1, '이번주 할일', 'CRUD', '2023-09-01', 0, 1),
    (2, '다음주 할일', 'Login', '2023-09-15', 0, 1);
