package com.cwy.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwy.exam.demo.repository.ReplyRepository;
import com.cwy.exam.demo.util.Ut;
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

		return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다", id), "id", id);
	}

	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		return replyRepository.getForPrintReplies(actorId, relTypeCode, relId);
	}

	public ResultData<Integer> deleteReply(int id) {
		 replyRepository.deleteReply(id);
		 
		 return ResultData.from("S-2", Ut.f("%d번 댓글이 삭제되었습니다", id), "id", id);
	}

}