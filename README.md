# 📝 화창한 봄날에 기록을 


## 💡 날씨(Api)를 활용해서 CRUD 날씨,일기를 작성/조회/수정/삭제 Back-end 구현하기!!!

- 프로젝트 기간 : 2022.10.23 ~ 2022.10.29








## 💡 주요기능
1. 날씨 API 정보 가져오기 기능 구현 (https://openweathermap.org/api)
2. 날씨,일기를 작성 (C) , 날씨,일기를 조회(R), 수정(U), 삭제(D)



## 💡 사용 기술
<img src="https://img.shields.io/badge/-Spring-6DB33F"/> <img src="https://img.shields.io/badge/-Swagger-1572B6"/>

![68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4a6176612d303037333936](https://user-images.githubusercontent.com/43702182/189212938-b87173c1-47ae-411c-916f-5431af631439.svg)
![68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4170616368253230546f6d6361742d463844433735](https://user-images.githubusercontent.com/43702182/189212944-9a709fc5-ea05-45be-ae06-3137e0188ed4.svg)
![마리아](https://user-images.githubusercontent.com/43702182/189213021-b3b90493-4446-4c30-bd5e-4196734279f7.svg)
![그레이들](https://user-images.githubusercontent.com/43702182/190194765-941ef188-383c-441c-bad9-716d63f64f9a.svg)
![깃허브](https://user-images.githubusercontent.com/43702182/189214961-5ae8258f-5df1-45b5-9eee-b9eaee2f034f.svg)

- 개발 언어 & 환경셋팅

- Java 17 Amazon Corretto version 17.0.5
- jpa:2.7.4
- DataBase: MariaDB 10.6
- springfox-swagger-ui:3.0.0
- springframework.boot' version '2.7.4
- 자세한건 build.gradle 참고


- API
   [openWeathermap(API)](https://openweathermap.org/api)


- 개발 환경
    - intelliJ IDEA, Gradle, Lombok , JPA
    
* Swagger API

https://user-images.githubusercontent.com/43702182/198825968-d5421eb1-e617-4a27-ae15-399aec0f4731.mp4



## 🐱페이지별 상세 기능

일기 작성, 조회, 수정, 삭제

https://user-images.githubusercontent.com/43702182/198825981-044b26b6-1a03-491c-8aef-8ce21b504e5f.mp4

https://user-images.githubusercontent.com/43702182/198826151-9d1a0b80-278d-4144-81bd-cfd886e7f253.mp4


https://user-images.githubusercontent.com/43702182/198826161-751a5117-f9af-4a83-b2f3-2b0a0edda7e4.mp4







## 💡 상세 기능 및 학습 내용 
1. 테스트 코드 작성해보기
2. 외부 API의 데이터를 활용해보기
3. JPA 방식으로 MariaDB 사용하기



### ✅ GET / read / diary
### ✅ GET / read / diaries1,2
### ✅ PUT / update / diary1,2 
### ✅ DELETE / delete / diary




### 🐱 DB와 관련된 함수들을 트랜잭션 처리 해보기

### 🐱 매일 새벽 2시에 날씨 데이터를 외부 API 에서 받아다 DB에 저장해두는 로직을 구현해보기.

### 🐱 logback 을 이용하여 프로젝트에 로그를 작성해보기.

### 🐱 ExceptionHandler 을 이용한 예외처리를 해보기.

### 🐱 swagger 을 이용하여 API documentation 을 작성해보기.


### 이슈 

### 오류 및 이슈 소개
- 몇 가지 이슈가 있었지만 하나를 살펴보자면 일기를 수정할 때 사용자 관점에서 보면 불편한 점이 한 두 가지가 아니었습니다.
- 예로 들면 일기를 수정 할시에 같은 날짜에 일기를 2번을 썻다면 처음 만들 때는 최근 일기만 수정하도록 구현했는데
- 만약 다른 같은 날짜에 다른 일기를 수정하고 싶은데 이런 부분에서 문제가 있다고 봅니다. 그래서 일기를 작성했을 때
- 일기의 고유의 아디값을 통해서 접근하도록 하나의 메소드를 더 추가하였습니다.
- 사실 이런 메소드 하나 고치는 건 어렵기도 어렵지 않을 수도 있겠지만 생각하고 끊임없이 생각하는 게 좋은 개발자로 나아가는 한 걸음이라 생각합니다.

## 💡 프로젝트 리뷰 및 개선방향

- 스프링부트를 활용해서 CRUD 작업을 해보았는데 아직 익숙지 않아서 어려운 부분이 많았습니다.
- 코드를 짜면서 잘 알지 못했던 부분 중에 프로젝트의 설계 부분이 이전보다 눈에 들어오는 거 같습니다








