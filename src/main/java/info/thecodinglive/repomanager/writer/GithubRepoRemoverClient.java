package info.thecodinglive.repomanager.writer;

import info.thecodinglive.repomanager.utils.CustomUriBuilderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubRepoRemoverClient {
    private final String API_DOMAIN = "https://api.github.com";

    public void removeGithubRepo(String repoUrl) {
        try {
            WebClient webClient = WebClient.builder()
                    .uriBuilderFactory(new CustomUriBuilderFactory(API_DOMAIN))
                    .build();
            webClient
                    .delete()
                    .uri(uriBuilder -> uriBuilder
                            .path(UriUtils.decode("/repos/{repoUrl}", StandardCharsets.UTF_8))
                            .build(repoUrl))
                    .header("Authorization", String.format("%s %s", "token", readToken()))
                    .retrieve()
                    .toBodilessEntity().block();
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

    private String readToken() throws Exception {
        String fileName = "github_token.txt";
        URL resource = ClassLoader.getSystemResource(fileName);
        File tokenFile = new File(resource.getFile());
        String githubToken = Files.readString(tokenFile.toPath(), StandardCharsets.UTF_8);
       return githubToken;
    }
}
