package com.codesquad.issuetracker.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;

public class MilestoneSteps {


    public static void 마일스톤_생성확인(ExtractableResponse<Response> response) {
        Assertions.assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(201),
                () -> assertThat(response.jsonPath().getLong("id")).isEqualTo(1L)
        );
    }

    public static ExtractableResponse<Response> 마일스톤을_생성한다() {
        String organizationTitle = "eojjeogojeojjeogo";
        Map<String, String> body = new HashMap<>();
        body.put("title", "마일 스톤 이름");
        body.put("description", "레이블 설명");
        body.put("dueDate", "2023.07.28");

        // when
        return RestAssured.given().log().all()
                .body(body).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/" + organizationTitle + "/milestones")
                .then().log().all()
                .extract();
    }
}
