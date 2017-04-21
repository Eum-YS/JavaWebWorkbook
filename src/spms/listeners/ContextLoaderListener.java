package spms.listeners;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
//	Connection conn;
//	DBConnectionPool connPool;
//	BasicDataSource ds;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			ServletContext sc = arg0.getServletContext();
//			Class.forName(sc.getInitParameter("driver"));
			
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb");
			
//			ds = new BasicDataSource();
//			ds.setDriverClassName(sc.getInitParameter("driver"));
//			ds.setUrl(sc.getInitParameter("url"));
//			ds.setUsername(sc.getInitParameter("username"));
//			ds.setPassword(sc.getInitParameter("password"));
			
//			connPool = new DBConnectionPool(sc.getInitParameter("driver"),
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password"));
//			
//			Connection conn = DriverManager.getConnection(
//					sc.getInitParameter("url"),
//					sc.getInitParameter("username"),
//					sc.getInitParameter("password")
//					);
//			
			MemberDao memberDao = new MemberDao();
//			memberDao.setConnection(conn);
//			memberDao.setConnectionPool(connPool);
			memberDao.setDataSource(ds);
			sc.setAttribute("memberDao", memberDao);		
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
//		try{
//			conn.close();
//		} catch (Exception e) {}
//		connPool.closeAll();
//		try{ if (ds != null) ds.close(); } catch (SQLException e){}
	}
}
