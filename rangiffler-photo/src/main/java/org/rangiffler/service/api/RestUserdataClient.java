package org.rangiffler.service.api;


import org.springframework.stereotype.Component;



import java.net.URI;
import java.util.List;

@Component
public class RestUserdataClient {
//    private final WebClient webClient;
//
//    private final String userdataUri;
//
//
//    @Autowired
//    public RestUserdataClient(WebClient webClient,
//                              @Value("${rangiffler-users.base-uri}") String userdataUri) {
//        this.webClient = webClient;
//        this.userdataUri = userdataUri;
//    }
//
//    public @Nonnull
//    List<UserJson> friends(@Nonnull String username) {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("username", username);
//        URI uri = UriComponentsBuilder.fromHttpUrl(userdataUri + "/friends").queryParams(params).build().toUri();
//
//        return webClient.get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<List<UserJson>>() {
//                })
//                .block();
//    }
//    @Bean
//    public WebClient webClient() {
//        return WebClient
//                .builder()
//                .exchangeStrategies(ExchangeStrategies.builder().codecs(
//                        configurer -> configurer.defaultCodecs().maxInMemorySize(Integer.MAX_VALUE)).build())
//                .build();
//    }

}
