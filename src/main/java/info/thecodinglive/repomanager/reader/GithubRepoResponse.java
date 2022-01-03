package info.thecodinglive.repomanager.reader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubRepoResponse {
    @JsonProperty("full_name")
    private String fullName;
}
