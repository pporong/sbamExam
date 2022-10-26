package com.cwy.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

	
	@Update ("""
			
			
			
			""")
	int increaseGoodRp(int id);

}
