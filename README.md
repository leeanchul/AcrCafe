# AcrCafe
—개발 목표—

간단한 게시판 

-사진,글,파일 올릴 수 있게(통합)

-문의 하기 (email api 활용하기)

-댓글 기능

-페이징 처리

-검색 기능

-상제정보 페이지

진행 상태

-jwt+security를 이용한 로그인 처리

-회원 가입

-게시판

-사진

-페이징 처리

-검색

TodoList

- ui 개선 시급하다.
- 댓글 및 게시물에 대한 수정 및 삭제 할 수 있도록 설정
- 비밀번호 변경 유효성 검사 (front) 에서 처리하기
- 내가 쓴 게시글, 및 사진, 한줄일기 , 댓글 관리기능
- 

Oauth2.0기능 및 프론트-백 분리하기 회원가입만 유효성 검사하기

#DB

# 사용자 DB

CREATE TABLE user_tbl(
num NUMBER ,
nickName VARCHAR2(20) NOT NULL,

userName VARCHAR2(20) PRIMARY KEY,
password VARCHAR2(100) NOT NULL,
email VARCHAR2(100) UNIQUE,
role VARCHAR2(10) NOT NULL,
profile VARCHAR2(100),
regdate DATE
);

# 사진 DB

CREATE TABLE baord_gallery (
NUM NUMBER PRIMARY KEY,
WRITER VARCHAR2(100),
CAPTION VARCHAR2(100),
SAVEFILENAME VARCHAR2(100),
REGDATE DATE
);

# 게시물 DB

CREATE TABLE cafe (
NUM NUMBER PRIMARY KEY,
WRITER VARCHAR2(100),
CONTENT VARCHAR2(200),
TITLE VARCHAR2(100),
REGDATE DATE
);

# testDB

CREATE TABLE baord_test(
NUM NUMBER PRIMARY KEY,
WRITER VARCHAR2(100) NOT NULL,
TITLE VARCHAR2(100)NOT NULL,
CONTENT VARCHAR2(300)NOT NULL,
SAVEFILENAME VARCHAR2(100),
REGDATE DATE
);

# 댓글 DB

- - 댓글을 저장할 테이블
CREATE TABLE board_test_comment(
num NUMBER PRIMARY KEY, --댓글의 글번호
writer VARCHAR2(100), --댓글 작성자의 아이디
content VARCHAR2(500), --댓글 내용
ref_group NUMBER, -- 원글의 글번호
regdate DATE
);
- CREATE SEQUENCE board_test_comment_seq;

#dto
private int num;
private String writer;
private String title;
private String content;
private String SaveFileName;
private String regdate;
private MultipartFile image;

#01 요구사항 정의서

| No | 구분 | 기능 | 요구사항 설명 | etc |
| --- | --- | --- | --- | --- |
|  | 로그인 | 로그인 | jwt 와 Spring Security를 통한 로그인 처리 기능 |  |
|  | 로그인 | 로그 아웃 | 클라이언트 쿠키 local storage 에 저장된 JWT 삭제 |  |
|  | 로그인 | 아이디 저장 | 로그인 form 체크 박스 선택 후 로그인 성공시 Cookie 값에 아이디 'Remember"로 저장된다. | 보류 |
|  | 회원 정보 | 회원 정보 수정 | 회원 e-mail,profile,nickName 을 수정 할 수 있다
profile 초기값은 null이며, defalut 값으로 기본 이미지가 있다. |  |
|  | 회원 정보 | 비밀번호 변경 | 변경한 비밀번호와 변경한 비밀번호 확인 입력을 받아 유효성 검사를 통과하고,
이전 비밀번호가 일치하면 비밀번호 변경을 한다. |  |
|  | 회원 가입 | 회원 가입 | ID(PK), Password,이름,E-mail 입력을 받는다.
ID는 중복되지 않아야 한다.(유효성 검사 성공해야 가입이 가능하다)
모든 입력은 NOT NULL이다. |  |
|  | 회원 가입 | E-mail 인증 | E-mail을 통해 받은 인증번호 입력 성공 | 보류 |
|  | 게시판 | 게시물 조회 | 비회원 사용자도 게시물을 조회 할 수 있다.
페이징 처리 및 검색 기능 |  |
|  |  | 게시물 추가 | 회원만 게시물 추가 할 수 있으며, 추가 버튼도 회원에게만 보인다. | 사진,글 |
|  |  | 게시물 수정 | 회원만 게시물 수정 할 수 있으며, 
수정 버튼은 본인 게시물에만 보인다,
 | 카페 번호(PK) 통해 수정 |
|  |  | 게시물 삭제 | 게시물 작성자와 회원 ID 동일 시 삭제 가능하다. |  |
|  |  | 댓글 조회 | 게시물에 num(px) 을 가지고 Ref_num으로 해당 게시글에 대한 댓글정보를 조회한다. |  |
|  |  | 댓글 추가 | 게시물 num(pk)을 이용하여 해당 게시글에 댓글을 추가한다. |  |
|  |  | 댓글 삭제 | 댓글에 num 을 가지고 댓글 삭제한다. |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  | 문의 | 문의 등록 | 관리자에게 1:1 문의를 할 수 있다. | 보류 |
|  |  | 문의 조회 | 본인이 작성한 문의 내역을 확인 할 수 있다. | 보류 |
|  | 회원 관리 | 회원 조회 | 관리자인 경우에만 회원 조회를 할 수 있다. |  |
|  |  | 회원 삭제 | 관리자인 경우 회원 삭제 처리를 할 수 있다. |  |
|  | 문의 관리 | 문의 조회 | 모든 사용자에 문의 조회
ID 검색 기능
답변하지 않은 문의, 답변한 문의 분류 |  |
|  |  |  |  |  |

#연구 일지

사진,글,파일 를 합치기 vs 사진,글 파일 개별 개발  고민 중.

#통합 방식

### 장점:

- **단일 워크플로우**: 모든 미디어를 하나의 인터페이스에서 처리할 수 있어 개발자와 사용자에게 직관적인 환경을 제공할 수 있습니다. 통합된 API나 인터페이스를 제공하면 유지보수도 더 간단해질 수 있습니다.
- **통일된 데이터 관리**: 사진, 글, 파일을 하나의 객체나 엔티티로 관리하면 저장 방식이나 처리 로직을 일관되게 가져갈 수 있습니다.
- **사용자 편의성**: 사용자에게 한 번의 업로드나 제출로 모든 콘텐츠를 관리할 수 있게 하는 편의성을 제공합니다.

### 단점:

- **복잡성 증가**: 사진, 글, 파일을 모두 포함하는 복합적인 처리 로직을 구현해야 하므로 코드가 복잡해질 수 있습니다.
- **확장성 문제**: 각 데이터 타입에 대한 변경 사항이 필요할 때 전체 구조를 변경해야 할 수 있습니다. 특정 파일 유형만 업데이트할 때도 영향을 미칠 수 있습니다.
- **성능 문제**: 파일 크기나 데이터의 차이에 따라 처리 성능이 저하될 수 있으며, 모든 데이터를 한 번에 처리하는 경우 효율적이지 않을 수 있습니다.

#단일방식

### 장점:

- **유연성**: 사진, 글, 파일을 개별적으로 처리하면 각 미디어의 특성에 맞춘 처리 로직을 작성할 수 있어 더 유연한 개발이 가능합니다. 예를 들어, 사진은 이미지 처리 API를, 글은 텍스트 분석 API를 따로 연결할 수 있습니다.
- **확장성**: 새로운 미디어 타입이 추가되거나 기존 미디어 처리 방법에 변경 사항이 있을 때, 다른 데이터 타입에 미치는 영향 없이 개별적으로 수정이 가능합니다.
- **성능 최적화**: 각각의 데이터 유형에 맞춘 최적화된 로직을 적용할 수 있어 효율성이 향상될 수 있습니다.

### 단점:

- **중복 코드 가능성**: 서로 다른 데이터 타입을 별도로 관리하다 보면 중복되는 코드가 늘어날 가능성이 있습니다.
- **관리 복잡성 증가**: 사진, 글, 파일을 모두 별도로 처리해야 하므로 데이터 흐름이나 처리 로직을 더 많이 관리해야 할 수 있습니다.
- **사용자 경험의 일관성 저하**: 사용자 입장에서 여러 번의 제출을 요구하는 인터페이스가 번거로울 수 있습니다.

### 어떤 방식을 선택할지에 대한 고려사항:

- **프로젝트 요구사항**: 사용자 경험을 중시한다면 통합 처리가 좋을 수 있고, 확장성과 유연성이 중요하다면 개별 처리가 유리할 수 있습니다.
- **성능 요구**: 파일 크기나 처리량이 클 경우, 이를 개별적으로 최적화된 방식으로 처리하는 것이 효율적일 수 있습니다.
- **유지보수성**: 프로젝트 규모가 크고 장기적으로 유지보수할 필요가 있다면 분리 방식을 고려하는 것이 좋습니다.

-Back-end 에서 게시물 삭제 및 수정 form page 접속 시 로그인한 사용자와 writer가 같으면 로그인할 수 있도록 한다. 

-front-end 에서도 본인 글에만 수정 및 삭제 버튼이 나오도록 수정
