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

	public boolean actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return false;
		}
		return reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId) == 0;
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.increaseGoodRp(relId);
				break;
		}
		return ResultData.from("S-1", "좋아요 처리 완료~");
	}
		
	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.increaseBadRp(relId);
				break;
		}
		return ResultData.from("S-1", "싫어요 처리 완료~");
	}
	

	public ResultData doDeleteBadReaction(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.delBadReactionPoint(actorId, relTypeCode, relId);
		
		switch (relTypeCode) {
		case "article" :
				articleService.decreaseBadRp(relId);
				break;
		}
		return ResultData.from("S-1", "싫어요 처리 완료~");
		
	}
	
	// 멤버별 게시물 추천 상황 
	public ResultData getRpInfoByMemberId(int actorId, String relTypeCode, int relId) {
		
		reactionPointRepository.getRpInfoByMemberId(actorId, relTypeCode, relId);
		
		return ResultData.from("S-2", "게시물별 추천 상황");
	}

}


