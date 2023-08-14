package com.codesquad.issuetracker.api.issue.repository;

import com.codesquad.issuetracker.api.issue.domain.IssueAssignee;
import com.codesquad.issuetracker.api.issue.domain.IssueAssigneeVo;
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
public class IssueAssigneeRepositoryImpl implements IssueAssigneeRepository {

    public static final String ID = "id";
    public static final String NICKNAME = "nickName";
    public static final String IMG_URL = "img_url";

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<IssueAssigneeVo> findAllBy(Long issueId) {
        String sql = "SELECT member.id, member.nickname, member.profile_img_url AS img_url "
                + "FROM issue_assignee "
                + "JOIN member ON issue_assignee.member_id = member.id "
                + "WHERE issue_assignee.issue_id = :issueId";
        return template.query(sql, Collections.singletonMap("issueId", issueId), issueAssigneeVoRowMapper());
    }

    @Override
    public void save(List<IssueAssignee> issueAssignees) {
        String sql = "INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)";
        template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(issueAssignees));
    }

    @Override
    public boolean update(List<IssueAssignee> assignees) {
        String sql = "INSERT INTO issue_assignee (issue_id, member_id) VALUES (:issueId, :memberId)";
        int[] result = template.batchUpdate(sql, SqlParameterSourceUtils.createBatch(assignees));
        return result.length == assignees.size();
    }

    @Override
    public void delete(Long issueId) {
        String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        template.update(sql, Map.of("issueId", issueId));
    }

    private RowMapper<IssueAssigneeVo> issueAssigneeVoRowMapper() {
        return (rs, rowNum) -> new IssueAssigneeVo(
                rs.getLong(ID),
                rs.getString(NICKNAME),
                rs.getString(IMG_URL)
        );
    }
}
