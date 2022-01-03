package info.thecodinglive.repomanager.reader;

import info.thecodinglive.repomanager.writer.GithubRepoRemoverClient;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class GithubRepoReadTest {
    @InjectMocks
    GithubRepoReader githubRepoReader;
    @InjectMocks
    GithubRepoRemoverClient githubRepoRemoverClient;
    @InjectMocks
    AppProperties appProperties;

    @Test
    void readGithubRepos() {
        //given
        String userId = "sjyun";
        //when
        List<GithubRepoResponse> responses = githubRepoReader.getGithubRepositories(userId, 100);
        //then
        Assertions.assertTrue(CollectionUtils.isNotEmpty(responses));
    }

    @Test
    void removeGithubRepo() {
        //given
        String repoUrl = "sjyun/lucene-solr";
        //when
        githubRepoRemoverClient.removeGithubRepo(repoUrl);
        //then
    }

    @Test
    void readToken() throws Exception {
        //given
        String fileName = "github_token.txt";
        //return new String(Files.readAllBytes(Paths.get("classpath:github_token.txt")));
        URL resource = ClassLoader.getSystemResource(fileName);
        File tokenFile = new File(resource.getFile());
        String githubToken = Files.readString(tokenFile.toPath(), StandardCharsets.UTF_8);
        System.out.println(githubToken);
    }



    @Test
    void urlDecodeTes() throws Exception {
        //given
        String url = "https://api.github.com/repos/sjyun%2Fguava";
        //when
        String decodeUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
        //then
        System.out.println(decodeUrl);
    }
}
