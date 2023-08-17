package com.codesquad.issuetracker.api.label.repository;

import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.label.domain.Label;
import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    List<Label> findAllBy(Long organizationId);

    List<LabelFilter> findFiltersBy(Long organizationId);

    Optional<Long> save(Label label);

    Long update(Label label);

    void delete(Long labelId);

    Long countBy(Long organizationId);
}
