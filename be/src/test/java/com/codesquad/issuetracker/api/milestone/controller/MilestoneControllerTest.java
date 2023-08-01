package com.codesquad.issuetracker.api.milestone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

    public static final String ORGANIZATION_TITLE = "eojjeogojeojjeogo";
    public static final String TEST_TITLE = "마일 스톤 이름";
    public static final String TEST_DESCRIPTION = "레이블 설명";
    public static final String TEST_DUE_DATE = "2023.07.28";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MilestoneService milestoneService;
    MilestoneRequest createMileStoneRequest;

    @BeforeEach
    void setUp() {
        createMileStoneRequest = new MilestoneRequest(TEST_TITLE, TEST_DESCRIPTION, TEST_DUE_DATE);
    }

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤을 생성요청하면 201코드과 마일 스톤 아이디를 반환한다")
    @Test
    void createMilestone() throws Exception {
        // when,then
        mockMvc.perform(post("/api/" + ORGANIZATION_TITLE + "/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMileStoneRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
        ;
    }

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤 아이디로 마일스톤을 조회 하면 마일스톤 info를 받을 수 있다")
    @Test
    void redMilestone() throws Exception {
        // given
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, createMileStoneRequest);

        // when
        mockMvc.perform(get("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
        // then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("title").value(TEST_TITLE))
                .andExpect(jsonPath("description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("dueDate").value(TEST_DUE_DATE))
                .andExpect(jsonPath("issueClosedCount").value(0))
                .andExpect(jsonPath("issueOpenedCount").value(0))
        ;
    }

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤 수정 요청하면 200 코드를 응답한다")
    @Test
    void updateMilestone() throws Exception {
        // given
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, createMileStoneRequest);
        MilestoneRequest changedMilestoneRequest = new MilestoneRequest(TEST_TITLE, TEST_DESCRIPTION,
                TEST_DUE_DATE);

        // when
        mockMvc.perform(patch("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .content(objectMapper.writeValueAsString(changedMilestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
        // then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Sql(statements = "insert into organization (title) values ('eojjeogojeojjeogo')")
    @DisplayName("마일 스톤 삭제 요청하면 204 코드를 응답한다")
    @Test
    void deleteMilestone() throws Exception {
        // given
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, createMileStoneRequest);

        // when
        mockMvc.perform(delete("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
        // then
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
