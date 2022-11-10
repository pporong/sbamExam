package com.cwy.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cwy.exam.demo.vo.Rq;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {

	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {

		if (rq.isLogined()) {
//			String afterLogoutUri = rq.getAfterLogoutUri();
			rq.printHistoryBackJs("!! 이미 로그인 상태입니다. !!");
//			rq.printReplaceJs("!! 이미 로그인 상태입니다. !!", "../member/login?afterLoginUri=" + afterLoginUri);
			return false;
		}

//		if (!rq.isLogined()) {
//			String afterLoginUri = rq.getAfterLoginUri();
//			rq.printReplaceJs("!! 로그인 후 이용 할 수 있습니다. !!", "../member/login?afterLoginUri=" + afterLoginUri);
//			return false;
//		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}