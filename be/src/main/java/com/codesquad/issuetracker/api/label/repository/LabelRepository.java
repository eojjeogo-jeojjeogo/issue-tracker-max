package com.codesquad.issuetracker.api.label.repository;

import com.codesquad.issuetracker.api.label.domain.Label;
import java.util.List;

public interface LabelRepository {

    List<Label> findAll(Long organizationId);
    Long save(Label label);

    Long update(Label label);

    void delete(Long labelId);

}
