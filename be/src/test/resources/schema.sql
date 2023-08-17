DROP TABLE IF EXISTS comment_emoticon;
DROP TABLE IF EXISTS issue_label;
DROP TABLE IF EXISTS issue_assignee;
DROP TABLE IF EXISTS organization_member;
DROP TABLE IF EXISTS issue_comment;
DROP TABLE IF EXISTS issue;
DROP TABLE IF EXISTS label;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS organization;
DROP TABLE IF EXISTS token;

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
