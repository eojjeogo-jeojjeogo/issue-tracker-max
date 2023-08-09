package com.codesquad.issuetracker.api.oauth.controller;

import com.codesquad.issuetracker.api.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;


}
