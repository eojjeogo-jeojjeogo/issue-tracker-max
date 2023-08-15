package com.codesquad.issuetracker.api.member.controller;

import com.codesquad.issuetracker.api.jwt.service.JwtService;
import com.codesquad.issuetracker.api.member.dto.request.RefreshTokenRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignInRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignUpRequest;
import com.codesquad.issuetracker.api.member.dto.response.SignInResponse;
import com.codesquad.issuetracker.api.member.service.MemberService;
import com.codesquad.issuetracker.api.oauth.dto.request.OauthSignInRequest;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("/api/oauth/sign-in/{provider}")
    public ResponseEntity<SignInResponse> oAuthSignIn(@RequestBody OauthSignInRequest oauthSignInRequest,
                                                      @PathVariable String provider) {
        SignInResponse oAuthSignInResponse = memberService.oAuthSignIn(
                provider,
                oauthSignInRequest.getAccessCode()
        );
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }

    @PostMapping("/api/sign-up/{provider}")
    public ResponseEntity<Map<String, Long>> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                    @PathVariable String provider) {
        Long memberId = memberService.signUp(signUpRequest, provider);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("id", memberId));
    }

    @PostMapping("/api/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        SignInResponse oAuthSignInResponse = memberService.signIn(signInRequest);
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }

    @PostMapping("/api/reissue-access-token")
    public ResponseEntity<Map<String, String>> reissueAccessToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest) {
        String accessToken = jwtService.reissueAccessToken(refreshTokenRequest);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("accessToken", accessToken));
    }
}
