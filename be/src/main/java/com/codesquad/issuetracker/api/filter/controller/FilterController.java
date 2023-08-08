package com.codesquad.issuetracker.api.filter.controller;

import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.filter.service.FilterService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @GetMapping(value = "/api/{organizationTitle}/milestones", params = "type=filter")
    public ResponseEntity<List<MilestoneFilter>> readAllMilestone(@PathVariable String organizationTitle) {
        List<MilestoneFilter> milestoneFiltersResponse = filterService.readMilestone(organizationTitle);
        return ResponseEntity.ok(milestoneFiltersResponse);
    }

    @GetMapping(value = "/api/{organizationTitle}/labels", params = "type=filter")
    public ResponseEntity<List<LabelFilter>> readAllLabel(@PathVariable String organizationTitle) {
        List<LabelFilter> labelFiltersResponse = filterService.readAllLabel(organizationTitle);
        return ResponseEntity.ok(labelFiltersResponse);
    }

    @GetMapping(value = "/api/{organizationTitle}/assignees", params = "type=filter")
    public ResponseEntity<List<MemberFilter>> readAllAssignee(@PathVariable String organizationTitle) {
        List<MemberFilter> memberFiltersResponse = filterService.readAllMember(organizationTitle);
        return ResponseEntity.ok(memberFiltersResponse);
    }
}
