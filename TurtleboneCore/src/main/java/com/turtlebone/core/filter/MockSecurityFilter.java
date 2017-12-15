package com.turtlebone.core.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.turtlebone.auth.bean.VerifyTokenRequest;
import com.turtlebone.auth.model.TokenModel;
import com.turtlebone.core.bean.ResultVO;
import com.turtlebone.core.exception.TurtleException;
import com.turtlebone.core.model.UserModel;
import com.turtlebone.core.service.UserService;
import com.turtlebone.core.util.SendHTTPUtil;
import com.turtlebone.core.util.StringUtil;

import lombok.Data;

@Data
public class MockSecurityFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(MockSecurityFilter.class);
	
	private String excludeRegex;
	private String[] excludePaths;
	
	private Environment env;
	
	private UserService userService;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String path = request.getRequestURI();
		
		String tokenId = request.getParameter("tokenId");
		String username = request.getParameter("username");
		
		tokenId = "MOCK";
		username = StringUtil.isEmpty(username) ? "DFS" : username;
		
		request.setAttribute("username", username);
		request.setAttribute("tokenId", tokenId);
		logger.info("Mock URI={}, tokenId={}, username={}", path, tokenId, username);
		
		if (checkExclude(path)) {
			filterChain.doFilter(req, rsp);	
			return;
		}
		
		logger.info("Mock Verification success");
		
		if (StringUtil.isEmpty(tokenId)) {
			rsp.setContentType("application/json");
			rsp.getWriter().print(JSON.toJSONString(new ResultVO<String>(ResultVO.PARAMERROR, "tokenId is empty", "")));
			return;
		}
		
		filterChain.doFilter(req, rsp);
	}
	
	private boolean checkExclude(String path) {
		if (excludePaths == null) {
			excludePaths = excludeRegex.split("\\|");
		}
		for (String ep : excludePaths) {
            if (ep == null || ep.equals("")) {
                continue;
            }
            Pattern p = Pattern.compile(ep);
            Matcher m = p.matcher(path);
            if (m.find()) {
                return true;
            }
        }
        return false;
	}
	
	private TokenModel verifyToken(String tokenId, String username) {
		VerifyTokenRequest request = new VerifyTokenRequest();
		request.setUsername(username);
		request.setTokenId(tokenId);
		String url = "http://turtlebone.top/auth/token/verify";
		try {
			String result = SendHTTPUtil.callApiServer(url, "POST", JSON.toJSONString(request), null);
			TokenModel token = JSON.parseObject(result, TokenModel.class);
			return token;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setExcludeRegex(getPropertyFromInitParams(filterConfig, "excludeRegex", null));
	}
	protected final String getPropertyFromInitParams(final FilterConfig filterConfig, final String propertyName, final String defaultValue)  {
        final String value = filterConfig.getInitParameter(propertyName);

        if (StringUtils.isNotBlank(value)) {
            logger.info("Property [" + propertyName + "] loaded from FilterConfig.getInitParameter with value [" + value + "]");
            return value;
        }

        final String value2 = filterConfig.getServletContext().getInitParameter(propertyName);

        if (StringUtils.isNotBlank(value2)) {
        	logger.info("Property [" + propertyName + "] loaded from ServletContext.getInitParameter with value [" + value2 + "]");
            return value2;
        }
        
        logger.info("Property [" + propertyName + "] not found.  Using default value [" + defaultValue + "]");
        return defaultValue;
    }
}
