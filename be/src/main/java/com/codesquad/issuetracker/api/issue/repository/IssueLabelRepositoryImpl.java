package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueLabelVo;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IssueLabelRepositoryImpl implements IssueLabelRepository {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String BACKGROUND_COLOR = "background_color";
    public static final String IS_DARK = "is_dark";

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<IssueLabelVo> findAllBy(Long issueId) {
        String sql = "SELECT label.id, label.title, label.description, label.background_color, label.is_dark "
                + "FROM issue_label "
                + "JOIN label ON issue_label.label_id = label.id "
                + "WHERE issue_label.issue_id = :issueId";
        return template.query(sql, Collections.singletonMap("issueId", issueId), issueLabelVoRowMapper());
    }

    public void save(List<IssueLabel> issueLabels) {
        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueLabels));
    }

    @Override
    public boolean update(List<IssueLabel> labels) {
        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)";
        int[] result = template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(labels));
        return result.length == labels.size();
    }

    @Override
    public void delete(Long issueId) {
        String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    private RowMapper<IssueLabelVo> issueLabelVoRowMapper() {
        return (rs, rowNum) -> IssueLabelVo.builder()
                .id(rs.getLong(ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .backgroundColor(rs.getString(BACKGROUND_COLOR))
                .isDark(rs.getBoolean(IS_DARK))
                .build();
    }
}
