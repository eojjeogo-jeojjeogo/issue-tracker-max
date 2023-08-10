package com.codesquad.issuetracker.api.member.repository;

import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.member.domain.Member;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private static final String ID = "id";
    private static final String NICKNAME = "nickname";
    private static final String URL = "url";

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Long> save(Member member, String providerName) {
        String sql = "INSERT INTO member (email, password, nickname, profile_img_url, created_time, sign_in_type_id) "
                + "VALUES (:email,:password, :nickname, :profileImgUrl, now(), (SELECT id FROM sign_in_type WHERE provider = :provider))";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", member.getEmail())
                .addValue("nickname", member.getNickname())
                .addValue("password", member.getPassword())
                .addValue("profileImgUrl", member.getProfileImgUrl())
                .addValue("provider", providerName);

        //todo profileImgUrl 에 기본값
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder);
        return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue);
    }

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

    @Override
    public Optional<Long> findBy(String email) {
        String sql = "SELECT id FROM member WHERE email = :email";

        List<Long> results = template.query(
                sql,
                Collections.singletonMap("email", email),
                (rs, rowNum) -> rs.getLong("id")
        );

        return results.stream().findFirst();
    }

    @Override
    public Optional<Member> findMemberBy(String email) {
        String sql = "SELECT id, email, password FROM member WHERE email = :email";

        List<Member> member = template.query(
                sql,
                Collections.singletonMap("email", email),
                memberRowMapper()
        );
        return member.stream().findFirst();
    }

    @Override
    public boolean existsNickname(String nickname) {
        String sql = "SELECT COUNT(nickname) FROM member WHERE nickname = :nickname";

        int count = template.queryForObject(sql, Collections.singletonMap("nickname", nickname), Integer.class);
        return count > 0;
    }

    private RowMapper<MemberFilter> memberFilterRowMapper() {
        return (rs, rowNum) ->
                MemberFilter.builder()
                        .id(rs.getLong(ID))
                        .name(rs.getString(NICKNAME))
                        .imgUrl(rs.getString(URL))
                        .build();
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) ->
                Member.builder()
                        .id(rs.getLong("id"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .build();
    }
}
