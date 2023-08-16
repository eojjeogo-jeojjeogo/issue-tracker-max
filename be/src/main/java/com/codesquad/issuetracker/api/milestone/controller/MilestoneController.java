package com.codesquad.issuetracker.api.milestone.controller;

import com.codesquad.issuetracker.api.milestone.domain.MilestoneVo;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneRequest;
import com.codesquad.issuetracker.api.milestone.dto.request.MilestoneStatusRequest;
import com.codesquad.issuetracker.api.milestone.dto.response.MilestonesResponse;
import com.codesquad.issuetracker.api.milestone.filterStatus.FilterStatus;
import com.codesquad.issuetracker.api.milestone.service.MilestoneService;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MilestoneController {

    public static final String ID = "id";
    private final MilestoneService milestoneService;

    @PostMapping("/api/{organizationTitle}/milestones")
    public ResponseEntity<Map<String, Long>> create(@PathVariable String organizationTitle,
                                                    @Valid @RequestBody MilestoneRequest mileStoneRequest) {
        Long milestoneId = milestoneService.create(organizationTitle, mileStoneRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(ID, milestoneId));
    }

    @GetMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<MilestoneVo> read(@PathVariable Long milestoneId) {
        MilestoneVo mileStoneResponse = milestoneService.read(milestoneId);
        return ResponseEntity.ok(mileStoneResponse);
    }

    @GetMapping("/api/{organizationTitle}/milestones")
    public ResponseEntity<MilestonesResponse> readAll(@PathVariable String organizationTitle,
                                                      @RequestParam String filter) {
        FilterStatus filterStatus = FilterStatus.from(filter);
        MilestonesResponse mileStonesResponse = milestoneService.readAll(organizationTitle, filterStatus);
        return ResponseEntity.ok(mileStonesResponse);
    }

    @PatchMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<Map<String, Long>> update(@PathVariable Long milestoneId,
                                                    @Valid @RequestBody MilestoneRequest mileStoneRequest) {
        Long id = milestoneService.update(milestoneId, mileStoneRequest);
        return ResponseEntity.ok(Collections.singletonMap(ID, id));
    }

    @PatchMapping("/api/{organizationTitle}/milestones/{milestoneId}/status")
    public ResponseEntity<Map<String, Long>> updateStatus(@PathVariable Long milestoneId,
                                                          @RequestBody MilestoneStatusRequest milestoneStatusRequest) {
        milestoneService.updateStatus(milestoneId, milestoneStatusRequest.getIsClosed());
        return ResponseEntity.ok(Collections.singletonMap(ID, milestoneId));
    }

    @DeleteMapping("/api/{organizationTitle}/milestones/{milestoneId}")
    public ResponseEntity<Void> delete(@PathVariable Long milestoneId) {
        milestoneService.delete(milestoneId);
        return ResponseEntity.noContent()
                .build();
    }
}
