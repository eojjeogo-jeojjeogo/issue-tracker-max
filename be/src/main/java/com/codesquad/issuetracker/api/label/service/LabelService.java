package com.codesquad.issuetracker.api.label.service;

import com.codesquad.issuetracker.api.label.domain.Label;
import com.codesquad.issuetracker.api.label.dto.request.LabelCreateRequest;
import com.codesquad.issuetracker.api.label.dto.request.LabelUpdateRequest;
import com.codesquad.issuetracker.api.label.dto.response.LabelResponse;
import com.codesquad.issuetracker.api.label.repository.LabelRepository;
import com.codesquad.issuetracker.api.organization.service.OrganizationService;
import com.codesquad.issuetracker.common.exception.CustomRuntimeException;
import com.codesquad.issuetracker.common.exception.customexception.LabelException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final OrganizationService organizationService;

    @Transactional
    public Long create(String organizationTitle, LabelCreateRequest labelCreateRequest) {
        Long organizationId = organizationService.getOrganizationIdBy(organizationTitle);
        Label label = labelCreateRequest.toEntity(organizationId);
        return labelRepository.save(label)
                .orElseThrow(() -> new CustomRuntimeException(LabelException.LABEL_SAVE_FAIL_EXCEPTION));
    }

    @Transactional
    public List<LabelResponse> readAll(String organizationTitle) {
        Long organizationId = organizationService.getOrganizationIdBy(organizationTitle);
        return labelRepository.findAllBy(organizationId).stream()
                .map(LabelResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public Long update(String organizationTitle, LabelUpdateRequest labelUpdateRequest,
                       Long labelId) {
        Long organizationId = organizationService.getOrganizationIdBy(organizationTitle);
        Label label = labelUpdateRequest.toEntity(organizationId, labelId);
        return labelRepository.update(label);
    }

    public void delete(Long labelId) {
        labelRepository.delete(labelId);
    }

}
