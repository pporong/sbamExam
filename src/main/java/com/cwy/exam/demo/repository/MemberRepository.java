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

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM `member` AS M
			WHERE 1
			AND delStatus = 0
			<if test="authLevel != 0">
				AND M.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							M.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	int getMembersCount(int authLevel, String searchKeywordTypeCode, String searchKeyword);

	@Select("""
			<script>
				SELECT M.*
				FROM `member` AS M
				WHERE 1
				AND delStatus = 0
				<if test="authLevel != 0">
					AND M.authLevel = #{authLevel}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'loginId'">
							AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'name'">
							AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'nickname'">
							AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<otherwise>
							AND (
								M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
								OR
								M.name LIKE CONCAT('%', #{searchKeyword}, '%')
								OR
								M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							)
						</otherwise>
					</choose>
				</if>
				ORDER BY M.id
				<if test="limitTake != -1">
					LIMIT #{limitStart}, #{limitTake}
				</if>
			</script>
			""")
	List<Member> getForPrintMembers(int authLevel, String searchKeyword, String searchKeywordTypeCode, int limitStart,
			int limitTake);

}
