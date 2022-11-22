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

		return rq.jsReplace(Ut.f("%s님 !! 회원가입이 완료되었습니다~ 로그인 후 이용해주세요 :)", member.getName()), afterJoinUri);
	}
	
	@RequestMapping("usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	// 회원가입시 id 중복검사
	@RequestMapping("usr/member/doCheckLoginId")
	@ResponseBody
	public ResultData doCheckLoginId(String loginId) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-A1", "아이디를 입력해주세요");
		}

		Member oldMember = memberService.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return ResultData.from("F-A2", "이미 존재하는 아이디 입니다.", "logindId", loginId);
		}

		return ResultData.from("S-A1", "사용 가능한 아이디 입니다.", "logindId", loginId);
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

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다");
		}

		rq.login(member);

		return Ut.jsReplace(Ut.f("%s님 환영합니다", member.getNickname()), afterLoginUri);
	}
	
	// 로그인 아이디 찾기
	@RequestMapping("usr/member/findLoginId")
	public String showFindLoginId() {
		return "usr/member/findLoginId";
	}

	@RequestMapping("usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(String name, String email,
			@RequestParam(defaultValue = "/") String afterFindLoginIdUri) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("!! 이름과 이메일을 다시 확인 해 주세요. !!");
		}

		return Ut.jsReplace(Ut.f("회원님의 아이디는 [ %s ] 입니다", member.getLoginId()), afterFindLoginIdUri);
	}

	// 로그인 비밀번호 찾기
	@RequestMapping("usr/member/findLoginPw")
	public String showFindLoginPw() {
		return "usr/member/findLoginPw";
	}

	@RequestMapping("usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(String loginId, String email,
			@RequestParam(defaultValue = "/") String afterFindLoginPwUri) {

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("!! 일치하는 회원이 없습니다. !!");
		}

		if (member.getEmail().equals(email) == false) {
			return Ut.jsHistoryBack("!! 이메일을 다시 확인 해 주세요. !!");
		}

		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmailRd(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getMsg(), afterFindLoginPwUri);
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
	
	//	회원가입시 비밀번호 확인
	@RequestMapping("usr/member/checkPassword")
	public String showCheckPassword() {

		return "usr/member/checkPassword";
	}

	@RequestMapping("usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
		
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("!! 비밀번호를 입력 해 주세요. !!");
		}
		
		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
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
			return rq.jsHistoryBack("비밀번호를 입력해주세요");
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
