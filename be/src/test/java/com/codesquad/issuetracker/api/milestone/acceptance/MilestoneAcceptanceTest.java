package com.codesquad.issuetracker.api.milestone.acceptance;

import static com.codesquad.issuetracker.steps.MilestoneSteps.마일스톤_생성확인;
import static com.codesquad.issuetracker.steps.MilestoneSteps.마일스톤을_생성한다;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class MilestoneAcceptanceTest {


    @DisplayName("마일 스톤을 생성 요청하면 201코드과 마일 스톤 아이디를 반환한다")
    @Test
    void createMilestone() {
        // given
        var response = 마일스톤을_생성한다();

        // then
        마일스톤_생성확인(response);
    }

}
