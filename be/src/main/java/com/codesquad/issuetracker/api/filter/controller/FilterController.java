package com.codesquad.issuetracker.api.filter.controller;

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

    @GetMapping(value = "/api/{organizationTitle}/milestones",params = "type=filter")
    public ResponseEntity<List<MilestoneFilter>> readAll(@PathVariable String organizationTitle) {
        List<MilestoneFilter> milestoneFiltersResponse = filterService.readMilestone(organizationTitle);
        return ResponseEntity.ok(milestoneFiltersResponse);
    }
}
