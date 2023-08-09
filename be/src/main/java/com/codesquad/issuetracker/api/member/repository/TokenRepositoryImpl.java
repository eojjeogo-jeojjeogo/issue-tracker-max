package com.codesquad.issuetracker.api.member.repository;

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
}
