package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection conn = null;
//		PreparedStatement stmt = null;
		
		try{
			ServletContext sc = this.getServletContext();
//			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection( sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));

//			conn = (Connection)sc.getAttribute("conn");
//			
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			memberDao.delete(Integer.parseInt(req.getParameter("no")));
			
//			stmt = conn.prepareStatement("DELETE FROM MEMBERS"
//					+ " WHERE MNO=?");
//			stmt.setInt(1, Integer.parseInt(req.getParameter("no")));
//			
//			stmt.executeUpdate();
			resp.sendRedirect("list");
		} catch (Exception e) {
//			throw new ServletException(e);
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		} finally{
//			try { if(stmt!=null) stmt.close(); } catch(Exception e){};
//			try { if(conn!=null) conn.close(); } catch(Exception e){};
		}
	}
}
