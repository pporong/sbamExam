package com.cwy.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwy.exam.demo.repository.ReactionPointRepository;
import com.cwy.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;

	@Autowired
	private ArticleService articleService;

	// 너 추천 남겼니?
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 후 이용 가능합니다.");
		}
		
		int sumReactionPointByMemberId =  reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);
		
		if(sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천기능 사용 불가!", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		
		return ResultData.from("S-1", "추천기능 사용 가능!", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	// 좋아요
	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.increaseGoodRp(relId);
				break;
		}
		return ResultData.from("S-1", "좋아요 처리 완료~");
	}
	
	
	// 좋아요 취소
	public ResultData deleteGoodRp(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.delGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.decreaseGoodRp(relId);
				break;
		}
		return ResultData.from("S-1", "좋아요 취소 처리 완료~");
		
	}
	
		
	// 싫어요
	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.increaseBadRp(relId);
				break;
		}
		return ResultData.from("S-1", "싫어요 처리 완료~");
	}
	

	// 싫어요 취소
	public ResultData deleteBadRp(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.delBadReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.decreaseBadRp(relId);
				break;
		}
		return ResultData.from("S-1", "싫어요 취소 처리 완료~");
		
	}

	// 멤버별 게시물 추천 상황 
	public ResultData getRpInfoByMemberId(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.getRpInfoByMemberId(actorId, relTypeCode, relId);
		
		return ResultData.from("S-2", "게시물별 추천 상황");
	}
	
	

}