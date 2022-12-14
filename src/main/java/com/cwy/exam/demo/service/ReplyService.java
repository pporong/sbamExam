package com.cwy.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwy.exam.demo.repository.ReplyRepository;
import com.cwy.exam.demo.util.Ut;
import com.cwy.exam.demo.vo.Member;
import com.cwy.exam.demo.vo.Reply;
import com.cwy.exam.demo.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public ReplyService(ReplyRepository replyRepository) {
		this.replyRepository = replyRepository;
	}
	
	public ResultData<Integer> writeReply(int actorId, String relTypeCode, int relId, String body) {
		
		replyRepository.writeReply(actorId, relTypeCode, relId, body);
		
		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.f("%d번 글에 댓글이 등록되었습니다", relId), "id", id);
	}

	// 댓글 list
	public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
		List<Reply> replies =  replyRepository.getForPrintReplies(relTypeCode, relId);
		
		for (Reply reply : replies) {
			updateForPrintData(actor, reply);
		}
		
		return replies;
	}
	
	private void updateForPrintData(Member actor, Reply reply) {
		if(actor == null) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actor, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

		ResultData actorCanModifyRd = actorCanModify(actor, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
		
	}

	// 댓글 수정 권한 체크
	public ResultData actorCanModify(Member actor, Reply reply) {
		
		if (reply == null) {
			return ResultData.from("F-1", "댓글이 존재하지 않습니다");
		}
		if (reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "해당 게시물에 대한 수정 권한이 없습니다");
		}

		return ResultData.from("S-1", "수정 가능");
	}

	// 댓글 수정
	public ResultData modifyReply(int id, String body) {
		replyRepository.modifyReply(id, body);
		 
		 return ResultData.from("S-1", Ut.f("%d번 댓글이 수정되었습니다", id), "id", id);
	}

	// 댓글 삭제 권한 체크
	public ResultData actorCanDelete(Member actor, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다");
		}

		if (reply.getMemberId() != actor.getId()) {
			return ResultData.from("F-2", "해당 게시물에 대한 삭제 권한이 없습니다");
		}

		return ResultData.from("S-1", "삭제 가능");
	}
	
	// 댓글 삭제
	public ResultData<Integer> deleteReply(int id) {
		 replyRepository.deleteReply(id);
		 
		 return ResultData.from("S-1", Ut.f("%d번 댓글이 삭제되었습니다", id), "id", id);
	}

	// 댓글 하나만 가져오기
	public Reply getForPrintReply(Member actor, int id) {
		
		Reply reply = replyRepository.getForPrintReply(id);
		
		updateForPrintData(actor, reply);
		
		return reply; 
	}

}