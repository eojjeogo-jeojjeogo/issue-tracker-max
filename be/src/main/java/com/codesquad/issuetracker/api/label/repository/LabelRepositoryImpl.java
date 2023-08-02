package com.codesquad.issuetracker.api.label.repository;

import com.codesquad.issuetracker.api.label.domain.Label;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepositoryImpl implements LabelRepository {

    private static final String FIND_COUNT_BY_ORGANIZATION_SQL = "SELECT COUNT(id)"
            + " FROM label"
            + " WHERE organization_id = :organization_id";
    private static final String ORGANIZATION_ID = "organization_id";
    private final NamedParameterJdbcTemplate template;

    public LabelRepositoryImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Label> findAll(Long organizationId) {
        String sql = "SELECT id, organization_id, title, description, background_color, is_dark "
                + "FROM label "
                + "WHERE organization_id = :organizationId";
        return template.query(sql, Map.of("organizationId", organizationId), labelRowMapper());
    }

    public Optional<Long> save(Label label) {
        String sql =
                "INSERT INTO label (organization_id, title, description, background_color, is_dark) "
                        + "VALUES (:organizationId, :title, :description, :backgroundColor, :isDark)";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("organizationId", label.getOrganizationId())
                .addValue("title", label.getTitle())
                .addValue("description", label.getDescription())
                .addValue("backgroundColor", label.getBackgroundColor())
                .addValue("isDark", label.getIsDark());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    public Optional<Long> update(Label label) {
        // TODO: 추후에 시간되면 마이바티스로 수정
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder("UPDATE label SET ");

        if (label.getTitle() != null) {
            sql.append("title = :title,");
            params.addValue("title", label.getTitle());
        }
        if (label.getDescription() != null) {
            sql.append("description = :description,");
            params.addValue("description", label.getDescription());
        }
        if (label.getBackgroundColor() != null) {
            sql.append("background_color = :backgroundColor,");
            params.addValue("backgroundColor", label.getBackgroundColor());
        }
        if (label.getIsDark() != null) {
            sql.append("is_dark = :isDark,");
            params.addValue("isDark", label.getIsDark());
        }

        String finalSql = sql.toString().replaceAll(",$", "") + " WHERE id = :id";
        params.addValue("id", label.getId());
        template.update(finalSql, params);
        return Optional.ofNullable(label.getId());
    }

    public void delete(Long labelId) {
        String sql = "DELETE FROM label WHERE id = :id";

        template.update(sql, Map.of("id", labelId));
    }

    @Override
    public Long findCountByOrganizationId(Long organizationId) {
        return template.queryForObject(
                FIND_COUNT_BY_ORGANIZATION_SQL,
                Map.of(ORGANIZATION_ID, organizationId),
                Long.class
        );
    }

    private RowMapper<Label> labelRowMapper() {
        return (rs, rowNum) -> Label.builder()
                .id(rs.getLong("id"))
                .organizationId(rs.getLong("organization_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .backgroundColor(rs.getString("background_color"))
                .isDark(rs.getBoolean("is_dark"))
                .build();
    }
}
