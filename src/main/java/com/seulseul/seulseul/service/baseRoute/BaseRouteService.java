package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteJsonDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class BaseRouteService {
    private WebClient webClient;
    private ApiKey apiKey;
    private BaseRouteRepository baseRouteRepository;

    public BaseRouteService(WebClient.Builder webClientBuilder,ApiKey apiKey, BaseRouteRepository baseRouteRepository) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.odsay.com/v1/api/")
                .defaultHeader("Content-Type", "application/json")
                .codecs(clientCodecConfigurer -> {clientCodecConfigurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024);})
                .build();
        this.apiKey = apiKey;
        this.baseRouteRepository = baseRouteRepository;
    }

    public Mono<BaseRouteDto> getResponseTest() throws IOException {
        return webClient.get()
                .uri("searchPubTransPathT?lang=0&SX={from android}&SY={from android}&EX={from android}&EY={from android}&SearchPathType=1&apiKey={apiKey}",
                        apiKey.getApiKey())
                .retrieve()
                .bodyToMono(String.class)
                .map(jsonData -> {
                    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        BaseRouteJsonDto jsonDto = mapper.readValue(jsonData, BaseRouteJsonDto.class);
                        String firstStation = jsonDto.getResult().getPath().get(0).getInfo().getFirstStartStation();
                        String lastStation = jsonDto.getResult().getPath().get(0).getInfo().getLastEndStation();
                        int startStation = jsonDto.getResult().getPath().get(0).getSubPath().get(1).getStartID();
                        int subPathLen = jsonDto.getResult().getPath().get(0).getSubPath().size();
                        int endStation = jsonDto.getResult().getPath().get(0).getSubPath().get(subPathLen - 2).getEndID();

                        // 여기서 new로 인스턴스화하는 것 괜찮은가?
                        BaseRouteDto baseRouteDto = new BaseRouteDto();
                        // setter말고 다르게 저장하자 -> setter 괜찮다. 단순 데이터 전달이라서. 근데 너무 코드 가독성이 떨어진다..
                        baseRouteDto.setFirstStation(firstStation);
                        baseRouteDto.setLastStation(lastStation);
                        baseRouteDto.setStartStation(startStation);
                        baseRouteDto.setEndStation(endStation);
                        baseRouteRepository.save(new BaseRoute(baseRouteDto.getFirstStation(),
                                baseRouteDto.getLastStation(),
                                baseRouteDto.getStartStation(),
                                baseRouteDto.getEndStation()));
                        return baseRouteDto;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("json parsing error : " + e);
                    }
                });
    }


}
