package com.codesquad.issuetracker.api.member.repository;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final NamedParameterJdbcTemplate template;

    @Override
    public void save(Long memberId, String refreshToken) {
        String sql = "INSERT INTO token "
                + "VALUES (:memberId, :refreshToken)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("refreshToken", refreshToken);

        template.update(sql, params);
    }

    @Override
    public void deleteRefreshToken(Long memberId) {
        String sql = "DELETE FROM token WHERE member_id = :memberId";

        template.update(sql, Collections.singletonMap("memberId", memberId));
    }
}
