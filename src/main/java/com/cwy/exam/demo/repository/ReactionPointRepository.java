package com.cwy.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
			""")
	int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId);
	// memberId 의 추천 수 sum

	
	@Insert ("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId= #{relId},
				memberId = #{actorId},
				`point` = 1
			</script>
					""")
	void addGoodReactionPoint(int actorId, String relTypeCode, int relId);
	// goodPoint +1
	
	
	@Insert ("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId= #{relId},
				memberId = #{actorId},
				`point` = - 1
			</script>
					""")
	void addBadReactionPoint(int actorId, String relTypeCode, int relId);
	// badPoint +1
	
	
	@Delete ("""
			<script>
				DELETE FROM reactionPoint
				WHERE memberId = #{actorId}
				AND relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				`point` = - 1
			</script>
					""")
	void delBadReactionPoint(int actorId, String relTypeCode, int relId);
	
	
	@Delete ("""
			<script>
				DELETE FROM reactionPoint
				WHERE memberId = #{actorId}
				AND relTypeCode = #{relTypeCode}
				AND relId = #{relId}
				`point` = + 1
			</script>
					""")
	void delGoodReactionPoint(int actorId, String relTypeCode, int relId);
	
	@Select ("""
			<script>
				SELECT RP.relTypeCode, RP.relId,
				SUM(IF(RP.point &gt; 0, RP.point, 0)) AS goodReactionPoint,
				SUM(IF(RP.point &lt; 0, RP.point * -1, 0)) AS badReactionPoint
				FROM reactionPoint AS RP
				WHERE RP.memberId = #{actorId}
				GROUP BY RP.relTypeCode, RP.relId
			</script>
					""")
	boolean getRpInfoByMemberId(int actorId, String relTypeCode, int relId);



}
