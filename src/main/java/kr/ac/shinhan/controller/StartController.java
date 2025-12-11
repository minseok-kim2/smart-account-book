package kr.ac.shinhan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {
	@GetMapping("/mainStart")
	public String hello(Model m) {
		m.addAttribute("mainStart", "spring! Start Controller");
		return "mainStart";
	}
}
