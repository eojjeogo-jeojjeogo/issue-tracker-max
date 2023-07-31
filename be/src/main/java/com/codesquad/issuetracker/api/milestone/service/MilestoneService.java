package com.codesquad.issuetracker.api.milestone.service;

import static com.codesquad.issuetracker.common.unit.ConverterFormatter.DATE_TIME_PATTERN;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.codesquad.issuetracker.api.milestone.dto.MilestoneCreatRequest;
import com.codesquad.issuetracker.api.milestone.repository.MilestoneRepository;
import com.codesquad.issuetracker.api.organization.repository.OrganizationRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final OrganizationRepository organizationRepository;

    public long create(String organizationTitle, MilestoneCreatRequest mileStoneCreatRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        LocalDate localDate = LocalDate.parse(mileStoneCreatRequest.getDueDate(), formatter);
        Long organizationId = organizationRepository.findIdByTitle(organizationTitle);
        if (organizationId == null) {
            throw new RuntimeException("organization이 존재하지 않습니다.");
        }
        Milestone milestone = Milestone.builder()
                .organizationId(organizationId)
                .title(mileStoneCreatRequest.getTitle())
                .description(mileStoneCreatRequest.getDescription())
                .isClosed(false)
                .dueDate(localDate)
                .build();
        Long save = milestoneRepository.save(milestone);
        if (save == null) {
            throw new RuntimeException("milestone을 저장하는데 실패했습니다.");
        }
        return save;
    }
}
