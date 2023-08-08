package com.codesquad.issuetracker.api.member.repository;

import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<MemberFilter> findFiltersBy(Long organizationId) {
        String sql = "SELECT m.id, m.nickname, pi.url FROM member AS m "
                + "JOIN profile_img AS pi "
                + "ON m.profile_img_id = pi.id "
                + "JOIN organization_member AS om "
                + "ON om.member_id = m.id "
                + "WHERE om.organization_id = :organizationId";

        return template.query(sql, Collections.singletonMap("organizationId", organizationId), memberFilterRowMapper());
    }

    private RowMapper<MemberFilter> memberFilterRowMapper() {
        return (rs, rowNum) ->
                MemberFilter.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("nickname"))
                        .imgUrl(rs.getString("url"))
                        .build();
    }
}
