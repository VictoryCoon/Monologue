$> docker pull mariadb
$> docker run —detach —name mariadb -p 3306:3306 —env MARIADB_USER=kspot —env MARIADB_PASSWORD=${PASSWORD} —env MARIADB_ROOT_PASSWORD=oo1414036! mariadb:latest

$> docker pull redis
$> sudo docker run -p 6379:6379 redis

show databases;

create database ${NAME};

create user '${NAME}'@'%' identified by '${PASSWORD}';

use mysql;

select * from user;

grant all privileges on ${NAME}.* to '${NAME}'@'%';

flush privileges;


/**
 * Push Service Metadata
 **/
CREATE TABLE G_PUSH_SEND
(
  MSG_ID        CHAR(20)       NOT NULL, -- YYYMMDD || 'RL' ||TYPE_DESC(2) || SEQUENCE(8), 8자리는 1억건까지 발행 가능
  MSG_DT        CHAR(8)        DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDD'),
  USR_ID        VARCHAR(16)    NOT NULL,
  SEND_TYPE     CHAR(1)        NOT NULL, -- D : DB I/O, F : FILE INSERT, O : TCP, ETC ...
  MSG_TYPE      CHAR(1)        NOT NULL, -- AND : A, IOS: I, WEB : W
  MSG_TITLE     VARCHAR(64),   
  MSG_CONTENT   VARCHAR(2000), 
  MSG_PAYLOAD   VARCHAR(4000),
  MSG_FULLTEXT  VARCHAR(4000),
  TOKEN_PRIVATE VARCHAR(256)   NOT NULL,
  TOKEN_PUBLIC  VARCHAR(256),  
  IMAGE_URL     VARCHAR(256),  
  SOUND         CHAR(1)        DEFAULT 'D',
  PRIMARY KEY(MSG_ID, MSG_DT)
);

CREATE TABLE G_PUSH_SCHD
(
  MSG_ID        CHAR(20)       NOT NULL, -- YYYMMDD || 'RL' ||TYPE_DESC(2) || SEQUENCE(8), 8자리는 1억건까지 발행 가능
  MSG_DT        CHAR(8)        DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDD'),
  USR_ID        VARCHAR(16)    NOT NULL,
  SEND_TYPE     CHAR(1)        DEFAULT 'B', -- B : BATCH
  MSG_TYPE      CHAR(1)        NOT NULL, -- AND : A, IOS: I, WEB : W
  MSG_TITLE     VARCHAR(64),   
  MSG_CONTENT   VARCHAR(2000), 
  MSG_PAYLOAD   VARCHAR(4000),
  MSG_FULLTEXT  VARCHAR(4000),
  TOKEN_PRIVATE VARCHAR(256)   NOT NULL,
  TOKEN_PUBLIC  VARCHAR(256),  
  IMAGE_URL     VARCHAR(256),  
  SOUND         CHAR(1)        DEFAULT 'D',
  RESERVE_TIME  CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  REG_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  REG_ID        VARCHAR(16)    NOT NULL,
  UPD_DT        CHAR(14),
  UPD_ID        VARCHAR(16),
  PRIMARY KEY(MSG_ID, MSG_DT)
);

CREATE TABLE G_PUSH_LOG
(
  MSG_ID        CHAR(20)       NOT NULL, -- YYYMMDD ||'RV'||TYPE_DESC(2) || SEQUENCE(8), 8자리는 1억건까지 발행 가능
  MSG_DT        CHAR(8)        DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDD'),
  USR_ID        VARCHAR(16)    NOT NULL,
  SEND_TYPE     CHAR(1)        NOT NULL, -- D : DB I/O, F : FILE INSERT, O : TCP, B : BATCH, ETC ...
  MSG_TYPE      CHAR(1)        NOT NULL, -- AND : A, IOS: I, WEB : W
  MSG_TITLE     VARCHAR(64),   
  MSG_CONTENT   VARCHAR(2000), 
  MSG_PAYLOAD   VARCHAR(4000),
  MSG_FULLTEXT  VARCHAR(4000),
  TOKEN_PRIVATE VARCHAR(256)   NOT NULL,
  TOKEN_PUBLIC  VARCHAR(256),
  IMAGE_URL     VARCHAR(256),  
  SOUND         CHAR(1)        DEFAULT 'D',
  REQUEST_TIME  VARCHAR(14)    DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  RESPONSE_TIME VARCHAR(14),
  CONFIRM_TIME  VARCHAR(14),
  STATUS        CHAR(1)        DEFAULT 'R', -- R(준비) > S(시작) > E(종료)
  STATUS_CODE   VARCHAR(5)     NOT NULL,
  REG_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  REG_ID        VARCHAR(16)    NOT NULL,
  UPD_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  UPD_ID        VARCHAR(16),
  PRIMARY KEY(MSG_ID, MSG_DT)
);

CREATE TABLE G_PUSH_TRACE
(
  MSG_ID        VARCHAR(20)    NOT NULL, -- YYYMMDD || TYPE_DESC(4) || SEQUENCE(8), 8자리는 1억건까지 발행 가능
  MSG_DT        CHAR(8)        DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDD'),
  TRACE_SEQ     INT            NOT NULL, -- MAX(TRACE_SEQ)+1
  STATUS        CHAR(1)        NOT NULL, -- (=STATUS)
  STATUS_CODE   VARCHAR(5)     NOT NULL, -- 고민해봐야할 영역
  REG_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  REG_ID        VARCHAR(16)    NOT NULL,
  -- UPD_DT        CHAR(14),
  -- UPD_ID        VARCHAR(16),
  PRIMARY KEY(MSG_ID, MSG_DT, TRACE_SEQ)
);

CREATE TABLE G_DEVICE_INFO
(
  APP_ID        VARCHAR(12)    NOT NULL,
  USR_ID        VARCHAR(16)    NOT NULL,
  MOBILE_NO     VARCHAR(16),
  OS_TYPE       CHAR(1)        NOT NULL,
  OS_VERSION    VARCHAR(8)     DEFAULT '0.0.0',
  TOKEN_PRIVATE VARCHAR(256)   NOT NULL,
  TOKEN_PUBLIC  VARCHAR(256),
  USE_AT        CHAR(1)        DEFAULT 'Y',
  REG_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  REG_ID        VARCHAR(16)    NOT NULL,
  UPD_DT        CHAR(14)       DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHH24MISS'),
  UPD_ID        VARCHAR(16),
  PRIMARY KEY(APP_ID, USR_ID, MOBILE_NO)
);

CREATE TABLE CM_CODE
(
  CODE_ID    VARCHAR(6)    NOT NULL,
  CODE_NM    VARCHAR(64)   NOT NULL,
  CODE_DESC  VARCHAR(128),
  CODE_LEVEL INT           DEFAULT 0,
  CODE_VAL_1 VARCHAR(64)   NOT NULL,
  CODE_VAL_2 VARCHAR(64),
  CODE_VAL_3 VARCHAR(64),
  CODE_VAL_4 VARCHAR(64),
  CODE_VAL_5 VARCHAR(64),
  USE_AT     CHAR(1)       DEFAULT 'Y',   -- 계정 비활성 여부
  REG_ID     VARCHAR(36)   DEFAULT 'Admin',
  REG_DT     VARCHAR(14)   DEFAULT TO_CHAR(SYSDATE(),'YYYYMMDDHHMISS'),
  UPD_ID     VARCHAR(36),
  UPD_DT     VARCHAR(14),
  PRIMARY KEY(CODE_ID)
);

CREATE INDEX CM_CODE_IDX_UNIQUE ON CM_CODE(CODE_ID);

ALTER TABLE CM_CODE COMMENT = '공통코드';
ALTER TABLE CM_CODE MODIFY CODE_ID    VARCHAR(6)   COMMENT '코드ID';
ALTER TABLE CM_CODE MODIFY CODE_NM    VARCHAR(64)  COMMENT '코드명';
ALTER TABLE CM_CODE MODIFY CODE_DESC  VARCHAR(128) COMMENT '코드설명';
ALTER TABLE CM_CODE MODIFY CODE_LEVEL INT          COMMENT '코드레벨';
ALTER TABLE CM_CODE MODIFY CODE_VAL_1 VARCHAR(64)  COMMENT '코드값1';
ALTER TABLE CM_CODE MODIFY CODE_VAL_2 VARCHAR(64)  COMMENT '코드값2';
ALTER TABLE CM_CODE MODIFY CODE_VAL_3 VARCHAR(64)  COMMENT '코드값3';
ALTER TABLE CM_CODE MODIFY CODE_VAL_4 VARCHAR(64)  COMMENT '코드값4';
ALTER TABLE CM_CODE MODIFY CODE_VAL_5 VARCHAR(64)  COMMENT '코드값5';
ALTER TABLE CM_CODE MODIFY USE_AT     CHAR(1)      COMMENT '사용여부';
ALTER TABLE CM_CODE MODIFY REG_ID     VARCHAR(36)  COMMENT '등록자ID';
ALTER TABLE CM_CODE MODIFY REG_DT     VARCHAR(14)  COMMENT '등록일자';
ALTER TABLE CM_CODE MODIFY UPD_ID     VARCHAR(36)  COMMENT '수정자ID';
ALTER TABLE CM_CODE MODIFY UPD_DT     VARCHAR(14)  COMMENT '수정일자';
