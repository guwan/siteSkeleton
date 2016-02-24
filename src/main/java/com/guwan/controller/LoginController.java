package com.guwan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guwan.services.JpaUserDetailsManager;

@Controller
public class LoginController {

	@RequestMapping(value="login", method=RequestMethod.GET)
	public void loginPage(Model model) {
	}
}
