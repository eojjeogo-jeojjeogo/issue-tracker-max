package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmoticonRepositoryImpl implements EmoticonRepository {

    private static final String ID = "id";
    private static final String UNICODE = "unicode";

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<Emoticon> findAll() {
        String sql = "SELECT id, unicode FROM emoticon";
        return template.query(sql, emoticonRowMapper());
    }

    private RowMapper<Emoticon> emoticonRowMapper() {
        return (rs, rowNum) ->
                Emoticon.builder()
                        .id(rs.getLong(ID))
                        .unicode(rs.getString(UNICODE))
                        .build();

    }
}
