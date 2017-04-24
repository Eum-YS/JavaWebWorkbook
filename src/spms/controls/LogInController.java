package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LogInController implements Controller {
	MemberDao memberDao;
	
	public LogInController setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("email") == null){
			return "/auth/LoginForm.jsp";
		}
		Member member = memberDao.exist((String)model.get("email"), (String)model.get("password"));
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
