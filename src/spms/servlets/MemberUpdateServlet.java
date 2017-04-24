package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		try{
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			Member member = memberDao.selectOne(Integer.parseInt(req.getParameter("no")));
			req.setAttribute("member", member);
			req.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally { }
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			try{
				ServletContext sc = this.getServletContext();

				MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
				Member member = (Member)req.getAttribute("member");
				memberDao.update(member);

				req.setAttribute("viewUrl", "redirect:list.do");
			} catch (Exception e){
				
			} finally { }
	}

}
