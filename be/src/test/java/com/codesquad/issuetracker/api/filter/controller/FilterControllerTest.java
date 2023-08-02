package com.codesquad.issuetracker.api.filter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class FilterControllerTest {

    private static final String ORGANIZATION_TITLE = "eojjeogojeojjeogo";
    @Autowired
    MilestoneService milestoneService;

    @Autowired
    MockMvc mockMvc;

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일스톤 필드 목록 조회 ")
    @Test
    void readAllMilestoneFilter() throws Exception {
        // given
        MilestoneRequest milestoneRequest1 = new MilestoneRequest("마일스톤1", "설명1", "2023.08.01");
        MilestoneRequest milestoneRequest2 = new MilestoneRequest("마일스톤2", "설명2", "2023.08.11");
        milestoneService.create(ORGANIZATION_TITLE, milestoneRequest1);
        milestoneService.create(ORGANIZATION_TITLE, milestoneRequest2);

        // when
        mockMvc.perform(get("/api/" + ORGANIZATION_TITLE + "/milestones")
                        .param("type", "filter"))
                // then
                .andDo(print())
                .andExpect(jsonPath("*.id").exists())
                .andExpect(jsonPath("*.name").exists());
    }
}
