# No-Ingredient (기피 재료 제외 식당 검색 서비스)
사용자가 못 먹거나 기피하는 재료를 선택하면, 해당 재료가 포함된 메뉴를 판매하는 식당을 제외하고 안전한 식당 리스트만 제공하는 백엔드 시스템입니다.


## Tech Stack
- **Language**: Java 17

- **Framework**: Spring Boot 3.5.x

- **Data**: Spring Data JPA

- **Database**: MySQL

- **Documentation**: SpringDoc OpenAPI (Swagger)


## Key Features
- **N:M 관계 매핑**: 메뉴(Menu)와 재료(Ingredient) 사이의 다대다 관계를 @ManyToMany 및 매핑 테이블로 구현

- **기피 재료 필터링**: NOT EXISTS 서브쿼리를 활용하여 특정 재료가 포함된 메뉴를 가진 식당을 동적으로 제외

- **전역 예외 처리**: 사용자가 잘못된 재료를 입력하거나 검색 결과가 없을 때 명확한 메시지를 응답하는 예외 핸들링

## Troubleshooting
#### 1. 데이터 무결성 및 매핑 최적화 ####

- **Issue**: 초기 데이터 세팅 시 수동 ID 입력으로 인한 메뉴-재료 간 매핑 오류 발생

- **Solution**: SQL의 INSERT ... SELECT 구문을 사용하여 이름(Name) 기반으로 ID를 자동 매칭하여 휴먼 에러를 차단하고 데이터 정확성 확보

#### 2. JPA 엔티티 및 JPQL 매핑 디버깅 ####
- **Issue**: UnknownPathException 발생 및 ingredientse와 같은 사소한 오타로 인한 빈(Bean) 생성 및 서버 기동 실패

- **Solution**: 하이버네이트 로그를 추적하여 리포지토리의 @Query 내 필드명을 엔티티 클래스의 실제 변수명과 일치시켜 해결

#### 3. JSON 무한 순환 참조(Infinite Recursion) 해결 ####
- **Issue**: 양방향 연관 관계인 엔티티를 직접 반환 시 Jackson 직렬화 중 무한 루프 발생

- **Solution**: DTO(Data Transfer Object) 패턴을 도입하여 엔티티와 응답 데이터를 완전히 분리. 필요한 정보만 선별하여 반환함으로써 성능 최적화 및 순환 참조 근본적 해결

#### 4. RESTful API 설계 및 전역 예외 처리 ####
- **Issue**: 동일한 매핑 주소 사용으로 인한 Ambiguous mapping 에러 및 불친절한 에러 페이지 노출

- **Solution**: 
  - 검색 엔드포인트를 /api/restaurants/search로 명확히 분리
  - @RestControllerAdvice 구현을 통해 검색 결과 부재 시 404 Not Found 및 커스텀 메시지 응답 로직 완성

## API Specification
#### 식당 검색 (POST)
``` POST /api/restaurants/search ```

#### Request Body

```
JSON

["토마토", "오이"]
```

#### Response Body (DTO)

```
JSON

[
  {
    "id": 4,
    "name": "사당 마라탕",
    "address": "동작구",
    "category": "중식",
    "menuNames": ["기본 마라탕"]
  }
]
```

## Development Status (진행 상황)

현재 프로젝트는 핵심 기능 구현 단계에 있으며, 전체 로드맵의 약 50%를 소화한 상태입니다. 
단순한 기능 구현을 넘어 서버의 동작 원리를 이해하고 코드의 품질을 높이는 과정을 병행하고 있습니다.

### 완료된 항목
- **기획 및 설계 (1주차)**
  - [x] MVP 기능 명세 및 사용자 흐름(User Flow) 정의
  - [x] 데이터베이스 ERD 설계 (Restaurant, Menu, Ingredient 관계 매핑)
  - [x] Spring Boot 레이어드 아키텍처(Controller-Service-Repository) 설정
- **핵심 로직 개발 (2주차)**
  - [x] JPA를 활용한 도메인 엔티티(Entity) 구현 및 연관관계 설정
  - [x] 식당, 메뉴, 재료 관리를 위한 기초 CRUD API 개발
  - [x] `NOT IN` 쿼리를 활용한 기피 재료 제외 필터링 로직 구현
### 진행 중 및 예정 항목
- **기능 고도화 및 안정화 (3주차 - 진행 중)**
  - [ ] `@ControllerAdvice`를 활용한 전역 예외 처리(Global Exception Handling)
  - [ ] QueryDSL 등을 활용한 다중 필터링 동적 쿼리 최적화
  - [ ] JUnit5와 AssertJ를 이용한 비즈니스 로직 단위 테스트 작성
- **프론트엔드 연동 (4주차)**
  - [ ] React/Next.js 기반의 검색 및 필터링 UI 구현
  - [ ] Axios를 이용한 비동기 API 통신 및 CORS 문제 해결
- **배포 및 문서화 (5주차)**
  - [ ] AWS EC2 및 Docker를 활용한 서버 배포
  - [ ] 프로젝트 회고 및 기술 블로그(Velog) 포스팅 정리
