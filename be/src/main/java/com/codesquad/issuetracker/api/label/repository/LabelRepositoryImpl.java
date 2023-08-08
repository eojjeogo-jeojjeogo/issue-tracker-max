package com.codesquad.issuetracker.api.label.repository;

import com.codesquad.issuetracker.api.filter.dto.LabelFilter;
import com.codesquad.issuetracker.api.label.domain.Label;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LabelRepositoryImpl implements LabelRepository {

    private static final String ORGANIZATION_ID = "organization_id";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BACKGROUND_COLOR = "background_color";
    private static final String IS_DARK = "is_dark";
    private static final String DESCRIPTION = "description";

    private final NamedParameterJdbcTemplate template;

    public List<Label> findAllBy(Long organizationId) {
        String sql = "SELECT id, organization_id, title, description, background_color, is_dark "
                + "FROM label "
                + "WHERE organization_id = :organizationId";
        return template.query(sql, Collections.singletonMap("organizationId", organizationId), labelRowMapper());
    }

    public Optional<Long> save(Label label) {
        String sql = "INSERT INTO label (organization_id, title, description, background_color, is_dark) "
                + "VALUES (:organizationId, :title, :description, :backgroundColor, :isDark)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(label);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    public Long update(Label label) {
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
        return label.getId();
    }

    public void delete(Long labelId) {
        String sql = "DELETE FROM label WHERE id = :id";
        template.update(sql, Collections.singletonMap("id", labelId));
    }

    @Override
    public List<LabelFilter> findFiltersBy(Long organizationId) {
        String sql = "SELECT id,title,background_color, is_dark "
                + "FROM label "
                + "WHERE organization_id = :organization_id ";
        return template.query(sql, Collections.singletonMap(ORGANIZATION_ID, organizationId), labelFilterRowMapper());
    }

    @Override
    public long countBy(Long organizationId) {
        String sql = "SELECT COUNT(id)"
                + " FROM label"
                + " WHERE organization_id = :organization_id";
        return Objects.requireNonNull(
                template.queryForObject(sql, Collections.singletonMap(ORGANIZATION_ID, organizationId), Long.class)
        );
    }

    private RowMapper<Label> labelRowMapper() {
        return (rs, rowNum) -> Label.builder()
                .id(rs.getLong(ID))
                .organizationId(rs.getLong(ORGANIZATION_ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .backgroundColor(rs.getString(BACKGROUND_COLOR))
                .isDark(rs.getBoolean(IS_DARK))
                .build();
    }

    private RowMapper<LabelFilter> labelFilterRowMapper() {
        return (rs, rowNum) -> LabelFilter.builder()
                .id(rs.getLong(ID))
                .name(rs.getString(TITLE))
                .backgroundColor(rs.getString(BACKGROUND_COLOR))
                .isDark(rs.getBoolean(IS_DARK))
                .build();
    }
}
