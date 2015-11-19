package com.guwan.security;

import java.util.Collection;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.stereotype.Component;

@Component
public class CustomVoter extends WebExpressionVoter{
	private SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();

	
	@Override
	public int vote(Authentication authentication, FilterInvocation fi,
			Collection<ConfigAttribute> attributes) {
		assert authentication != null;
		assert fi != null;
		assert attributes != null;

		WebExpressionConfigAttribute weca = findConfigAttribute(attributes);

		if (weca == null) {
			return ACCESS_ABSTAIN;
		}

		EvaluationContext ctx = expressionHandler.createEvaluationContext(authentication,
				fi);

		return ExpressionUtils.evaluateAsBoolean(weca.getAuthorizeExpression(), ctx) ? ACCESS_GRANTED
				: ACCESS_DENIED;
	}
	public int vote1(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		if(authentication == null) {
			return ACCESS_DENIED;
		}
		int result = ACCESS_ABSTAIN;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (ConfigAttribute attribute : attributes) {
			if (this.supports(attribute)) {
				result = ACCESS_DENIED;

				// Attempt to find a matching granted authority
				for (GrantedAuthority authority : authorities) {
					if (attribute.getAttribute().equals(authority.getAuthority())) {
						return ACCESS_GRANTED;
					}
				}
			}
		}

		return result;
	}

	private WebExpressionConfigAttribute findConfigAttribute(
			Collection<ConfigAttribute> attributes) {
		for (ConfigAttribute attribute : attributes) {
			if (attribute.getClass().getName().equals("org.springframework.security.web.access.expression.WebExpressionConfigAttribute")) {
				//WebExpressionConfigAttribute wbc=new WebExpressionConfigAttribute(authorizeExpression)
				//return (WebExpressionConfigAttribute) attribute;
			}
		}
		return null;
	}
}
