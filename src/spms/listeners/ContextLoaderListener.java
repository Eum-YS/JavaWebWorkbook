package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			ServletContext sc = arg0.getServletContext();
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/studydb");
		
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);

			sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogOutController());
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
