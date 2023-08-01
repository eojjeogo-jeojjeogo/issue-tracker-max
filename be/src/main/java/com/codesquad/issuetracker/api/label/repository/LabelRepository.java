package com.codesquad.issuetracker.api.label.repository;

import com.codesquad.issuetracker.api.label.domain.Label;
import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    List<Label> findAll(Long organizationId);

    Optional<Long> save(Label label);

    Optional<Long> update(Label label);

    void delete(Long labelId);

}
