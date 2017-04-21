package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
//		rd.forward(request, response);
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		Connection conn = null;
//		PreparedStatement stmt = null;
		
		try{
//			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//			conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost/studydb",
//					"study",
//					"1111");
//			conn = (Connection)this.getServletContext().getAttribute("conn");
//			
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			
			MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");
			memberDao.insert(new Member().setEmail(req.getParameter("email"))
					.setPassword(req.getParameter("password"))
					.setName(req.getParameter("name")));
			
//			stmt = conn.prepareStatement("insert into members(email, pwd, mname, cre_date, mod_date)"
//					+ "values(?, ?, ?, now(), now())");
//			stmt.setString(1,  req.getParameter("email"));
//			stmt.setString(2,  req.getParameter("password"));
//			stmt.setString(3,  req.getParameter("name"));
//			stmt.executeUpdate();

			resp.sendRedirect("list");
			
//			resp.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = resp.getWriter();
//			out.println("<html><head><title>회원등록결과</title>");
//			out.println("<meta http-equiv='Refresh' content=1; url=list");
//			out.println("</head>");
//			out.println("<body>");
//			out.println("<p>등록 성공입니다!</p>");
//			out.println("</body></html>");
			
//			resp.addHeader("Refresh", "1;url=list");
		} catch (Exception e) {
//			throw new ServletException(e);
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
			
		} finally {
//			try{ if(stmt != null) stmt.close(); } catch (Exception e){}
//			try{ if(conn != null) conn.close(); } catch (Exception e){}
		}
	}
}
