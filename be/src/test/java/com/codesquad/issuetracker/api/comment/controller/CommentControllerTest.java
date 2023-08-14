package com.codesquad.issuetracker.api.comment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
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
    void commentCreate() throws Exception {
        //given
        String organizationTitle = "testOrganization";
        String issueId = "1";
        String request = objectMapper.writeValueAsString(dummyCommentRequest());

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/{organizationTitle}/issues/{issueId}/comments",
                                        organizationTitle, issueId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request));

        //then
        resultActions.andExpect(jsonPath("$.id").value(issueId));
    }

    @Sql(scripts = "classpath:schemaForDummyData.sql")
    @Test
    @DisplayName("comment의 title, fileUrl 수정 요청이 들어오면 해당 comment를 수정한뒤 commentId를 포함한 응답을 전송한다.")
    void commentUpdateWithTitleAndFileUrl() throws Exception {
        //given
        String organizationTitle = "testOrganization";
        String issueId = "1";
        String commentId = "1";
        String request = objectMapper.writeValueAsString(
                dummyCommentRequest());

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.patch(
                                        "/api/{organizationTitle}/issues/{issueId}/comments/{commentId}",
                                        organizationTitle, issueId, commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request));

        //then
        resultActions.andExpect(jsonPath("$.id").value(issueId));
    }

    private CommentRequest dummyCommentRequest() {
        return CommentRequest.builder()
                .content("testContent")
                .fileUrl("fileUrl")
                .build();
    }

}
