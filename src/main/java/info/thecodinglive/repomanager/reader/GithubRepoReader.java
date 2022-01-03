package info.thecodinglive.repomanager.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
@Slf4j
@Component
public class GithubRepoReader {
    //https://api.github.com/users/{userId}/repos?per_page=200
    private final String API_DOMAIN = "https://api.github.com";

    private ExchangeStrategies createExchangeStrategies() {
        return ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(-1))
                .build();
    }

    public List<GithubRepoResponse> getGithubRepositories(String userId, Integer pageCount) {
        WebClient webClient = WebClient.builder().exchangeStrategies(createExchangeStrategies()).baseUrl(API_DOMAIN).build();
        Mono<List<GithubRepoResponse>> response = webClient
                .get().uri(uriBuilder -> uriBuilder
              .path("/users/{userId}/repos")
              .queryParam("per_page", pageCount)
              .build(userId))
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<>() {});

        List<GithubRepoResponse> responseList = response.block(Duration.ofSeconds(6));
        LOG.debug("size: {}", responseList.size());
        LOG.debug("data: {}", responseList.toString());
        return responseList;
    }

    private Mono<List<GithubRepoResponse>> findGithubRepo(String userId, Integer pageCount) {
        WebClient webClient = WebClient.builder().exchangeStrategies(createExchangeStrategies()).baseUrl(API_DOMAIN).build();
        Mono<List<GithubRepoResponse>> response = webClient
                .get().uri(uriBuilder -> uriBuilder
                        .path("/users/{userId}/repos")
                        .queryParam("per_page", pageCount)
                        .build(userId))
                .retrieve()
                .bodyToFlux(GithubRepoResponse.class).collectList();

        return response;
    }
}
