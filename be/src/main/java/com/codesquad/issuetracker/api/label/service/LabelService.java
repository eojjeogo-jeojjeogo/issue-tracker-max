package com.codesquad.issuetracker.api.label.service;

import com.codesquad.issuetracker.api.label.domain.Label;
import com.codesquad.issuetracker.api.label.dto.LabelCreateRequest;
import com.codesquad.issuetracker.api.label.dto.LabelResponse;
import com.codesquad.issuetracker.api.label.dto.LabelUpdateRequest;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final OrganizationRepository organizationRepository;

    public List<LabelResponse> readAll(String organizationTitle) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        return labelRepository.findAllBy(organizationId).stream()
            .map(LabelResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long create(String organizationTitle, LabelCreateRequest labelCreateRequest) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        Label label = LabelCreateRequest.toEntity(organizationId, labelCreateRequest);
        return labelRepository.save(label).orElseThrow();
    }

    public Long update(String organizationTitle, LabelUpdateRequest labelUpdateRequest,
        Long labelId) {
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle).orElseThrow();
        Label label = LabelUpdateRequest.toEntity(labelUpdateRequest, organizationId, labelId);
        return labelRepository.update(label).orElseThrow();
    }

    public void delete(Long labelId) {
        labelRepository.delete(labelId);
    }
}
