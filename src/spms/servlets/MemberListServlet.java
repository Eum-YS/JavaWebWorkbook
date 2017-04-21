package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
//			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//			conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost/studydb", //JDBC URL
//					"study",	// DBMS 사용자 아이디
//					"1111");	// DBMS 사용자 암호
//			conn = (Connection)this.getServletContext().getAttribute("conn");
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);

//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(
//					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
//					" FROM MEMBERS" +
//					" ORDER BY MNO ASC");
//			
//			// UI 출력 코드 제거하고 JSP에 위임
////			response.setContentType("text/html; charset=UTF-8");
////			PrintWriter out = response.getWriter();
////			out.println("<html><head><title>회원목록</title></head>");
////			out.println("<body><h1>회원목록</h1>");
////			out.println("<p><a href='add'>신규 회원</a></p>");
//			
//			ArrayList<Member> members = new ArrayList<Member>();
//			
//			while(rs.next()) {
//				members.add(new Member().setNo(rs.getInt("MNO"))
//						.setName(rs.getString("MNAME"))
//						.setEmail(rs.getString("EMAIL"))
//						.setCreatedDate(rs.getDate("CRE_DATE")));
//	
////				out.println(
////					rs.getInt("MNO") + "," +
////					"<a href='update?no=" + rs.getInt("MNO") + "'>" +
////					rs.getString("MNAME") + "," +
////					"</a> " +
////					rs.getString("EMAIL") + "," + 
////					rs.getDate("CRE_DATE") + 
////					"<a href='delete?no=" + rs.getInt("MNO") + "'>[삭제]</a><br>"
////				);
//			}
////			out.println("</body></html>");
			
			MemberDao memberDao = (MemberDao)this.getServletContext().getAttribute("memberDao");			
			req.setAttribute("members", memberDao.selectList());
			req.setAttribute("viewUrl", "/member/MemberList.jsp");
//			resp.setContentType("text/html; charset=UTF8");
//			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberList.jsp");
//			rd.include(req, resp);	// UI 처리 후 제어권이 다시 넘어옴
//			rd.forward(request, response);	// 제어권이 넘어오지 않음

		} catch (Exception e) {
			throw new ServletException(e);
//			req.setAttribute("error", e);
//			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
//			rd.forward(req, resp);
			
		} finally {
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
//			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
