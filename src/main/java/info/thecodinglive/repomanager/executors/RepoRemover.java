package info.thecodinglive.repomanager.executors;

import info.thecodinglive.repomanager.reader.GithubRepoReader;
import info.thecodinglive.repomanager.reader.GithubRepoResponse;
import info.thecodinglive.repomanager.writer.GithubRepoRemoverClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RepoRemover {

    private final GithubRepoReader githubRepoReader;
    private final GithubRepoRemoverClient githubRepoRemoverClient;

    //삭제할 깃헙저장소를 소유하고 있는 사용자 아이디
    @Value("${info.thecodinglive.remover-user-id}")
    private String userId;
    @Value("${info.thecodinglive.pageCount}")
    private Integer pageCount;


    public void removeBlukGithubRepo() {
       List<GithubRepoResponse> responseList =  githubRepoReader.getGithubRepositories(userId, pageCount);
       //NPE
       if (CollectionUtils.isEmpty(responseList)) {return;}

       responseList
               .forEach(d ->  githubRepoRemoverClient.removeGithubRepo(urlDecode(d.getFullName())));
    }

    private String urlDecode(String originTxt) {
        try {
            return URLDecoder.decode(originTxt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return originTxt;
    }
}
