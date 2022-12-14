package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.ArticleService;
import com.cwy.exam.demo.service.ReplyService;
import com.cwy.exam.demo.util.Ut;
import com.cwy.exam.demo.vo.Article;
import com.cwy.exam.demo.vo.Reply;
import com.cwy.exam.demo.vo.ResultData;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {

	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {
		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode을(를) 입력해주세요");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("relId을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body을(를) 입력해주세요");
		}

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;
			}
		}
		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}
	
	// 수정
	@RequestMapping("/usr/reply/modify")
	public String modify(int id, String replaceUri, Model model) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("해당 id는 존재하지 않습니다.");
		}

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		if (reply.isExtra__actorCanModify() == false) {
			return rq.jsHistoryBack("해당 댓글에 대한 수정 권한이 없습니다.");
		}

		String relDataTitle = null;

		switch (reply.getRelTypeCode()) {
		case "article":
			Article article = articleService.getArticle(reply.getRelId());
			relDataTitle = article.getTitle();
			break;
		}

		model.addAttribute("reply", reply);
		model.addAttribute("relDataTitle", relDataTitle);

		return "usr/reply/reModify";
	}
	
	// 수정
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

		if (Ut.empty(id)) {
			return rq.jsHistoryBack(Ut.f("존재하지 않는 id입니다."));
		}
		
		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("!! 수정 내용을 입력 해 주세요. !!");
		}

		if (reply.isExtra__actorCanModify() == false) {
			return rq.jsHistoryBack(Ut.f("해당 댓글에 대한 수정 권한이 없습니다."));
		}

		ResultData modifyReplyRd = replyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}

		}

		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}

	// 삭제
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack(Ut.f("존재하지 않는 id입니다."));
		}

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

		if (reply == null) {
			return rq.jsHistoryBack(Ut.f("%d번 댓글은 존재하지 않습니다.", id));
		}
		if (reply.isExtra__actorCanDelete() == false) {
			return rq.jsHistoryBack(Ut.f("해당 댓글에 대한 삭제 권한이 없습니다."));
		}

		ResultData<Integer> deleteReplyRd = replyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
			replyService.deleteReply(id);
		}
		return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}

}
