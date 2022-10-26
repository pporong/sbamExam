package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.ArticleService;
import com.cwy.exam.demo.service.BoardService;
import com.cwy.exam.demo.service.ReactionPointService;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class ReactionPointController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody
	public int increaseGoodRp(int id) {
    	// article 테이블에서 해당 게시물의 좋아요 1 증가 
		reactionPointService.increaseGoodRp(id);
        // article 테이블에서 해당 게시물의 최신화된 좋아요 수 불러오기
		int goodRp = reactionPointService.getGoodRpCount(id);

		return goodRp;
	}


}
