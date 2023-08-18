package com.codesquad.issuetracker.api.milestone.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesquad.issuetracker.api.jwt.domain.Jwt;
import com.codesquad.issuetracker.api.jwt.service.JwtProvider;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MilestoneControllerTest {

    public static final String ORGANIZATION_TITLE = "백엔드";
    public static final String TEST_TITLE = "마일스톤 이름";
    public static final String TEST_DESCRIPTION = "마일스톤 설명";
    public static final String TEST_DUE_DATE = "2023.07.28";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MilestoneService milestoneService;

    private Jwt jwt;
    private MilestoneRequest fullDataRequest;
    private MilestoneRequest partDataRequest;

    @BeforeEach
    void setUpRequest() {
        fullDataRequest = new MilestoneRequest(TEST_TITLE, TEST_DESCRIPTION, TEST_DUE_DATE);
        partDataRequest = new MilestoneRequest(TEST_TITLE, null, TEST_DUE_DATE);
        jwt = jwtProvider.createTokens(Map.of("memberId", 1));
    }

    @DisplayName("모든 데이터가 있는 마일스톤 생성 요청 시 201 코드과 마일스톤 아이디를 반환한다.")
    @Test
    void createFullDataMilestone() throws Exception {
        mockMvc.perform(post("/api/" + ORGANIZATION_TITLE + "/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fullDataRequest))
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }

    @DisplayName("부분 데이터가 있는 마일스톤 생성 요청 시 201 코드과 마일스톤 아이디를 반환한다.")
    @Test
    void createPartDataMilestone() throws Exception {
        mockMvc.perform(post("/api/" + ORGANIZATION_TITLE + "/milestones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partDataRequest))
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists());
    }

    @DisplayName("마일스톤 전체를 조회 하면 모든 마일스톤 info를 받을 수 있다")
    @Test
    void readAllMilestone() throws Exception {
        mockMvc.perform(get("/api/" + ORGANIZATION_TITLE + "/milestones?filter=open")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("milestoneOpenedCount").exists())
                .andExpect(jsonPath("milestoneClosedCount").exists())
                .andExpect(jsonPath("milestones").isArray())
                .andExpect(jsonPath("milestones[*].id").exists())
                .andExpect(jsonPath("milestones[*].title").exists())
                .andExpect(jsonPath("milestones[*].description").exists())
                .andExpect(jsonPath("milestones[*].dueDate").exists())
                .andExpect(jsonPath("milestones[*].issueClosedCount").exists())
                .andExpect(jsonPath("milestones[*].issueOpenedCount").exists());
    }

    @DisplayName("마일 스톤 아이디로 마일스톤을 조회 하면 마일스톤 info를 받을 수 있다")
    @Test
    void readMilestone() throws Exception {
        // given
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, fullDataRequest);

        // when
        mockMvc.perform(get("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("title").value(TEST_TITLE))
                .andExpect(jsonPath("issueClosedCount").exists())
                .andExpect(jsonPath("issueOpenedCount").exists());
    }

    @DisplayName("마일 스톤 수정 요청하면 200 코드를 응답한다")
    @Test
    void updateMilestone() throws Exception {
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, fullDataRequest);
        String newTitle = "수정 타이틀";
        String newDescription = "수정 설명";

        // TODO 부분 수정만 되는지 확인 필요
        MilestoneRequest updateMilestoneRequest = new MilestoneRequest(newTitle, newDescription, TEST_DUE_DATE);

        mockMvc.perform(patch("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .content(objectMapper.writeValueAsString(updateMilestoneRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                // then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("마일 스톤 삭제 요청하면 204 코드를 응답한다")
    @Test
    void deleteMilestone() throws Exception {
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, fullDataRequest);

        // when
        mockMvc.perform(delete("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("마일 스톤 상태 변경 요청하면 200 코드를 응답한다")
    @Test
    void updateMilestoneStatus() throws Exception {
        long milestoneId = milestoneService.create(ORGANIZATION_TITLE, fullDataRequest);
        Map<String, Boolean> request = Map.of("isClosed", true);

        mockMvc.perform(patch("/api/" + ORGANIZATION_TITLE + "/milestones/" + milestoneId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer" + " " + jwt.getAccessToken())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
