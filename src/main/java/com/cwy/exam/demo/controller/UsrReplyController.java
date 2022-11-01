package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.ReplyService;
import com.cwy.exam.demo.util.Ut;
import com.cwy.exam.demo.vo.Article;
import com.cwy.exam.demo.vo.ResultData;
import com.cwy.exam.demo.vo.Rq;

import lombok.Data;

@Controller
public class UsrReplyController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	
	// 액션메서드
	@RequestMapping("/usr/reply/doWrite")
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {
		
		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode 을(를) 입력 해 주세요. !!");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("relId 을(를) 입력 해 주세요. !!");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body 을(를) 입력 해 주세요. !!");
		}
		
		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		
		int id = writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			switch(relTypeCode) {
			case "article" :
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}
		
		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
}
