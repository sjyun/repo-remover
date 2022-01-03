package info.thecodinglive.repomanager.executors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 어플리케이션이 시작되면 삭제를 시작한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AppReadyRunner {
    private final RepoRemover repoRemover;

    //어플리케이션 기동이 완료되고 요청을 받을 준비가 되면 이벤트가 전달된다.
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        repoRemover.removeBlukGithubRepo();
    }
}
