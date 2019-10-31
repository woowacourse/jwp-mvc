### 1단계 reflection 실습
#### 1 - 클래스 정보 출력
- [x] ReflectionTest의 showClass() 구현 -> Question 클래스의 모든 필드, 생성자, 메소드 정보 출력

#### 2 - test로 시작하는 메소드 실행
- [x] junit3Test클래스의 test로 시작하는 메소드만 실행하도록 구현

###d# 3 - @Test 애노테이션 메소드 실행
- [x] junit에서는 @Test 어노테이션만 자동으로 실행. @MyTest만 실행하도록 구현

#### 4 - private field에 값 할당
- [x] Student 클래스의 NAme과 age 필드에 값을 할당.

#### 5 - 인자를 가진 생성자의 인스턴스 생성
- [x] Question클래스의 Constructor를 reflection해서 인스턴스 생성

#### 6 - component scan
- [x] examples 패키지의 @Controller, @Service, @Repository 애노테이션 붙은 클래스 찾아서 출력

### 1단계 MVC 구현
- [x] 어노테이션 핸들러 구현
- [x] 핸들러 익스큐션 구현
- [x] 레거시 코드 리팩토링

### 2단계 View 구현
- [x] JsonView 구현
- [x] 모든 Controller를 애노테이션 기반 MVC로 변경
- [x] UserAcceptanceTest 통과하도록 Controller 추가
