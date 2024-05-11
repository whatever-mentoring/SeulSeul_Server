# SeulSeul

> 현재 위치 기반으로 막차 시간에 따른 알림을 전송하는 서비스

> 프로젝트 기간: 2023.08.26 ~ 2023.10.06

![image](https://media.discordapp.net/attachments/1143088443224772680/1163125169766805574/6fefef5562b75d85.jpg?ex=653e6fba&is=652bfaba&hm=0b08d0a7401d80e45f7007ad7a04c9ec1e9bf784f60cc7f650818083daa1e698&=&width=1193&height=671)


## 🚉 Introduce 

지하철 막차를 놓치지 않도록 알림 서비스를 제공합니다.

항상 아슬아슬하게 타거나 막차를 놓쳤던 당신!

슬슬을 통해 여유있고 안전한 귀가를 경험해보세요.



## 🚉 Core Function

|목적지 설정|알림 설정|경로 안내|알림 온 오프|알림 수신|
|-----------|---------|--------|--------|--------|
|![목적지설정](https://github.com/whatever-mentoring/SeulSeul_Server/assets/125520029/aad9a67a-03b2-431f-92ab-1d3c6fa2aaf5)|![알림설정](https://github.com/whatever-mentoring/SeulSeul_Server/assets/125520029/3a05d88f-b061-45f1-a347-ca86654f353e)|![경로안내](https://github.com/whatever-mentoring/SeulSeul_Server/assets/125520029/016a03c8-0943-4bab-ad01-96ce5e009035)|![알림온오프](https://github.com/whatever-mentoring/SeulSeul_Server/assets/125520029/6d17999f-03cc-4dd0-b461-67de1815e0e0)|![알림수신](https://github.com/whatever-mentoring/SeulSeul_Server/assets/125520029/4d0db273-dfc6-4128-b3d6-dbba51329f5e)







## 🚉 Structure

```markdown
src.main
└── java
     └── com.seulseul.seulseul
                ├── config
                |      ├── CustomException
                |      ├── CustomExceptionHandler
                |      └── ErrorCode
                |
                ├── controller  
                |      ├── alarm
                |      ├── android   
                |      ├── baseRoute
                |      ├── endPos
                |      ├── firebase
                |      ├── stopTimeList
                |      ├── transferInfo  
                |      └── user
                |
                ├── dto
                |      ├── alarm
                |      ├── android   
                |      ├── baseRoute
                |      ├── endPos
                |      ├── firebase
                |      ├── Response
                |      ├── stopTimeList
                |      ├── transferInfo  
                |      └── user
                |
                ├── entity 
                |      ├── alarm
                |      ├── android   
                |      ├── baseRoute
                |      ├── endPos
                |      ├── stopTimeList
                |      ├── transferInfo  
                |      ├── user
                |      ├── ApiKey
                |      └── TokenKey
                |
                ├── repository
                |      ├── alarm
                |      ├── android   
                |      ├── baseRoute
                |      ├── endPos
                |      ├── stopTimeList
                |      ├── transferInfo  
                |      └── user
                |
                ├── service
                |      ├── alarm
                |      ├── android   
                |      ├── baseRoute
                |      ├── endPos
                |      ├── firebase
                |      ├── result
                |      ├── stopTimeList
                |      ├── transferInfo  
                |      └── user
                |
                └── SeulseulApplication

```


## 🚉 Architecture
![KakaoTalk_20231016_170308654](https://github.com/whatever-mentoring/SeulSeul_Server/assets/68958749/b69f2577-10ec-455b-8aad-4f057695dc47)


## 🚉 Tech Stack

Language: Java

Library & Framework: SpringBoot

Database: AWS RDS (MySQL)

Deploy: AWS EC2

## 🚉 시연 영상

develop branch's commit `88cdb7702a145cfeccdd1b8f2645e694a7f720d9`

<https://youtu.be/K_m05GKCufQ?si=N9H8KQdBQ5DJ1u65>

## 🚉 Contributor
|<img width=150 src="https://avatars.githubusercontent.com/u/125520029?v=4" />|<img width=150 src="https://avatars.githubusercontent.com/u/68958749?v=4" />
|:----:|:----:|
| [박서연](https://github.com/seoyeon0201) | [주다애](https://github.com/jooda00)
