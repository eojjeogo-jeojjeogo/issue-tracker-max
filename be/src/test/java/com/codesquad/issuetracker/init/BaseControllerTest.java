package com.codesquad.issuetracker.init;

import com.codesquad.issuetracker.api.comment.service.CommentService;
import com.codesquad.issuetracker.api.jwt.domain.Jwt;
import com.codesquad.issuetracker.api.jwt.service.JwtProvider;
import com.codesquad.issuetracker.api.label.service.LabelService;
import com.codesquad.issuetracker.api.member.service.MemberService;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseControllerTest {

    public static final long MEMBER_ID = 1L;
    public static final Long ISSUE_ID = 1L;
    public static final Long COMMENT_ID = 1L;
    public static final String AUTHORIZATION = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String TEST_ORGANIZATION_NAME = "testOrganization";

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public CommentService commentService;

    @Autowired
    public MilestoneService milestoneService;

    @Autowired
    public MemberService memberService;

    @Autowired
    public LabelService labelService;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public JwtProvider jwtProvider;

    public Jwt jwt;

    @BeforeEach
    void init() {
        jwt = jwtProvider.createTokens(Collections.singletonMap("memberId", MEMBER_ID));
    }
}
