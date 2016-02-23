package com.guwan.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guwan.pojo.JavaBean;

@Controller
@RequestMapping("/")
public class IndexController {


	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		return "pages/index";
	}


}
