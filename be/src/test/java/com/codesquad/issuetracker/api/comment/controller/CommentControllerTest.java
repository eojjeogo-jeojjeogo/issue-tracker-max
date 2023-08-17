package com.codesquad.issuetracker.api.comment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.issuetracker.annotation.ControllerTest;
import com.codesquad.issuetracker.api.comment.dto.request.CommentEmoticonAddRequest;
import com.codesquad.issuetracker.api.comment.dto.request.CommentRequest;
import com.codesquad.issuetracker.init.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ControllerTest
class CommentControllerTest extends BaseControllerTest {

    public static final long EMOTICON_ID = 1L;

    @Test
    @DisplayName("comment 생성요청이 들어오면 comment를 저장한뒤 commentId를 포함한 응답을 전송한다.")
    void commentCreate() throws Exception {
        //given
        CommentRequest commentRequest = new CommentRequest("코멘트");
        String request = objectMapper.writeValueAsString(commentRequest);

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/" + TEST_ORGANIZATION_NAME + "/issues/{issueId}/comments",
                                        ISSUE_ID)
                                .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request));

        //then
        resultActions.andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("comment의 title 수정 요청이 들어오면 해당 comment를 수정한뒤 commentId를 포함한 응답을 전송한다.")
    void commentUpdateWithTitleAndFileUrl() throws Exception {
        //given
        CommentRequest commentRequest = new CommentRequest("코멘트");
        commentService.createComment(ISSUE_ID, commentRequest, MEMBER_ID);

        String request = objectMapper.writeValueAsString(commentRequest);

        //when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.patch(
                                        "/api/" + TEST_ORGANIZATION_NAME + "/issues/{issueId}/comments/{commentId}", ISSUE_ID,
                                        COMMENT_ID)
                                .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request));

        //then
        resultActions.andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("comment에 이모티콘을 달수 있다.")
    void createCommentEmoticon() throws Exception {
        //given
        CommentEmoticonAddRequest commentEmoticonAddRequest = new CommentEmoticonAddRequest(EMOTICON_ID);

        String request = objectMapper.writeValueAsString(commentEmoticonAddRequest);
        //when
        ResultActions resultActions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(
                                        "/api/" + TEST_ORGANIZATION_NAME + "/issues/{issueId}/comments/{commentId}/emoticons",
                                        ISSUE_ID, COMMENT_ID)
                                .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request));

        //then
        resultActions.andExpect(status().isCreated());
    }

}
