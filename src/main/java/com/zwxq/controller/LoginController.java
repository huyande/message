package com.zwxq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
	
	@RequestMapping(value="/index")
	public String index(Model  model,@RequestParam(value = "error", required = false) String error) {
		if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
		return "login";
	}
	
	
	@RequestMapping("/admin/logout")
	public void logout() {
		
	}


}
