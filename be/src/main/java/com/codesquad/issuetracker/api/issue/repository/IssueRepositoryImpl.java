package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.Issue;
import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueLabel;
import com.codesquad.issuetracker.api.issue.domain.IssueVo;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class IssueRepositoryImpl implements IssueRepository {

    private static final String ID = "id";
    private static final String MILESTONE_ID = "milestone_id";
    private static final String NUMBER = "number";
    private static final String TITLE = "title";
    private static final String IS_CLOSED = "is_closed";
    private static final String CREATED_TIME = "created_time";
    private static final String AUTHOR = "author";

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Long> countIssuesBy(Long organizationId) {
        String sql = "SELECT COUNT(id) FROM issue WHERE organization_id = :organizationId";
        return Optional.ofNullable(
            template.queryForObject(sql, Map.of("organizationId", organizationId), Long.class));
    }

    @Override
    public Optional<Long> save(Issue issue) {
        String queryForIssueSaved =
            "INSERT INTO issue (organization_id, milestone_id, member_id, title, number, is_closed, created_time) "
                + "VALUES (:organizationId, :milestoneId, :memberId, :title, :number, :isClosed, now())";

        SqlParameterSource params = new BeanPropertySqlParameterSource(issue);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(queryForIssueSaved, params, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

    @Override
    public void save(List<?> options) {
        if (options.get(0).getClass() == IssueAssignee.class) {
            saveIssueAssignees(options);
        }
        if (options.get(0).getClass() == IssueLabel.class) {
            saveIssueLabels(options);
        }
    }

    private void saveIssueAssignees(List<?> issueAssignees) {
        String sql = "INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueAssignees));
    }

    private void saveIssueLabels(List<?> issueLabels) {
        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueLabels));
    }

    @Override
    public IssueVo findBy(Long issueId) {
        String sql = "SELECT issue.id, milestone_id, number, title, is_closed, issue.created_time, member.nickname AS author "
                + "FROM issue "
                + "JOIN member ON issue.member_id = member.id "
                + "WHERE issue.id = :issueId";
        return template.queryForObject(sql, Collections.singletonMap("issueId", issueId), issueVoRowMapper());
    }

    @Override
    public boolean updateTitle(Issue issue) {
        String sql = "UPDATE issue SET title = :title WHERE id = :issueId";
        SqlParameterSource parmas = new MapSqlParameterSource().addValue("title", issue.getTitle())
            .addValue("issueId", issue.getId());
        return template.update(sql, parmas) == 1;
    }

    @Override
    @Transactional
    public boolean updateAssignees(List<IssueAssignee> assignees) {
        deleteAssignees(assignees.get(0)
            .getIssueId()); // TODO: 서비스에서 트랜잭션 걸어서 메서드를 각각 호출하는게 좋을지 여기서 처리하는게 맞는지 모르겠음

        String sql = "INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)";
        int[] result = template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(assignees));
        return result.length == assignees.size();
    }

    private void deleteAssignees(Long issueId) {
        String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    @Override
    @Transactional
    public boolean updateLabels(List<IssueLabel> labels) {
        deleteLabels(labels.get(0).getIssueId());

        String sql = "INSERT INTO issue_label (issue_id, label_id) VALUES (:issueId, :labelId)";
        int[] result = template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(labels));
        return result.length == labels.size();
    }

    private void deleteLabels(Long issueId) {
        String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    @Override
    public boolean updateMilestone(Issue issue) {
        String sql = "UPDATE issue SET milestone_id = :milestoneId WHERE id = :issueId";
        SqlParameterSource parmas = new MapSqlParameterSource().addValue("milestoneId",
                issue.getMilestoneId())
            .addValue("issueId", issue.getId());
        return template.update(sql, parmas) == 1;
    }

    @Override
    public void updateStatus(Issue issue) {
        String sql = "UPDATE issue SET is_closed = :isClosed WHERE id = :issueId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("isClosed", issue.getIsClosed())
                .addValue("issueId", issue.getId());
        template.update(sql, params);
    }

    @Override
    public void updateStatuses(List<Issue> issues) {
        String sql = "UPDATE issue SET is_closed = :isClosed WHERE id = :id";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issues));
    }

    @Override
    @Transactional
    public void delete(Long issueId) {
        String queryForDeleteIssue = "DELETE FROM issue WHERE id = :issueId";
        String queryForDeleteIssueAssignees = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        String queryForDeleteIssueLabels = "DELETE FROM issue_label WHERE issue_id = :issueId";

        Map<String, Long> param = Collections.singletonMap("issueId", issueId);

        template.update(queryForDeleteIssue, param);
        template.update(queryForDeleteIssueAssignees, param);
        template.update(queryForDeleteIssueLabels, param);
    }

    private RowMapper<IssueVo> issueVoRowMapper() {
        return (rs, rowNum) ->
                IssueVo.builder()
                        .id(rs.getLong(ID))
                        .milestone_id(rs.getLong(MILESTONE_ID))
                        .number(rs.getLong(NUMBER))
                        .title(rs.getString(TITLE))
                        .isClosed(rs.getBoolean(IS_CLOSED))
                        .createdTime(rs.getTimestamp(CREATED_TIME).toLocalDateTime())
                        .author(rs.getString(AUTHOR))
                        .build();
    }
}
