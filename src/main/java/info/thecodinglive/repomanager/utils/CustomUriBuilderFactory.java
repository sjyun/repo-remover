package info.thecodinglive.repomanager.utils;

import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * webClient에서 pathVariable 사용시 slash가 자동으로 escapse되는 것을 막는다.
 */
public class CustomUriBuilderFactory extends DefaultUriBuilderFactory {
    public CustomUriBuilderFactory(String baseUriTemplate) {
        super(UriComponentsBuilder.fromHttpUrl(baseUriTemplate));
        super.setEncodingMode(EncodingMode.NONE);
    }
}
