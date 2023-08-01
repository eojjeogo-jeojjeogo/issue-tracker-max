package com.codesquad.issuetracker.api.milestone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneCreateRequest;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MilestoneControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MilestoneService milestoneService;


    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤을 생성요청하면 201코드과 마일 스톤 아이디를 반환한다")
    @Test
    void createMilestone() throws Exception {
        // given
        String organizationTitle = "eojjeogojeojjeogo";
        String title = "마일 스톤 이름";
        String description = "레이블 설명";
        String dueDate = "2023.07.28";
        MilestoneCreateRequest mileStoneCreateRequest = new MilestoneCreateRequest(title, description, dueDate);

        // when,then
        mockMvc.perform(post("/api/" + organizationTitle + "/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mileStoneCreateRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
        ;
    }

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤 아이디로 마일스톤을 조회 하면 마일스톤 info를 받을 수 있다")
    @Test
    void Milestone() throws Exception {
        // given
        String organizationTitle = "eojjeogojeojjeogo";
        String title = "마일 스톤 이름";
        String description = "레이블 설명";
        String dueDate = "2023.07.28";
        MilestoneCreateRequest mileStoneCreateRequest = new MilestoneCreateRequest(title, description, dueDate);
        long milestoneId = milestoneService.create(organizationTitle, mileStoneCreateRequest);

        // when,then
        mockMvc.perform(get("/api/" + organizationTitle + "/milestones/" + milestoneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("title").value(title))
                .andExpect(jsonPath("description").value(description))
                .andExpect(jsonPath("dueDate").value(dueDate))
                .andExpect(jsonPath("issueClosedCount").value(0))
                .andExpect(jsonPath("issueOpenedCount").value(0))
        ;
    }
}
