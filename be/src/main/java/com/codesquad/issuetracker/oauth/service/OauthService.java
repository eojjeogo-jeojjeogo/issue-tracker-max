package com.codesquad.issuetracker.oauth.service;

import com.codesquad.issuetracker.oauth.OauthAttributes;
import com.codesquad.issuetracker.oauth.config.InMemoryProviderRepository;
import com.codesquad.issuetracker.oauth.domain.OauthProvider;
import com.codesquad.issuetracker.oauth.dto.response.OauthTokenResponse;
import com.codesquad.issuetracker.oauth.dto.response.OauthUserProfile;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;

    public OauthUserProfile login(String providerName, String code) {
        // 프론트에서 넘어온 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider 가져오기
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        // access token 가져오기
        OauthTokenResponse tokenResponse = getToken(code, provider);

        // 유저 정보 가져오기
        OauthUserProfile oauthUserProfile = getUserProfile(providerName, tokenResponse, provider);

        return oauthUserProfile;
    }

    private OauthTokenResponse getToken(String code, OauthProvider provider) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUrl())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(OauthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
    }

    private OauthUserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse,
                                            OauthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        return OauthAttributes.extract(providerName, userAttributes);
    }

    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }
}
