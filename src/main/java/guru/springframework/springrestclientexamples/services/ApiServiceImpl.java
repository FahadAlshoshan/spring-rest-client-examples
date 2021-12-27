package guru.springframework.springrestclientexamples.services;

import guru.springframework.api.domain.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {
  private final String api_url;
  private RestTemplate restTemplate;

  public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
    this.restTemplate = restTemplate;
    this.api_url = api_url;
  }

  @Override
  public List<User> getUsers(Integer limit) {
    UriComponentsBuilder uriBuilder =
        UriComponentsBuilder.fromUriString(api_url).queryParam("_limit", limit);
    return restTemplate.getForObject(uriBuilder.toUriString(), List.class);
  }

  @Override
  public Flux<User> getUsers(Mono<Integer> limit) {
    return WebClient.create(api_url)
        .get()
        .uri(uriBuilder -> uriBuilder.queryParam("_limit", limit.block()).build())
        .retrieve()
        .bodyToFlux(User.class);
    //        .accept(MediaType.APPLICATION_JSON).exchange().flatMap(clientResponse ->
    // clientResponse.bodyToFlux(User.class));
  }

  @Override
  public User getUser(Integer id) {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(api_url);
    return restTemplate.getForObject(uriBuilder.toUriString() + id, User.class);
  }
}
