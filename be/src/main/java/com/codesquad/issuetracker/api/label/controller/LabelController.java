package com.codesquad.issuetracker.api.label.controller;

import com.codesquad.issuetracker.api.label.dto.LabelCreateRequest;
import com.codesquad.issuetracker.api.label.dto.LabelResponse;
import com.codesquad.issuetracker.api.label.dto.LabelUpdateRequest;
import com.codesquad.issuetracker.api.label.service.LabelService;
import java.util.List;
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
public class LabelController {

    private final LabelService labelService;

    @GetMapping("/api/{organizationTitle}/labels")
    public ResponseEntity<List<LabelResponse>> readLabels(@PathVariable String organizationTitle) {
        List<LabelResponse> labels = labelService.readAll(organizationTitle);
        return ResponseEntity.ok()
                .body(labels);
    }

    @PostMapping("/api/{organizationTitle}/labels")
    public ResponseEntity<Map<String, Long>> create(@RequestBody LabelCreateRequest labelCreateRequest,
                                                    @PathVariable String organizationTitle) {
        // TODO: 로그인 관련 처리 필요
        Long labelId = labelService.create(organizationTitle, labelCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("id", labelId));
    }

    @PatchMapping("/api/{organizationTitle}/labels/{labelId}")
    public ResponseEntity<Map<String, Long>> update(@RequestBody LabelUpdateRequest labelUpdateRequest,
                                                    @PathVariable Long labelId,
                                                    @PathVariable String organizationTitle) {
        // TODO: 로그인 관련 처리 필요
        Long updatedLabelId = labelService.update(organizationTitle, labelUpdateRequest, labelId);
        return ResponseEntity.ok()
            .body(Map.of("id", updatedLabelId));
    }

    @DeleteMapping("/api/{organizationTitle}/labels/{labelId}")
    public ResponseEntity<Void> delete(@PathVariable Long labelId) {
        // TODO: 로그인 관련 처리 필요
        labelService.delete(labelId);
        return ResponseEntity.noContent().build();
    }
}
