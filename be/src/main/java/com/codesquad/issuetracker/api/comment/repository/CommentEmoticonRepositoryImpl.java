package com.codesquad.issuetracker.api.comment.repository;

import com.codesquad.issuetracker.api.comment.domain.CommentEmoticonVo;
import com.codesquad.issuetracker.api.comment.domain.Emoticon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentEmoticonRepositoryImpl implements CommentEmoticonRepository {

    private final static String ID = "id";
    private final static String EMOTICON_ID = "emoticonId";
    private final static String EMOTICON_UNICODE = "emoticon";
    private final static String MEMBER_NICKNAME = "memberNickname";
    private final static String MEMBER_ID = "memberId";
    private final static String COMMENT_ID = "commentId";

    private final NamedParameterJdbcTemplate template;

    @Override
    public List<CommentEmoticonVo> findAllBy(Long commentId) {
        String sql =
                "SELECT m.nickname AS memberNickname, e.unicode AS emoticon, e.id "
                        + "FROM comment_emoticon AS ce "
                        + "JOIN emoticon AS e ON ce.emoticon_id = e.id "
                        + "JOIN member AS m ON ce.member_id = m.id "
                        + "WHERE ce.comment_id = :commentId";

        return template.query(sql, Collections.singletonMap(COMMENT_ID, commentId), new CommentEmoticonExtractor());
    }

    @Override
    public void save(Long commentId, Long memberId, Emoticon emoticon) {
        String sql = "INSERT INTO comment_emoticon "
                + "VALUES (:commentId, :emoticonId, :memberId)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(COMMENT_ID, commentId)
                .addValue(EMOTICON_ID, emoticon.getId())
                .addValue(MEMBER_ID, memberId);

        template.update(sql, params);
    }

    /**
     * Comment에 달린 Emoticon의 정보를 가져올때 MemberNickname은 List 형태일수 있기떄문에 조회 결과 테이블을 한번에 다룰수 있는 ResultSetExtractor를 사용한다.
     */
    private class CommentEmoticonExtractor implements ResultSetExtractor<List<CommentEmoticonVo>> {

        @Override
        public List<CommentEmoticonVo> extractData(ResultSet rs) throws SQLException {
            Map<Long, CommentEmoticonVo> map = new LinkedHashMap<>();
            while (rs.next()) {
                Long id = rs.getLong(ID);
                map.putIfAbsent(id, new CommentEmoticonVo(id, rs.getString(EMOTICON_UNICODE), new ArrayList<>()));
                CommentEmoticonVo info = map.get(id);
                info.getMemberNickname().add(rs.getString(MEMBER_NICKNAME));
            }
            return new ArrayList<>(map.values());
        }
    }

}
