package com.codesquad.issuetracker.api.jwt.repository;

import java.util.Collections;
import java.util.Optional;
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

    @Override
    public Optional<Long> findMemberIdBy(String refreshToken) {
        String sql = "SELECT member_id FROM token WHERE refresh_token = :refreshToken";

        return template.query(sql, Collections.singletonMap("refreshToken", refreshToken),
                        (rs, rowNum) -> rs.getLong("member_id"))
                .stream()
                .findFirst();
    }
}
