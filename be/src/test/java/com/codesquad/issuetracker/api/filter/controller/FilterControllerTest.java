package com.codesquad.issuetracker.api.filter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.codesquad.issuetracker.annotation.ControllerTest;
import com.codesquad.issuetracker.api.label.dto.request.LabelCreateRequest;
import com.codesquad.issuetracker.api.member.dto.request.SignUpRequest;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.init.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@ControllerTest
class FilterControllerTest extends BaseControllerTest {

    @DisplayName("마일스톤 필드 목록 조회 한다")
    @Test
    void readMilestones() throws Exception {
        // given
        MilestoneRequest milestoneRequest1 = new MilestoneRequest("마일스톤1", "설명1", "2023.08.01");
        MilestoneRequest milestoneRequest2 = new MilestoneRequest("마일스톤2", "설명2", "2023.08.11");
        milestoneService.create(TEST_ORGANIZATION_NAME, milestoneRequest1);
        milestoneService.create(TEST_ORGANIZATION_NAME, milestoneRequest2);

        // when
        mockMvc.perform(get("/api/" + TEST_ORGANIZATION_NAME + "/milestones")
                        .param("type", "filter")
                        .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken()))
                // then
                .andDo(print())
                .andExpect(jsonPath("*.id").exists())
                .andExpect(jsonPath("*.name").exists());
    }

    @DisplayName("레이블 필드 목록 조회 한다")
    @Test
    void readLabels() throws Exception {
        // given
        LabelCreateRequest labelCreateRequest1 = new LabelCreateRequest("레이블1", "레이블 설명1", "#004DE3", true);
        LabelCreateRequest labelCreateRequest2 = new LabelCreateRequest("레이블2", "레이블 설명2", "#004DE4", false);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest1);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest2);

        // when
        mockMvc.perform(get("/api/" + TEST_ORGANIZATION_NAME + "/labels")
                        .param("type", "filter")
                        .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken()))
                // then
                .andDo(print())
                .andExpect(jsonPath("*.id").exists())
                .andExpect(jsonPath("*.name").exists())
                .andExpect(jsonPath("*.backgroundColor").exists())
                .andExpect(jsonPath("*.isDark").exists());
    }

    /**
     * 담당자 필터에서 필요한 담당자를 가져오기 위해선 organization_member 테이블에 정보가 필요한데, 아직 Organization 에 대한 CRUD가 없기 때문에 불가능하다.
     *
     * @throws Exception
     */
    @Sql(statements = {
            "INSERT INTO organization_member VALUES (1,1);",
            "INSERT INTO organization_member VALUES (1,2);"
    })
    @DisplayName("담당자 필드 목록을 조회 한다.")
    @Test
    void readAssignees() throws Exception {
        // given
        SignUpRequest tester1 = SignUpRequest.builder()
                .email("aaa@naver.com")
                .nickname("tester1")
                .password("123456")
                .build();

        SignUpRequest tester2 = SignUpRequest.builder()
                .email("bbb@naver.com")
                .nickname("tester2")
                .password("123456")
                .build();

        memberService.signUp(tester1, "local");
        memberService.signUp(tester2, "local");

        // when
        mockMvc.perform(get("/api/" + TEST_ORGANIZATION_NAME + "/assignees")
                        .param("type", "filter")
                        .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken()))
                // then
                .andDo(print())
                .andExpect(jsonPath("*.id").exists())
                .andExpect(jsonPath("*.name").exists())
                .andExpect(jsonPath("*.imgUrl").exists());
    }
}
