package com.seulseul.seulseul.service.firebase;//package com.seulseul.seulseul.service.firebase;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.apache.coyote.Response;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class FirebaseCloudMessageService {
//
//    private final String API_URL = "https://fcm.googleapis.com/v1/projects/본인 프로젝트 ID/messages:send";
//    private final ObjectMapper objectMapper;
//
//    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
//        String message = makeMessage(targetToken, title, body);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//
//        Response response = client.newCall(request)
//                .execute();
//
//        System.out.println(response.body().string());
//    }
//
//    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
//        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(FcmMessage.Message.builder()
//                        .token(targetToken)
//                        .notification(FcmMessage.Notification.builder()
//                                .title(title)
//                                .body(body)
//                                .image(null)
//                                .build()
//                        )
//                        .build()
//                )
//                .validate_only(false)
//                .build();
//
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//
//    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "/firebase/다운 받은 비공개키.json";
//
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
//
//}
