package com.cwy.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cwy.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, int boardId, String title, String body);

	public Article getForPrintArticle(int id);

	public List<Article> getForPrintArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart, int limitTake);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	public List<Article> getForPrintFreeArticles();

	@Select("""
			<script>
				SELECT COUNT(*) AS cnt
				FROM article AS A
				WHERE 1
				<if test="boardId != 0">
					AND A.boardId = #{boardId}
				</if>
				<if test="searchKeyword != ''">
					<choose>
						<when test="searchKeywordTypeCode == 'title'">
							AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<when test="searchKeywordTypeCode == 'body'">
							AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
						</when>
						<otherwise>
							AND (
								A.title LIKE CONCAT('%', #{searchKeyword}, '%')
								OR A.body LIKE CONCAT('%', #{searchKeyword}, '%')
								)
						</otherwise>
					</choose>
				</if>
			</script>
							""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);
	// 게시물 카운트
	
	@Update("""
			<script>
				UPDATE article SET hitCount = hitCount + 1
				WHERE id = #{id}
			</script>
							""")
	public int increaseHitCount(int id);
	// 조회수 증가
	
	@Select("""
			<script>
				SELECT hitCount
				FROM article
				WHERE id = #{id}
			</script>
				""")
	public int getArticleHitCount(int id);
	// 조회수 가져오기

	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint + 1
				WHERE id = #{relId}
			</script>
					""")
	public int increaseGoodRp(int relId);
	// 좋아요 증가 update
	
	@Update("""
			<script>
				UPDATE article
				SET goodReactionPoint = goodReactionPoint - 1
				WHERE id = #{relId}
			</script>
					""")
	public int decreaseGoodRp(int relId);
	// 좋아요 취소 update

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint + 1
				WHERE id = #{relId}
			</script>
					""")
	public int increaseBadRp(int relId);
	// 싫어요 증가 update

	@Update("""
			<script>
				UPDATE article
				SET badReactionPoint = badReactionPoint - 1
				WHERE id = #{relId}
			</script>
					""")
	public int decreaseBadRp(int relId);
	// 실헝요 취소 update

	@Select("""
			<script>
				SELECT *
				FROM article
				WHERE id = #{id}
			</script>
				""")
	public Article getArticle(int id);

}
