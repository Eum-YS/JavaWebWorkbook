package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

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
		RequestDispatcher rd = req.getRequestDispatcher("/auth/LoginForm.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;		
		try{
//			conn = (Connection)this.getServletContext().getAttribute("conn");
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");
			Member member = memberDao.exist(req.getParameter("email"), req.getParameter("password"));
//			stmt = conn.prepareStatement(
//					"SELECT MNAME, EMAIL FROM MEMBERS" + 
//					" WHERE EMAIL=? AND PWD = ?"
//					);
//			
//			stmt.setString(1, req.getParameter("email"));
//			stmt.setString(2, req.getParameter("password"));
//			
//			rs = stmt.executeQuery();
			if(member != null){
//				Member member = new Member()
//					.setEmail(rs.getString("EMAIL"))
//					.setName(rs.getString("MNAME"));
				HttpSession session = req.getSession();
				session.setAttribute("member", member);
				
				resp.sendRedirect("../member/list");
			} else {
				RequestDispatcher rd = req.getRequestDispatcher("/auth/LoginFail.jsp");
				rd.forward(req, resp);
			}			
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
//			try{ if(rs != null) rs.close(); } catch (Exception e){}
//			try{ if(stmt != null) stmt.close(); } catch (Exception e){}
		}
		
	}
	
}
