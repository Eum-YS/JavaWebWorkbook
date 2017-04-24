package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		try{
			MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");
			Member member = memberDao.exist(req.getParameter("email"), req.getParameter("password"));

			if(member != null){
				HttpSession session = req.getSession();
				session.setAttribute("member", member);
				
				req.setAttribute("viewUrl", "redirect:../member/list.do");
				
			} else {
				req.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}			
		} catch (Exception e) {
			throw new ServletException(e);
		} finally { }
		
	}
	
}
