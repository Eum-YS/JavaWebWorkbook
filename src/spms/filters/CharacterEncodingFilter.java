package spms.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(
		urlPatterns="/*",
		initParams={
				@WebInitParam(name="encoding",value="UTF-8")
		})
public class CharacterEncodingFilter implements Filter {
	FilterConfig config;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain nextFilter) throws IOException, ServletException {
		req.setCharacterEncoding(config.getInitParameter("encoding"));
//		resp.setCharacterEncoding(config.getInitParameter("encoding"));
		nextFilter.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
