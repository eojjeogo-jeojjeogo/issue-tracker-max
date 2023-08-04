package com.codesquad.issuetracker.api.common.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.codesquad.issuetracker.api.label.dto.LabelCreateRequest;
import com.codesquad.issuetracker.api.label.service.LabelService;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class CommonControllerTest {

    private static final String ORGANIZATION_TITLE = "eojjeogojeojjeogo";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MilestoneService milestoneService;
    @Autowired
    LabelService labelService;

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("레이블 및 마일스톤 개수 조회")
    @Test
    void getNavigationInfo() throws Exception {
        // given
        MilestoneRequest milestoneRequest1 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.08.02");
        MilestoneRequest milestoneRequest2 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.07.15");
        MilestoneRequest milestoneRequest3 = new MilestoneRequest("마일스톤1", "마일스톤 설명1", "2023.07.02");
        milestoneService.create(ORGANIZATION_TITLE, milestoneRequest1);
        milestoneService.create(ORGANIZATION_TITLE, milestoneRequest2);
        milestoneService.create(ORGANIZATION_TITLE, milestoneRequest3);

        LabelCreateRequest labelCreateRequest1 = new LabelCreateRequest("레이블1", "레이블 설명1", "#004DE3", true);
        LabelCreateRequest labelCreateRequest2 = new LabelCreateRequest("레이블2", "레이블 설명2", "#004DE4", true);
        LabelCreateRequest labelCreateRequest3 = new LabelCreateRequest("레이블3", "레이블 설명3", "#004DE5", true);
        LabelCreateRequest labelCreateRequest4 = new LabelCreateRequest("레이블4", "레이블 설명4", "#004DE6", true);
        labelService.create(ORGANIZATION_TITLE, labelCreateRequest1);
        labelService.create(ORGANIZATION_TITLE, labelCreateRequest2);
        labelService.create(ORGANIZATION_TITLE, labelCreateRequest3);
        labelService.create(ORGANIZATION_TITLE, labelCreateRequest4);

        // when
        mockMvc.perform(get("/api/" + ORGANIZATION_TITLE + "/common/navigation"))
                // then
                .andDo(print())
                .andExpect(jsonPath("labelsCount").value(4))
                .andExpect(jsonPath("milestonesCount").value(3));
    }
}
