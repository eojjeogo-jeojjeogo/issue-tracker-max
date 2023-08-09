package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.Comment;
import com.codesquad.issuetracker.api.comment.domain.IssueCommentVo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final static String ID = "id";
    private final static String CONTENT = "content";
    private final static String AUTHOR = "author";
    private final static String AUTHOR_IMG = "authorImg";
    private final static String CREATED_TIME = "created_time";
    private final static String FILES = "files";

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Long> save(Comment comment) {
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
        String sql = "DELETE FROM comment WHERE id = :commentId";
        template.update(sql, Collections.singletonMap("commentId", commentId));
    }

    @Override
    public List<IssueCommentVo> findAllBy(Long issueId, String issueAuthor) {
        String sql =
                "SELECT ic.id, ic.content, ic.created_time, ic.file_url AS files, m.nickname AS author, pi.url AS authorImg "
                        + "FROM issue_comment AS ic "
                        + "JOIN member AS m ON ic.member_id = m.id "
                        + "JOIN profile_img AS pi ON m.profile_img_id = pi.id "
                        + "WHERE ic.issue_id = :issueId "
                        + "ORDER BY ic.created_time ASC ";

        return template.query(sql, Collections.singletonMap("issueId", issueId), issueCommentRowMapper(issueAuthor));
    }

    private RowMapper<IssueCommentVo> issueCommentRowMapper(String author) {
        return (rs, rowNum) ->
                IssueCommentVo.builder()
                        .id(rs.getLong(ID))
                        .content(rs.getString(CONTENT))
                        .author(rs.getString(AUTHOR))
                        .authorImg(rs.getString(AUTHOR_IMG))
                        .isIssueAuthor(author.equals(rs.getString(AUTHOR)))
                        .createdTime(rs.getTimestamp(CREATED_TIME).toLocalDateTime())
                        .files(rs.getString(FILES))
                        .build();
    }
}
