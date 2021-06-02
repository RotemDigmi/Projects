package app.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.sessions.SessionContext;

public class LoginFilter implements Filter {

	private SessionContext sessioncontext;

	public LoginFilter(SessionContext sessioncontext) {
		super();
		this.sessioncontext = sessioncontext;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hsreq = (HttpServletRequest) request;
		String token = hsreq.getHeader("token");
		System.out.println("-----------------" + token + "-----------------");

		try {

			if (hsreq.getMethod().equals("OPTIONS")) {
				System.out.println("prefligh");
				chain.doFilter(hsreq , response);
				return;
			}
			
			if (token != null && sessioncontext.getSession(token) != null) {
				System.out.println(token);
				chain.doFilter(hsreq, response);
				return;
			}
			System.out.println("error------------");
			HttpServletResponse hsresp = (HttpServletResponse) response;
			hsresp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
			hsresp.sendError(HttpStatus.UNAUTHORIZED.value(), "You are NOT logged in.");
			hsresp.setStatus(HttpStatus.UNAUTHORIZED.ordinal());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}