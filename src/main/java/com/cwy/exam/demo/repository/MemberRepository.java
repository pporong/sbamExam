package com.cwy.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cwy.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {

	@Insert("""
				INSERT INTO `member`
				SET regDate = NOW(),
				updateDate = NOW(),
				loginId = #{loginId},
				loginPw = #{loginPw},
				`name` = #{name},
				nickname = #{nickname},
				cellphoneNum = #{cellphoneNum},
				email = #{email}
								""")
	void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	@Select("""
				SELECT *
				FROM `member` AS M
				WHERE M.id = #{id}
				AND delStatus = 0
								""")
	Member getMemberById(int id);

	@Select("""
				SELECT *
				FROM `member` AS M
				WHERE M.loginId = #{loginId}
				AND delStatus = 0
							""")
	Member getMemberByLoginId(String loginId);

	@Select("""
				SELECT *
				FROM `member` AS M
				WHERE M.name = #{name}
				AND M.email = #{email}
									""")
	Member getMemberByNameAndEmail(String name, String email);

	@Select("""
				SELECT M.name AS extra__writerName
				FROM `member` AS M
				WHERE M.id = #{id}
								""")
	Member getLoginedMemberName(int id);

	@Select("""
				SELECT M.*
				FROM `member` AS M
				WHERE M.id = #{id}
								""")
	public Member getForPrintMember(int id);
	
	@Select("""
			SELECT M.*, M.name AS extra__WriterName
			FROM `member` AS M
			LEFT JOIN article AS A
			ON M.id = A.memberId = M.id
							""")
	public List<Member> getForPrintMembers();
	
	@Update("""
			<script>
			UPDATE `member`
			<set>
				updateDate = NOW(),
				<if test="loginPw != null">
					loginPw = #{loginPw},
				</if>
				<if test="nickname != null">
					nickname = #{nickname},
				</if>
				<if test="cellphoneNum != null">
					cellphoneNum = #{cellphoneNum},
				</if>
				<if test="email != null">
					email = #{email}
				</if>
			</set>
			WHERE id = #{id};
			</script>
				""")
	void modifyMyInfo(int id, String loginPw, String nickname, String cellphoneNum, String email);

	@Select("""
			<script>
			SELECT A.*, M.name AS extra__writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id
			WHERE 1 AND A.memberId = #{M.loginId}
			</script>
								""")
	Member getArticleByMemberId();

}
