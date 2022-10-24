# DB 생성
DROP DATABASE IF EXISTS SB_AM;
CREATE DATABASE SB_AM;

# DB 선택 
USE SB_AM;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테스트 데이터 생성

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '공지1',
`body` = '공지내용1~';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1!';
 
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2!';

SELECT LAST_INSERT_ID(); 
 
SELECT * FROM article ORDER BY id DESC;

--
# 멤버 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨 (3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL, 
    nickname CHAR(20) NOT NULL,
    cellphoneNum CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부 (0=탈퇴 전,1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

# 멤버 데이터 생성 (관리자)
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'admin1',
    loginPw = 'admin1',
    `authLevel` = 7,
    `name` = '김관리',
    nickname = '관리자',
    cellphoneNum = '010-0000-0000',
    email = 'inane09@gmail.com';
    
# 멤버 데이터 생성 (일반)
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test1',
    loginPw = 'test1',
    `name` = '김회원',
    nickname = '사용자1',
    cellphoneNum = '010-1234-5678',
    email = 'inane09@gmail.com';
    
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'test2',
    loginPw = 'test2',
    `name` = '최회원',
    nickname = '사용자2',
    cellphoneNum = '010-5678-1234',
    email = 'inane09@gmail.com';
    
        
SELECT * FROM `member`;


#게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# memberId 추가
UPDATE article
SET memberId = 1
WHERE memberId = 0;

SELECT * FROM article ORDER BY id DESC;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free1(자유게시판1), free2(자유게시판2),..',
    `name` CHAR(50) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제여부 (0=삭제 전,1=삭제 후)',
    delDate DATETIME COMMENT '삭제날짜'
);

# basic board 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';


INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free1',
`name` = '자유';

# board 테이블에 boardId 칼럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

# 1 게시물을 공지사항 게시물로 수정
UPDATE article
SET boardId = 1
WHERE id IN (1)

# 2, 3번 게시물을 공지사항 게시물로 수정
UPDATE article
SET boardId = 2
WHERE id IN (2, 3)

UPDATE article
SET memberId = 2
WHERE id IN (2, 3)


SELECT * FROM article ORDER BY id DESC;
SELECT * FROM board;
SELECT * FROM `member`;
SELECT * FROM reactionPoint;

#게시물 테이블에 조회수 칼럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;

# reactionPint 테이블 생성
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) UNSIGNED NOT NULL COMMENT '관련 데이터 번호',
    `point` SMALLINT(2) NOT NULL
);

# reactionPoint test data
# 1번 회원이 1번 article에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article', 
relId = 1,
`point` = -1; 

# 1번 회원이 2번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article', 
relId = 2,
`point` = 1; 

# 2번 회원이 1번 article에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article', 
relId = 1,
`point` = -1; 

# 2번 회원이 2번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article', 
relId = 2,
`point` = 1; 

# 3번 회원이 1번 article에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article', 
relId = 1,
`point` = 1; 



SELECT * FROM article ORDER BY id DESC;
SELECT * FROM board;
SELECT * FROM `member`;
SELECT * FROM reactionPoint;