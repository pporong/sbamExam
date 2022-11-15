package com.cwy.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsrAPITestContnroller {
	
	@RequestMapping("usr/home/APITest")
	String showTestPage() {
		return "usr/home/APITest";
	}
}


