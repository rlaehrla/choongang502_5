# 농산물 직거래 온라인 플랫폼(여름지기)

---

## 👨‍🏫 프로젝트 소개

농부들이 직접 재배한 농산물을 직접 소비자에게 제공함으로써

투명한 가격으로 판매자와 소비자 모두 윈윈하여 우리의 식탁을 건강하게 만들고 싶었습니다.

또한, 우리 농산물의 소비 촉진을 위한 방안으로 

소비자가 구매한 상품을 활용한 레시피를 공유할 수 있는 커뮤니케이션의 공간으로 활용될 수 있게 구현했습니다.

* 배포 서버 : http://43.202.183.76:3000/


## ⏲️ 개발 기간

+ 2023.12.26(화) ~ 2024.02.01(목)

+ **기획 & 설계**(1주) | **개발 & 구현**(4주) | **시험 및 피드백**(1주)
![project_progress](https://github.com/rlaehrla/choongang502_5/assets/143992194/2bafd69a-e94b-4e85-96bc-ea660bbd3a6b)
  

## 🌈 개발자 소개

+ 개발인원 : 6명

  |  개발자  | 깃허브 | 역할분담                                                            | 
  |-----------|----------|-----------------------------------------------------------------|
  | 김도훈 | [GitHub](https://github.com/rlaehrla) | 팀원과의 협업 및 업무 분배, 메인 페이지(배너 관리, 최신 상품, 지도 API), 결제 시스템 구현, 전반적인 CSS 작업 등 |
  | 김병훈 | [GitHub](https://github.com/OasisDefinitelyMaybe) | 결제 시스템 및 장바구니 구현, 메인 페이지(제철 특산품, 채팅 기능, FAQ), 게시판(CRUD, 파일 업로드 기능), 전반적인 CSS 작업 등 |
  | 김은진 | [GitHub](https://github.com/3un3un) | 레시피 페이지, 레시피 관리 페이지 등 |
  | 양형경 | [GitHub](https://github.com/yhg1024) | 상품, 주소 연동, 게시글(list, view, write, update, save), 게시판 댓글 등 |
  | 이수정 | [GitHub](https://github.com/ddoonsim) | 회원 인증, 등록 및 로그인 기능 구현, 판매자의 관리 페이지, 블로그 구현, 주문 관리 등 |
  | 이윤희 | [GitHub](https://github.com/yunii118) | 관리자 페이지(기본 설정, 회원 관리, 레시피 관리, 상품 관리), 메인 페이지(베스트, 상품, 찜하기, 주문, 포인트 등) CRUD 작업 및 페이지 생성 등 |



## ⚙️ 기술 스택

+ Version : Java 17
  
+ IDE : InteliJ
  
+ Framework : SpringBoot 3.2.0

+ Server : 젠킨스, AWS EC2

+ DataBase : Oracle, DBeaver
  
+ Front-end : JavaScript, HTML, CSS, Thymeleaf

+ 아이디어 회의 : 중앙정보처리학원, Notion
![dev](https://github.com/rlaehrla/choongang502_5/assets/143992194/e6b4dbc5-eb61-4c82-a4cb-4d6d5dd9d084)

## 📌 주요 기능

+ 이메일 인증코드 전송 확인, 프로필 이미지 등록, API를 이용한 사업자 번호 인증을 구현
  
+ **회원 이원화** : 농부 & 구매자 회원가입 및 회원 탈퇴 기능

+ Spring Security를 이용한 로그인과 로그인 유지

+ 농부회원의 **상품 등록, 주문 관리, 블로그, 구매 후기, 소식 관리 기능**

+ 구매자는 **회원 & 비회원 형태로 상품 구매 가능**, 장바구니, 포인트, **API를 연동한 결제 기능**

+ 농부와 구매자 사이의 상품문의는 **웹소켓을 활용한 채팅 기능**

+ **CRUD를 이용한 각 농산물 카테고리 및 레시피 게시판**

+ **카카오 지도API**를 이용하여 상품 원산지 지도에 구현

+ 베스트 페이지에 **최다 판매량을 기준으로 농장 랭킹 구현**

+ 배너 관리에서 메인 페이지, 레시피 페이지에 배너 각각 관리 가능

## ERD 구조
![ERD](https://github.com/rlaehrla/choongang502_5/assets/143992194/5deeb9e5-4a35-477e-afc8-b8ae9f1e736f)

## 화면 설계
<details>
  <summary>자세히 보기</summary>
  

  |메인 홈 화면|회원가입|
  |:-:|:-:|
  |![main_home](https://github.com/rlaehrla/choongang502_5/assets/143992194/9c1f8749-24ef-4804-9117-9743cb071427)|![회원가입_](https://github.com/rlaehrla/choongang502_5/assets/143992194/01c2613b-5abf-408a-b0ae-264f63bf0b1b)|

  |로그인|상품 상세 페이지|
  |:-:|:-:|
  |![로그인_](https://github.com/rlaehrla/choongang502_5/assets/143992194/df6e33f0-cdac-49af-b7fe-d818d57573af)|![상품_상세](https://github.com/rlaehrla/choongang502_5/assets/143992194/dd777ada-c275-424f-93b1-5aa567412700)|

  |장바구니|주문서|
  |:-:|:-:|
  |![장바구니](https://github.com/rlaehrla/choongang502_5/assets/143992194/9f58e7e2-8de9-4338-9dc7-a8c93c8e1ca4)|![주문서](https://github.com/rlaehrla/choongang502_5/assets/143992194/6871a752-cbca-4a46-a2f8-f24ece73992c)|

  |결제하기|주문 상세|
  |:-:|:-:|
  |![결제](https://github.com/rlaehrla/choongang502_5/assets/143992194/ea8b007d-7abf-4bbb-a468-e68ea1cf216f)|![주문상세](https://github.com/rlaehrla/choongang502_5/assets/143992194/69314eac-bfbe-4568-865d-06309652bc76)|

  |레시피 페이지|레시피 상세보기|
  |:-:|:-:|
  |![레시피_페이지](https://github.com/rlaehrla/choongang502_5/assets/143992194/04aba3c5-852a-4756-bfb4-b1c4fa736149)|![레시피_상세](https://github.com/rlaehrla/choongang502_5/assets/143992194/5039fdc1-831c-472b-8d28-2d811df6d599)|

  |판매자 전용 관리페이지|농부 블로그 페이지|
  |:-:|:-:|
  |![판매자_관리_페이지](https://github.com/rlaehrla/choongang502_5/assets/143992194/b6bb02d4-3ce3-4bc0-a24e-c0e37dbe8817)|![판매자_블로그](https://github.com/rlaehrla/choongang502_5/assets/143992194/d8c933bf-891d-4aed-a092-d1bbbb7f47c3)|
</details>

## 자체 총평 및 소감
<details>
  <summary>자세히 보기</summary>
  
  ![feedback](https://github.com/rlaehrla/choongang502_5/assets/143992194/0d65320a-ff5a-476d-a46b-683f3829f449)
</details>

