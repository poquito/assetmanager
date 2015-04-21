package at.poquito.assetmanager.web.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import at.poquito.assetmanager.intern.AssetManagerContextFactory;

@WebFilter("/api/*")
public class AuthorizationFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class.getName());

	@Inject
	private AssetManagerContextFactory contextFactory;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException {

		contextFactory.setCurrentRequest((HttpServletRequest) servletRequest);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("assetmanager authorization filter initialized");
	}

}
