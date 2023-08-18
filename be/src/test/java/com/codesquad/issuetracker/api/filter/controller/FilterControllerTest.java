package com.codesquad.issuetracker.api.filter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.codesquad.issuetracker.annotation.ControllerTest;
import com.codesquad.issuetracker.api.label.dto.request.LabelCreateRequest;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.init.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
