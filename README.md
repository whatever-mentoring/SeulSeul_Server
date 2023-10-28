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
|![](https://media.discordapp.net/attachments/1143088443224772680/1163127279363313674/2.png?ex=653e71b1&is=652bfcb1&hm=2139b369609fff6e0e9cc9d1a73f1f01808f480e21e6f5903559a2512212410f&=&width=377&height=670)|![](https://media.discordapp.net/attachments/1143088443224772680/1163127279845654558/3.png?ex=653e71b1&is=652bfcb1&hm=9d5b04f2eabe386af671e7dd3f7a9bbcece6b1000f712aa85014e6840b3e28ba&=&width=377&height=670)|![](https://media.discordapp.net/attachments/1143088443224772680/1163127280273477654/4.png?ex=653e71b1&is=652bfcb1&hm=7b65f98781b0f99556e8f699260d36a2d47f04cb3f33f4e9a6409e2bab730843&=&width=377&height=670)|![](https://media.discordapp.net/attachments/1143088443224772680/1163127280692895785/5.png?ex=653e71b1&is=652bfcb1&hm=5c72c6e9aca7e4f968803f100bdae80250bf4275f879d5348a65cba7fde8b50d&=&width=377&height=670)|![](https://media.discordapp.net/attachments/1143088443224772680/1163129626344828969/6.png?ex=653e73e0&is=652bfee0&hm=c0dc5af139b01eb1c5e3a0439d7dcd997a326b9f8c68b22dd453e96c899899cb&=&width=377&height=670)


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

<https://youtu.be/K_m05GKCufQ?si=N9H8KQdBQ5DJ1u65>

## 🚉 Contributor
|<img width=150 src="https://avatars.githubusercontent.com/u/125520029?v=4" />|<img width=150 src="https://avatars.githubusercontent.com/u/68958749?v=4" />
|:----:|:----:|
| [박서연](https://github.com/seoyeon0201) | [주다애](https://github.com/jooda00)
