package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmoticonRepositoryImpl implements EmoticonRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<Emoticon> findAll() {
        String sql = "SELECT id, unicode FROM emoticon";
        return template.query(sql, emoticonRowMapper());
    }

    private RowMapper<Emoticon> emoticonRowMapper() {
        return BeanPropertyRowMapper.newInstance(Emoticon.class);
    }
}
