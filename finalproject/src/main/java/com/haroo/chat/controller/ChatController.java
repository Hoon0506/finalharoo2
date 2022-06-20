package com.haroo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.haroo.chat.service.ChatService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
//@RestController
@Log4j
@RequestMapping("/chat/*")
@AllArgsConstructor
public class ChatController {

	@Autowired
	ChatService service; 

	
	@GetMapping("/chat")
	public void chatlist(Model model) {
		
		log.info("list");
		model.addAttribute("chatlist", service.getList());
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("em_no") Long em_no,  Model model) {
		
		log.info("/get");
		model.addAttribute("chatlist", service.getList());
		model.addAttribute("chat", service.get(em_no));
		model.addAttribute("contents", service.getChatList(em_no));
	}
	
//	@RequestMapping("/chat")
//	public ModelAndView chat(Model model) {
//		ModelAndView mv = new ModelAndView();
//		model.addAttribute("chatlist", service.getList());
//		mv.setViewName("chat/chat_message");
//		return mv;
//	}
}