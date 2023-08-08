package com.codesquad.issuetracker.api.issue.controller;

import com.codesquad.issuetracker.api.issue.dto.IssueAssigneeUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueLabelUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueMilestoneUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueResponse;
import com.codesquad.issuetracker.api.issue.dto.IssueStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueTitleUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssuesStatusUpdateRequest;
import com.codesquad.issuetracker.api.issue.service.IssueService;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/api/{organizationTitle}/issues")
    public ResponseEntity<Map<String, Long>> create(@RequestBody IssueCreateRequest issueCreateRequest,
                                                    @PathVariable String organizationTitle) {
        // TODO: 로그인 관련 처리 필요
        Long issueId = issueService.create(organizationTitle, issueCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("id", issueId));
    }

    @GetMapping("/api/{organizationTitle}/issues/{issueId}")
    public ResponseEntity<IssueResponse> read(@PathVariable String organizationTitle, @PathVariable Long issueId) {
        IssueResponse issueResponse = issueService.read(issueId);
        return ResponseEntity.ok(issueResponse);
    }

    @PatchMapping("/api/{organizationTitle}/issues/{issueId}/title")
    public ResponseEntity<Map<String, Long>> updateTitle(@RequestBody IssueTitleUpdateRequest issueTitleUpdateRequest,
                                                         @PathVariable String organizationTitle,
                                                         @PathVariable Long issueId) {
        issueService.update(issueId, issueTitleUpdateRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", issueId));
    }

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/assignees")
    public ResponseEntity<Map<String, Long>> updateAssignees(
            @RequestBody IssueAssigneeUpdateRequest issueAssigneeUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueService.update(issueId, issueAssigneeUpdateRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", issueId));
    }

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/labels")
    public ResponseEntity<Map<String, Long>> updateLabels(@RequestBody IssueLabelUpdateRequest issueLabelUpdateRequest,
                                                          @PathVariable String organizationTitle,
                                                          @PathVariable Long issueId) {
        issueService.update(issueId, issueLabelUpdateRequest);
        return ResponseEntity.ok(Map.of("id", issueId));
    }

    @PatchMapping("/api/{organizationTitle}/issues/{issueId}/milestones")
    public ResponseEntity<Map<String, Long>> updateMilestones(
            @RequestBody IssueMilestoneUpdateRequest issueMilestoneUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueService.update(issueId, issueMilestoneUpdateRequest);
        return ResponseEntity.ok(Map.of("id", issueId));
    }

    @PatchMapping("/api/{organizationTitle}/issues/{issueId}/status")
    public ResponseEntity<Map<String, Long>> updateStatus(
            @RequestBody IssueStatusUpdateRequest issueStatusUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueService.update(issueId, issueStatusUpdateRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", issueId));
    }

    @PatchMapping("/api/{organizationTitle}/issues")
    public ResponseEntity<Map<String, Long>> updateStatuses(
            @RequestBody IssuesStatusUpdateRequest issuesStatusUpdateRequest,
            @PathVariable String organizationTitle) {
        issueService.update(issuesStatusUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/{organizationTitle}/issues/{issueId}")
    public ResponseEntity<Void> delete(@PathVariable String organizationTitle, @PathVariable Long issueId) {
        // TODO: 로그인 관련 처리 필요
        issueService.deleteOne(issueId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
