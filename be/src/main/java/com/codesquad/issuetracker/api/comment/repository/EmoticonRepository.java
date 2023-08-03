package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.util.List;

public interface EmoticonRepository {

    List<Emoticon> findAll();
}
