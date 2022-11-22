package com.cwy.exam.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.cwy.exam.demo.service.MemberService;
import com.cwy.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isAjax;
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService, HttpServletRequest request) {
		this.req = req;
		this.resp = resp;
		
		paramMap = Ut.getParamMap(req);

		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;
		

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		
		String requestUri = req.getRequestURI();
		
		boolean isAjax = requestUri.endsWith("Ajax");
		if (isAjax == false) {
			if (paramMap.containsKey("ajax") && paramMap.get("ajax").equals("Y")) {
				isAjax = true;
			}
			else if (paramMap.containsKey("isAjax") && paramMap.get("isAjax").equals("Y")) {
				isAjax = true;
			}
		}
		if (isAjax == false) {
			if(requestUri.contains("/get")) {
				isAjax = true;
			}
		}
		this.isAjax = isAjax;
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public boolean isNotLogined() {
		return !isLogined;
	}

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}
	
	public String jsHistoryBackOnView(String resultCode, String msg) {
		req.setAttribute("msg", String.format("[%s] %s", resultCode, msg));
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public String jsHistoryBack(String resultCode, String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}

	
	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		return currentUri;
	}

	public String getEncodedCurrentUri() {

		return Ut.getUriEncoded(getCurrentUri());
	}

	public void printReplaceJs(String msg, String url) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, url));
	}

	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}
	
	public String getFindLoginIdUri() {
		return "../member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri();
	}

	public String getFindLoginPwUri() {
		return "../member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri();
	}

	public String getAfterFindLoginIdUri() {
		return getEncodedCurrentUri();
	}

	public String getAfterFindLoginPwUri() {
		return getEncodedCurrentUri();
	}
	
	public String getJoinUri() {
		return "../member/join?afterLoginUri=" + getAfterLoginUri();
	}

	public String getAfterLoginUri() {
		
		String requestUri = req.getRequestURI();
		
		// login 후 다시 돌아가면 안 되는 url
		switch(requestUri) {
		// login 시 계속 접근 할 수 없는 page
		case "/usr/member/login" :
		// login 버튼 누른 후 join 버튼 누르면 err 발생
//		case "/usr/member/join" :
		case "/usr/member/findLoginId" :
		case "/usr/member/findLoginPw" :
			return Ut.getUriEncoded(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}
		
		return getEncodedCurrentUri();
	}
	
	public String getLogoutUri() {
		
		String requestUri = req.getRequestURI();
		
		switch(requestUri) {
		// logout 시 계속 접근 할 수 없는 page
		case "/usr/article/write" :
		case "/usr/article/modify" :
		case "/usr/member/myPage" :
			return "../member/doLogout?afterLogoutUri=" + "/";
		}
		
		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}	

	public String getAfterLogoutUri() {	
		return getEncodedCurrentUri();
	}
	
	public String getArticleDetailUriFromArticleList(Article article) {
		
		return "../article/detail?id=" + article.getId() + "&listUri=" +  getEncodedCurrentUri();
	}

}
