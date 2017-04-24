package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null){
			Integer no = Integer.parseInt((String)model.get("no"));
		    Member member = memberDao.selectOne(no);
		    model.put("member", member);
		    return "/member/MemberUpdateForm.jsp";
		}		
		
		Member member = (Member)model.get("member");
		memberDao.update(member);
		
		return "redirect:list.do";
	}

}
