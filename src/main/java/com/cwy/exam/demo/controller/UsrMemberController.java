package com.cwy.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwy.exam.demo.service.MemberService;
import com.cwy.exam.demo.util.Ut;
import com.cwy.exam.demo.vo.Member;
import com.cwy.exam.demo.vo.ResultData;
import com.cwy.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	// join
	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return  rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		Member member = memberService.getMemberById(joinRd.getData1());
		
		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);

//		return Ut.jsReplace(Ut.f(" 환영합니다, %s님 !! :) ", member.getName()), afterJoinUri);
		return rq.jsReplace(Ut.f("%s님 !! 회원가입이 완료되었습니다~ 로그인 후 이용해주세요 :)", member.getName()), afterJoinUri);
	}
	
	@RequestMapping("usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}

	
	// login
	@RequestMapping("usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("이미 로그인 되었습니다");
		}

		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("아이디를 입력해주세요");
		}

		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("아이디를 잘못 입력했습니다");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		rq.login(member);

		return Ut.jsReplace(Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		if (!rq.isLogined()) {
			return Ut.jsHistoryBack("로그아웃 상태입니다");
		}

		rq.logout();

		return Ut.jsReplace("로그아웃 되었습니다", afterLogoutUri);
	}
	
	@RequestMapping("usr/member/myPage")
	public String showMyPage() {

		return "usr/member/myPage";
	}
	
	@RequestMapping("usr/member/checkPassword")
	public String shoWCheckPassword() {

		return "usr/member/checkPassword";
	}

	@RequestMapping("usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
		
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("!! 비밀번호를 입력 해 주세요. !!");
		}
		
		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("!! 비밀번호가 일치하지 않습니다. !!");
		}
		
		// memberModifyAuthKey
		if (replaceUri.equals("../member/modifyMyInfo")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId());

			replaceUri += "?memberModifyAuthKey=" + memberModifyAuthKey;
		}

		
		return rq.jsReplace("", replaceUri);
	}
	
	@RequestMapping("usr/member/modifyMyInfo")
	public String modifyMyInfo(String memberModifyAuthKey) {
		
		if (Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBackOnView("!! 회원 수정 인증코드가 필요합니다. !!");
		}
		
		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBackOnView(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		return "usr/member/modifyMyInfo";
		
	}
	
	// 개인정보수정
	@RequestMapping("usr/member/doModifyMyInfo")
	@ResponseBody
	public String doModifyMyInfo(String memberModifyAuthKey, String loginPw, String nickname, String cellphoneNum, String email) {

		if (Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBack("!! 회원 수정 인증코드가 필요합니다. !!");
		}
		

		ResultData checkMemberModifyAuthKeyRd = memberService.checkMemberModifyAuthKey(rq.getLoginedMemberId(),
				memberModifyAuthKey);

		if (checkMemberModifyAuthKeyRd.isFail()) {
			return rq.jsHistoryBack(checkMemberModifyAuthKeyRd.getMsg());
		}
		
		if(Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("이메일을 입력해주세요");
		}
		
		ResultData modifyMyInfoRd = memberService.modifyMyInfo(rq.getLoginedMemberId(), loginPw, nickname, cellphoneNum, email);

		return Ut.jsReplace(modifyMyInfoRd.getMsg(), "/");
		
	}
}
