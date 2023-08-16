package com.codesquad.issuetracker.api.filter.controller;

import static com.codesquad.issuetracker.common.util.RequestUtil.extractMemberId;

import com.codesquad.issuetracker.api.filter.dto.DynamicFiltersResponse;
import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.filter.dto.MilestoneFilter;
import com.codesquad.issuetracker.api.filter.dto.StaticFiltersResponse;
import com.codesquad.issuetracker.api.filter.service.FilterService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FilterController {

    public static final String MEMBER_ID = "memberId";
    private final FilterService filterService;

    @GetMapping(value = "/api/{organizationTitle}/milestones", params = "type=filter")
    public ResponseEntity<List<MilestoneFilter>> readMilestones(@PathVariable String organizationTitle) {
        List<MilestoneFilter> milestoneFiltersResponse = filterService.readMilestones(organizationTitle);
        return ResponseEntity.ok(milestoneFiltersResponse);
    }

    @GetMapping(value = "/api/{organizationTitle}/labels", params = "type=filter")
    public ResponseEntity<List<LabelFilter>> readLabels(@PathVariable String organizationTitle) {
        List<LabelFilter> labelFiltersResponse = filterService.readLabels(organizationTitle);
        return ResponseEntity.ok(labelFiltersResponse);
    }

    @GetMapping(value = "/api/{organizationTitle}/assignees", params = "type=filter")
    public ResponseEntity<List<MemberFilter>> readAssignees(@PathVariable String organizationTitle) {
        List<MemberFilter> memberFiltersResponse = filterService.readAssignees(organizationTitle);
        return ResponseEntity.ok(memberFiltersResponse);
    }

    @GetMapping(value = "/api/{organizationTitle}/issues/filters", params = "type=dynamic")
    public ResponseEntity<DynamicFiltersResponse> readDynamicFilters(@PathVariable String organizationTitle) {
        DynamicFiltersResponse response = filterService.readDynamicFilters(organizationTitle);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/{organizationTitle}/issues/filters", params = "type=static")
    public ResponseEntity<StaticFiltersResponse> readStaticFilters(@PathVariable String organizationTitle,
                                                                   HttpServletRequest httpServletRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        return ResponseEntity.ok(new StaticFiltersResponse(memberId));
    }
}
