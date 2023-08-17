package com.codesquad.issuetracker.api.common.controller;

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
class CommonControllerTest extends BaseControllerTest {

    @DisplayName("레이블 및 마일스톤 개수 조회")
    @Test
    void getNavigationInfo() throws Exception {
        // given
        MilestoneRequest milestoneRequest1 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.08.02");
        MilestoneRequest milestoneRequest2 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.07.15");
        MilestoneRequest milestoneRequest3 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.07.02");
        milestoneService.create(TEST_ORGANIZATION_NAME, milestoneRequest1);
        milestoneService.create(TEST_ORGANIZATION_NAME, milestoneRequest2);
        milestoneService.create(TEST_ORGANIZATION_NAME, milestoneRequest3);

        LabelCreateRequest labelCreateRequest1 = new LabelCreateRequest("레이블1", "레이블 설명1", "#004DE3", true);
        LabelCreateRequest labelCreateRequest2 = new LabelCreateRequest("레이블2", "레이블 설명2", "#004DE4", true);
        LabelCreateRequest labelCreateRequest3 = new LabelCreateRequest("레이블3", "레이블 설명3", "#004DE5", true);
        LabelCreateRequest labelCreateRequest4 = new LabelCreateRequest("레이블4", "레이블 설명4", "#004DE6", true);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest1);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest2);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest3);
        labelService.create(TEST_ORGANIZATION_NAME, labelCreateRequest4);

        // when
        mockMvc.perform(get("/api/" + TEST_ORGANIZATION_NAME + "/common/navigation")
                        .header(AUTHORIZATION, JWT_TOKEN_PREFIX + jwt.getAccessToken()))
                // then
                .andDo(print())
                .andExpect(jsonPath("labelsCount").value(4))
                .andExpect(jsonPath("milestonesCount").value(3));
    }
}
