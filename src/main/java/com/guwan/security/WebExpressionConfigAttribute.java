package com.guwan.security;

import org.springframework.expression.Expression;
import org.springframework.security.access.ConfigAttribute;

class WebExpressionConfigAttribute implements ConfigAttribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = -763864457010108413L;
	private final Expression authorizeExpression;

	public WebExpressionConfigAttribute(Expression authorizeExpression) {
		this.authorizeExpression = authorizeExpression;
	}

	Expression getAuthorizeExpression() {
		return authorizeExpression;
	}

	public String getAttribute() {
		return null;
	}

	@Override
	public String toString() {
		return authorizeExpression.getExpressionString();
	}
}