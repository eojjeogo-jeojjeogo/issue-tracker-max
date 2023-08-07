package com.codesquad.issuetracker.api.issue.controller;

import com.codesquad.issuetracker.api.issue.dto.IssueAssigneeUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueCreateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueLabelUpdateRequest;
import com.codesquad.issuetracker.api.issue.service.IssueService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
                .body(Map.of("id", issueId));
    }

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/assignees")
    public ResponseEntity<Map<String, Long>> updateAssignees(
            @RequestBody IssueAssigneeUpdateRequest issueAssigneeUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueService.update(issueId, issueAssigneeUpdateRequest);
        return ResponseEntity.ok(Map.of("id", issueId));
    }

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/labels")
    public ResponseEntity<Map<String, Long>> updateLabels(
            @RequestBody IssueLabelUpdateRequest issueLabelUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueService.update(issueId, issueLabelUpdateRequest);
        return ResponseEntity.ok(Map.of("id", issueId));
    }

    @DeleteMapping("/api/{organizationTitle}/issues/{issueId}")
    public ResponseEntity<Void> deleteOne(@PathVariable String organizationTitle, @PathVariable Long issueId) {
        // TODO: 로그인 관련 처리 필요
        issueService.deleteOne(issueId);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
