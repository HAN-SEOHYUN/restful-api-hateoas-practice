## 식품 영양 정보 API
### 1. 소개
식품 영양 정보 데이터를 관리하기 위한 RESTful API 프로젝트입니다. 
요구사항에 따라 CRUD 및 검색(Search) 기능을 포함하고 있습니다. 
* 개발 언어: Java 21
* 프레임워크: Spring Boot 3.2.5
* 데이터베이스: MySQL 8.0
* 배포: Docker, AWS EC2

### 2. 요구사항 및 구현
주요 내용은 다음과 같습니다.
* 기능: Search API와 CRUD 기능 구현
* API 디자인: RESTful API 디자인 모범 사례 준수
* 문서화: GitHub README.md, API 사용법 문서 작성

### 3. RESTful API 
본 API는 RESTful 아키텍처를 준수합니다.

RESTful API의 성숙도 모델(Richardson Maturity Model)을 참고하여 URI, HTTP 메서드, HTTP 상태 코드, 하이퍼미디어(HATEOAS)를 적용했습니다.

제공된 [Richardson Maturity Model](https://martinfowler.com/articles/richardsonMaturityModel.html) 문서를 통해 RESTful API의 성숙도를 Level 0 ~ Level 3 단계로 나눠 이해하는 것을 돕는 RMM 모델에 대해 학습했습니다.

**정확한 이해를 위해 개인적으로 정리한 내용은 다음 링크에서 확인하실 수 있습니다.**
[개인블로그 - Restful 아키텍쳐: RMM의 의미와 각 단계의 설계적 의의](https://feelfreetothink.tistory.com/250)

### 4. API 테스트 방법
Postman을 사용하여 API를 테스트할 수 있도록 컬렉션을 제공합니다.
1. Postman 컬렉션 다운로드: **메일에 첨부된** Food Nutrition Facts API.postman_collection.json 파일을 다운로드합니다.
2. Postman에서 컬렉션 Import Postman 애플리케이션에서 Import 버튼을 클릭하여 다운로드한 JSON 파일을 불러옵니다.
3. 테스트 실행: 컬렉션에 포함된 각 요청을 선택하고 Send 버튼을 눌러 API 호출을 테스트할 수 있습니다.
   - 모든 요청의 URL은 http://98.83.105.239:8080으로 미리 설정되어 있습니다.

### 5. Spring REST Docs
* API 사용법 및 스펙은 Spring REST Docs 를 활용하여 생성된 문서를 통해 확인하실 수 있습니다. 
* Spring REST Docs 는 Spring 애플리케이션의 API 문서를 테스트 코드 기반으로 자동 생성해주는 도구입니다.
* 응답 본문의 _links 필드를 통해 API 대한 상세 문서 정보를 제공하여 클라이언트에서 API를 적극적으로 탐색할 수 있도록 설계했습니다.
#### [http://98.83.105.239:8080/docs/index.html](Spring REST Docs)

### 7. 개발 및 배포 환경
* 애플리케이션과 DB는 EC2 인스턴스에 배포되어 있습니다.







