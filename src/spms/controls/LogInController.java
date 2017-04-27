package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

@Component("/auth/login.do")
public class LogInController implements Controller, DataBinding {
	MemberDao memberDao;
	
	public LogInController setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		return new Object[] { "email", String.class, "password", String.class };
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		String email = (String)model.get("email");
		if(email == null){
			return "/auth/LoginForm.jsp";
		}

		Member member = memberDao.exist(email, (String)model.get("password"));
		if(member != null){
			HttpSession session = (HttpSession)model.get("session");
			session.setAttribute("member", member);
			return "redirect:../member/list.do";
		}
		else{
			return "/auth/LoginFail.jsp";
		}
	}

}
