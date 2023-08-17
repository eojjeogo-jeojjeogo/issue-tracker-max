package com.codesquad.issuetracker.init;

import com.codesquad.issuetracker.api.comment.repository.CommentRepository;
import com.codesquad.issuetracker.api.jwt.domain.Jwt;
import com.codesquad.issuetracker.api.jwt.service.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseControllerTest {

    public static final long ID = 1L;
    public static final String MEMBER_ID = "memberId";
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public ObjectMapper objectMapper;

    @Autowired
    public JwtProvider jwtProvider;
    
    public Jwt jwt;

    @BeforeEach
    void init() {
        jwt = jwtProvider.createTokens(Collections.singletonMap(MEMBER_ID, ID));
    }
}
