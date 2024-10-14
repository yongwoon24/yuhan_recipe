# Yuhan Recipe

Yuhan Recipe는 1인 가구 및 배달비 문제를 해결하기 위해, 사용자가 가지고 있는 재료로 만들 수 있는 요리 레시피를 제공하고, 본인이 알고 있는 레시피를 공유할 수 있는 웹사이트입니다.

## 주제 선정 이유
1인 가구가 증가하고 배달비가 비싸진 상황에서, 간편하게 집에 있는 재료로 요리할 수 있도록 레시피를 제공하고자 이 프로젝트를 구상하였습니다.

## 주요 기능

### 메인페이지
- **오늘의 추천 메뉴**: 사용자별 맞춤 레시피 10개 추천
- **랭킹**: 좋아요 상위 10개 레시피 표시
- **최근 본 레시피**: 로그인한 사용자의 최근 조회 레시피 표시
- **유저 랭킹**: 좋아요 수 상위 3명의 사용자 표시

### 로그인/회원가입
- 이메일 인증을 통해 회원가입 및 ID/PW 찾기 구현

### 랭킹페이지
- 좋아요 순서로 레시피를 정렬하며, 카테고리 및 기간별 정렬 가능

### 레시피 분류 페이지
- 레시피를 등록 최신순, 조회순으로 정렬
- 카테고리별, 특정 재료로 레시피 검색 가능

### 레시피 페이지
- 레시피의 재료, 양, 조리방법 제공
- 사용자는 레시피를 좋아요/스크랩하고 댓글 작성 가능

### 레시피 작성 페이지
- 로그인한 사용자가 레시피를 작성할 수 있으며, 재료 및 조리 단계를 동적으로 추가/삭제 가능

### 자유게시판
- 사용자 간 소통 게시판으로, 게시글 및 댓글 작성 가능

### 마이페이지
- 작성한 레시피/게시글 조회, 좋아요/스크랩한 레시피 조회, 회원정보 수정 가능

### 관리자 기능
- 관리자는 레시피를 검토 후 게시할 수 있으며, 모든 레시피/게시글을 수정 및 삭제 가능

### 알람 기능
- 레시피가 관리자에 의해 승인되면 알림 표시

## 기술 스택

- **프로그래밍 언어**: Java(Spring Boot), JavaScript, HTML, CSS
- **데이터베이스**: MySQL, AWS RDS

## 웹 페이지 구조

### 메인페이지
![image](https://github.com/user-attachments/assets/9ab1d065-f8a5-46d4-ad66-359bc9dbe4ee)

### 로그인/회원가입/ID,PW찾기페이지
![image](https://github.com/user-attachments/assets/8df1b937-6619-4a28-a516-b2cde226dc1d)
![image](https://github.com/user-attachments/assets/e04e586c-d914-4672-8045-672088a5b174)
![image](https://github.com/user-attachments/assets/b11377b9-837f-41ba-966d-aeb26cbde8a5)

### 랭킹페이지
![image](https://github.com/user-attachments/assets/529df5f2-8801-4a61-a1d1-6042b0547e92)

### 레시피 목록
![image](https://github.com/user-attachments/assets/63576bbb-25a8-452a-9403-86690bb059a9)

### 레시피 페이지
![image](https://github.com/user-attachments/assets/1d6d35e2-0171-42f9-87ce-c65a0fd2d4ce)

### 마이페이지
![image](https://github.com/user-attachments/assets/0eb2c729-5fc6-4c94-983f-dcab149fc596)
