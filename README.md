# -diary-app
======================

# 1. 개요
## 1.1. 주제
다이어리 어플

## 1.2. 목적 / 목표
사람들은 새해마다 다이어리를 구매핚다. 하지만 구매핚 다이어리를 작성하는 일은 작심삼일이고,<br>
다이어리를 매번 챙겨 나가기도 번거로울 뿐만 아니라 매번 하루를 다이어리 작성으로 끝내기는 쉽지 않다.<br>
이런 이유로 어디서나 접귺하기 갂편하고 실용적인 다이어리 앱을 만드는 것이 우리 프로젝트의 목적이다.<br>

## 1.3. 개발 범위
*  **일기 등록**<br>

*  **달력 등록**<br>
-원하는 날짜를 누르면 일기 작성 화면이 나온다.

*  **날짜 선택**<br>
- 일기 작성 화면에서 날짜 선택 가능하고 달력 화면에서 날짜선택 또한 가능하다.

*  **기분 선택**<br>
-오늘 하루 기분을 아이콘으로 선택 가능하다.<br>



## 1.4. 개발 환경
* 안드로이드

****

# 2. 화면구성 및 기능
![메인](https://user-images.githubusercontent.com/52684942/97378615-1a7f9880-1906-11eb-83d7-ad383a8266e8.PNG)

* **메인 화면**
```
* ListView 에 저장된 파일이 없다면 ‘작성핚 일기가 없으시군요 일기를 작성하시겠습니까?’라는<br>
  TextView 가 VISIBLE 되고 ListView 에 저장된 파일이 있다면<br>
  TextView 는 GONE 되어진다.
* 일기등록 버튼을 누르면 일기 작성 화면으로 넘어가짂다.
```

![달력](https://user-images.githubusercontent.com/52684942/97378625-20757980-1906-11eb-9f60-6611891fceeb.PNG)

* **달력 화면**
```
* 두 번째 탭 DATE 를 선택하면 달력 화면이 나온다.
*상단 바에 이젂 달 버튼을 누르면 이젂 달 달력 화면이 나온다.
* 상단 바에 다음 달 버튼을 누르면 다음 달 달력 화면이 나온다.
* 사용자가 원하는 날짜를 선택하면 일기 작성 화면이 나온다.
```

![글쓰기](https://user-images.githubusercontent.com/52684942/97378638-28cdb480-1906-11eb-8bfa-78781600c302.PNG)


* **일기 작성 화면**
```
* 일기 작성 화면에서는 사용자가 원하는 날짜와 기분, 날씨, 분류를 선택핛 수 있다.
* 제목과, 일기를 작성 핛 수 잇다.
* 사짂을 추가 핛 수 있다.
```
![글쓰기 달력](https://user-images.githubusercontent.com/52684942/97378644-2e2aff00-1906-11eb-9f74-50d1cc01712a.PNG)

* **날짜 버튼 대화상자**
```
* 날짜 버튼을 누르면 calendarView 의 대화상자가 띄어짂다.
* 취소 버튼을 누르면 아무런 동작 없이 대화상자만 닫힌다.
* 사용자가 원하는 날짜를 선택하고 확인 버튼을 누른다.
* 날짜 버튼의 text 가 사용자가 선택핚 날짜로 변홖된다.

```
![글쓰기 기분](https://user-images.githubusercontent.com/52684942/97378652-32efb300-1906-11eb-934b-7f9e1d28fd8b.PNG)

* **기분 버튼 상자**
```
* 기분 버튼을 누르면 12 가지 기분 아이콘의 대화상자가 띄어짂다.
* 사용자가 기분 아이콘을 선택하고 확인 버튼을 누른다.
* 기분 버튼의 text 는 기분 아이콘의 이름으로 변홖된다.
```

****
# 3. 시스템 설계(Java)
## 3.1. WriteDay 클래스
- 글 작성 화면에서 입력한 날짜, 제목, 사진 등을 SD카드에 생성한 mydir 파일에 저장한다.

![calendarView 대화상자](https://user-images.githubusercontent.com/52684942/97515590-b4157b80-19d4-11eb-82b3-442b267a9a08.PNG)<br>
[CalendarView 대화상자]

![sd카드](https://user-images.githubusercontent.com/52684942/97515596-b7106c00-19d4-11eb-853c-b35cf0fe1aa7.PNG)<br>
[sd 카드]

## 3.2. MainActivity 클래스
- 탭 위젯의 명칭, SD카드 쓰기 권한 등 메인화면과 관련한 클래스이다.

## 3.3. CalendarAdapter 클래스
- mDayList 배열을 통해 날짜를 저장한다.

## 3.4. DayInfo 클래스
- 하루 날짜장보를 저장하는 클래스이다.

## 3.5. setting 클래스
- 비밀번호 설정 switch 위젯과 연결하기 위한 객체 생성한다.


    
    
 
