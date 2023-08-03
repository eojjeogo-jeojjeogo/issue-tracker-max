package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Long> create(Comment comment) {
        String sql =
            "INSERT INTO issue_comment (content, file_url, issue_id, member_id, created_time) "
                + "VALUES (:content, :fileUrl, :issueId, :memberId, now())";

        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    @Override
    public Long update(Comment comment) {
        StringBuilder sql = new StringBuilder("UPDATE issue_comment SET ");

        if (comment.getContent() != null) {
            sql.append("content = :content,");
        }

        if (comment.getFileUrl() != null) {
            sql.append("file_url = :fileUrl,");
        }

        String finalSql = sql.toString().replaceAll(",$", "") + " WHERE id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(comment);
        template.update(finalSql, params);
        return comment.getId();
    }

    @Override
    public void delete(Long commentId) {
    }

    @Override
    public List<Comment> findAllByIssueId(Long issueId) {
        return null;
    }
}
