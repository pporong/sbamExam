package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.ReactionPointService;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class UsrReactionPointController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
		
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		if (actorCanMakeReaction == false) {
			return rq.jsHistoryBackOnView("좋아요는 한번만 누를 수 있어요!");
		}

		reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace("좋아요를 눌렀어요!", replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public String addBadReactionPoint(String relTypeCode, int relId, String replaceUri) {	
		
		boolean actorCanMakeReaction = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(), relTypeCode, relId);
		if (actorCanMakeReaction == false) {
			return rq.jsHistoryBackOnView("싫어요는 한번만 누를 수 있어요!");
		}

		reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
		
		return rq.jsReplace("싫어요를 눌렀어요!", replaceUri);
	}
	
	@RequestMapping("/usr/reactionPoint/doDeleteBadReaction")
	@ResponseBody
	public String doDeleteBadReaction(String relTypeCode, int relId, String replaceUri) {

		reactionPointService.doDeleteBadReaction(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace("싫어요를 취소했어요!", replaceUri);
	}

}
