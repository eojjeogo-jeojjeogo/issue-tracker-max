package com.codesquad.issuetracker.api.comment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.codesquad.issuetracker.api.comment.dto.request.CommentCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("comment 생성요청이 들어오면 comment를 저장한뒤 commentId를 포함한 응답을 전송한다.")
    void commentCreateTest() throws Exception {
        //given
        String organizationTitle = "testOrganization";
        String issueId = "1";
        String request = objectMapper.writeValueAsString(dummyCommentCreateRequest());

        //when
        ResultActions resultActions =
            mockMvc.perform(
                MockMvcRequestBuilders.post("/api/{organizationTitle}/issues/{issueId}/comments",
                        organizationTitle, issueId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request));

        //then
        resultActions.andExpect(jsonPath("$.id").value(1));
    }

    private CommentCreateRequest dummyCommentCreateRequest() {
        return CommentCreateRequest.builder()
            .content("testContent")
            .fileUrl("fileUrl")
            .build();
    }
    
}