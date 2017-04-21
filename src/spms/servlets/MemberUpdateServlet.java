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
		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
		
		try{
			ServletContext sc = this.getServletContext();
//			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection(sc.getInitParameter("url"), 
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));
//			Class.forName(this.getInitParameter("driver"));
//			conn = DriverManager.getConnection(this.getInitParameter("url"), 
//					this.getInitParameter("username"),
//					this.getInitParameter("password"));
//			conn = (Connection)sc.getAttribute("conn");
			
//			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			Member member = memberDao.selectOne(Integer.parseInt(req.getParameter("no")));
			
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery("select MNO, EMAIL, MNAME, CRE_DATE from MEMBERS" + 
//			" where MNO=" + req.getParameter("no"));
//			
//			rs.next();
//			
//			Member member = new Member().setNo(rs.getInt("MNO"))
//					.setName(rs.getString("MNAME"))
//					.setEmail(rs.getString("EMAIL"))
//					.setCreatedDate(rs.getDate("CRE_DATE"));
//			
			req.setAttribute("member", member);
			
			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(req, resp);
			
//			resp.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = resp.getWriter();
//			
//			out.println("<html><head><title>회원정보</title></head>");
//			out.println("<body><h1>회원정보</h1>");
//			out.println("<form action='update' method='post'>");
//			out.println("번호: <input type='text' name='no' value='" + req.getParameter("no") + "'readonly><br>");
//			out.println("이름: *<input type='text' name='name' value='" + rs.getString("MNAME") + "'><br>");
//			out.println("이메일: <input type='text' name='email' value='" + rs.getString("EMAIL") + "'><br>");
//			out.println("가입일: " + rs.getString("CRE_DATE") + "<br>");
//			out.println("<input type='submit' value='저장'>");
//			out.println("<input type='button' value='삭제'" + " onclick='location.href=\"delete?no=" + req.getParameter("no") + "\"'>");
//			out.println("<input type='button' value='취소'" + " onclick='location.href=\"list\"'>");
//			out.println("</form>");
//			out.println("</body></html>");
		} catch (Exception e) {
//			throw new ServletException(e);
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		} finally{
//			try { if(rs!=null) rs.close(); } catch(Exception e){};
//			try { if(stmt!=null) stmt.close(); } catch(Exception e){};
//			try { if(conn!=null) conn.close(); } catch(Exception e){};
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//			Connection conn = null;
//			PreparedStatement stmt = null;
			try{
				ServletContext sc = this.getServletContext();
//				Class.forName(sc.getInitParameter("driver"));
//				conn = DriverManager.getConnection( sc.getInitParameter("url"),
//						sc.getInitParameter("username"),
//						sc.getInitParameter("password"));
				
//				Connection conn = (Connection)sc.getAttribute("conn");
				
//				MemberDao memberDao = new MemberDao();
//				memberDao.setConnection(conn);
				MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
				memberDao.update(new Member().setNo(Integer.parseInt(req.getParameter("no")))
						.setEmail(req.getParameter("email"))
						.setName(req.getParameter("name")));
				
//				Class.forName(this.getInitParameter("driver"));
//				conn = DriverManager.getConnection( this.getInitParameter("url"),
//						this.getInitParameter("username"),
//						this.getInitParameter("password"));
//				stmt = conn.prepareStatement("UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=now()"
//						+ " WHERE MNO=?");
//				stmt.setString(1, req.getParameter("email"));
//				stmt.setString(2, req.getParameter("name"));
//				stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
//				
//				stmt.executeUpdate();
				resp.sendRedirect("list");
			} catch (Exception e){
				
			} finally {
//				try{ if(stmt!=null) stmt.close(); }catch(Exception e){}
//				try{ if(conn!=null) conn.close(); }catch(Exception e){}
			}
	}

}
