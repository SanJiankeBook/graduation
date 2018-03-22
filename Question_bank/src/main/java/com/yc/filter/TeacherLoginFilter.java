package com.yc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/back/*")
public class TeacherLoginFilter extends AbstractFilter{

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException {
//
//		HttpServletRequest request = (HttpServletRequest) sRequest;
//		HttpServletResponse response = (HttpServletResponse) sResponse;
//
//		// 获取项目路径
//		String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+  request.getContextPath() + "/";
//		// 项目首页路径
//		String homePage = basePath + "login.jsp";
//		HttpSession session = request.getSession();
//		String uname = (String) session.getAttribute("userName");
//		String identity = (String) session.getAttribute("identity");
//		if (identity == null || "".equals(identity)) {
//			response.sendRedirect(homePage);
//			return;
//		}
//		if (identity.equals("1")) {
//			response.sendRedirect(homePage);
//			return;
//		}
//		if (uname == null || uname == "") {
//			response.sendRedirect(homePage);
//			return;
//		}
		filterChain.doFilter(sRequest, sResponse);
	}
}
