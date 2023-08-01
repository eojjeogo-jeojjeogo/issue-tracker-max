package com.codesquad.issuetracker.api.milestone.controller;

import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneCreateRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MileStoneResponse;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MilestoneController {

    private final MilestoneService milestoneService;

    @PostMapping("/api/{organizationTitle}/milestones")
    public ResponseEntity<Map<String, Long>> create(@PathVariable String organizationTitle,
            @RequestBody MilestoneCreateRequest mileStoneCreateRequest) {
        long milestoneId = milestoneService.create(organizationTitle, mileStoneCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", milestoneId));
    }

    @GetMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<MileStoneResponse> read(@PathVariable Long milestoneId) {
        MileStoneResponse mileStoneResponse = milestoneService.read(milestoneId);
        return ResponseEntity.ok(mileStoneResponse);
    }
}
