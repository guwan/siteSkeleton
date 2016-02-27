package com.guwan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.guwan.domain.User;
import com.guwan.formBean.UserFormBean;
import com.guwan.services.JpaUserDetailsManager;
import com.guwan.support.AjaxUtils;

@Controller
@RequestMapping("/register")
@SessionAttributes("userFormBean")
public class RegisterController {

	private JpaUserDetailsManager userDetailsManager ;
	
	
	@Autowired
    public RegisterController(JpaUserDetailsManager userDetailsManager){
		this.userDetailsManager=userDetailsManager;
	}
	
	// Invoked on every request

	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}

	// Invoked initially to create the "form" attribute
	// Once created the "form" attribute comes from the HTTP session (see @SessionAttributes)

	@ModelAttribute("userFormBean")
	public UserFormBean createUserFormBean() {
		return new UserFormBean();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String form() {
		return "pages/register";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(@Valid UserFormBean userFormBean, BindingResult result, 
								@ModelAttribute("ajaxRequest") boolean ajaxRequest, 
								Model model, RedirectAttributes redirectAttrs,SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			return null;
		}
		// Typically you would save to a db and clear the "form" attribute from the session 
		// via SessionStatus.setCompleted(). For the demo we leave it in the session.
		String message = "Form submitted successfully.  Bound " + userFormBean;
		User user =userFormBean.getFilledUser();
		userDetailsManager.createUserWithDefaultAuth(user);
		sessionStatus.setComplete();//clear the "form" attribute from the session
		// Success response handling
		if (ajaxRequest) {
			// prepare model for rendering success message in this request
			model.addAttribute("message", message);
			return null;
		} else {
			// store a success message for rendering on the next request after redirect
			// redirect back to the form to render the success message along with newly bound values
			redirectAttrs.addFlashAttribute("message", message);
			return "redirect:pages/register";			
		}
	}
}
