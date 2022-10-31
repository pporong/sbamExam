package com.cwy.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyRepository {

	@Insert ( """
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = 'article',
				relId =  #{relId},
				`body` = #{body};
			</script>	
					""")
	public void writeReply(int actorId, String relTypeCode, int relId, String body);



}
