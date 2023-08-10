package com.codesquad.issuetracker.api.issue.controller;

import com.codesquad.issuetracker.api.issue.dto.IssueAssigneeUpdateRequest;
import com.codesquad.issuetracker.api.issue.dto.IssueLabelUpdateRequest;
import com.codesquad.issuetracker.api.issue.service.IssueInfoService;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Issue Label, Assignee Update을 담당하는 컨트롤러,
 * CRD는 각 도메인 컨트롤러에서 진행
 */
@Controller
@RequiredArgsConstructor
public class IssueInfoController {

    private final IssueInfoService issueInfoService;

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/assignees")
    public ResponseEntity<Map<String, Long>> updateAssignees(
            @RequestBody IssueAssigneeUpdateRequest issueAssigneeUpdateRequest,
            @PathVariable String organizationTitle,
            @PathVariable Long issueId) {
        issueInfoService.update(issueId, issueAssigneeUpdateRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", issueId));
    }

    @PutMapping("/api/{organizationTitle}/issues/{issueId}/labels")
    public ResponseEntity<Map<String, Long>> updateLabels(@RequestBody IssueLabelUpdateRequest issueLabelUpdateRequest,
                                                          @PathVariable String organizationTitle,
                                                          @PathVariable Long issueId) {
        issueInfoService.update(issueId, issueLabelUpdateRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", issueId));
    }

}
