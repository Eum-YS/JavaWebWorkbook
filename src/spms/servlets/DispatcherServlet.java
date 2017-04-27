package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.listeners.ContextLoaderListener;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String servletPath = req.getServletPath();
		
		try{
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();

			HashMap<String, Object> model = new HashMap<>();
			model.put("session", req.getSession());

			Controller pageController = (Controller)ctx.getBeans(servletPath);
			if(pageController instanceof DataBinding){
				prepareRequestData(req, model, (DataBinding)pageController);
			}
			String viewUrl = pageController.execute(model);
			for(String key : model.keySet()){
				req.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")){
				resp.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = req.getRequestDispatcher(viewUrl);
				rd.include(req, resp);
			}
			
		} catch (Exception e){
			e.printStackTrace();
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		}
	}
	
	private void prepareRequestData(HttpServletRequest req, HashMap<String, Object> model, DataBinding dataBinding) throws Exception{
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		for (int i = 0; i < dataBinders.length; i+=2){
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			dataObj = ServletRequestDataBinder.binder(req, dataType, dataName);
			model.put(dataName, dataObj);
		}
	}
}