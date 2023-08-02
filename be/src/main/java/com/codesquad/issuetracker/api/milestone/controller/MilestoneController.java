package com.codesquad.issuetracker.api.milestone.controller;

import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneStatusRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.EditMileStoneResponse;
import com.codesquad.issuetracker.api.milestone.dto.response.MileStonesResponse;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            @RequestBody MilestoneRequest mileStoneRequest) {
        long milestoneId = milestoneService.create(organizationTitle, mileStoneRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", milestoneId));
    }

    @GetMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<EditMileStoneResponse> read(@PathVariable Long milestoneId) {
        EditMileStoneResponse mileStoneResponse = milestoneService.read(milestoneId);
        return ResponseEntity.ok(mileStoneResponse);
    }

    @GetMapping("/api/{organizationTitle}/milestones")
    public ResponseEntity<MileStonesResponse> readAll(@PathVariable String organizationTitle) {
        MileStonesResponse mileStonesResponse = milestoneService.readAll(organizationTitle);
        return ResponseEntity.ok(mileStonesResponse);
    }

    @PatchMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<Map<String, Long>> update(@PathVariable Long milestoneId,
            @RequestBody MilestoneRequest mileStoneRequest) {
        long id = milestoneService.update(milestoneId, mileStoneRequest);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @DeleteMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<Void> delete(@PathVariable Long milestoneId) {
        milestoneService.delete(milestoneId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/{organizationTitle}/milestones/{milestoneId}/status")
    public ResponseEntity<Map<String, Long>> updateStatus(@PathVariable Long milestoneId,
            @RequestBody MilestoneStatusRequest milestoneStatusRequest) {
        milestoneService.updateStatus(milestoneId, milestoneStatusRequest.isClosed());
        return ResponseEntity.ok(Map.of("id", milestoneId));
    }
}
