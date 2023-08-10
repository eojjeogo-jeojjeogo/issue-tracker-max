package com.codesquad.issuetracker.api.member.controller;

import com.codesquad.issuetracker.api.member.Service.MemberService;
import com.codesquad.issuetracker.api.member.dto.request.SignInRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignUpRequest;
import com.codesquad.issuetracker.api.member.dto.response.SignInResponse;
import com.codesquad.issuetracker.oauth.dto.request.OauthSignInRequest;
import java.util.Collections;
import java.util.Map;
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

    @PostMapping("/api/oauth/sign-up/{provider}")
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
    public ResponseEntity<Map<String, Long>> signUp(@RequestBody SignUpRequest signUpRequest,
                                                    @PathVariable String provider) {
        Long memberId = memberService.signUp(signUpRequest, provider);
        return ResponseEntity.ok()
                .body(Collections.singletonMap("id", memberId));
    }

    @PostMapping("/api/sign-In")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        SignInResponse oAuthSignInResponse = memberService.signIn(signInRequest);
        return ResponseEntity.ok()
                .body(oAuthSignInResponse);
    }
}
