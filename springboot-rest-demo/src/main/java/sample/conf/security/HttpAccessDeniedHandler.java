package sample.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class HttpAccessDeniedHandler implements AccessDeniedHandler{
	
	protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);
	
	private final ObjectMapper mapper;
	
	private String errorPage;
	
	@Autowired
	public HttpAccessDeniedHandler(MappingJackson2HttpMessageConverter messageConverter) {
		System.out.println("[HttpAccessDeniedHandler]set deny url");
		
        this.mapper = messageConverter.getObjectMapper();
		this.setErrorPage("/accessdeny"); //전역을 이용해야함
	}

	
	@Override
    public void handle(HttpServletRequest request
    		         , HttpServletResponse response
    		         , AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		//특정 flag를 통한 제어 샘플
		HttpSession session = request.getSession();
		String mode = (String)session.getAttribute("mode");
		
		if("true".equals(mode)){
			//api
			System.out.println("[API]ACCESS_DENY");
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("STATUS", HttpServletResponse.SC_FORBIDDEN);
			returnMap.put("MESSAGE", WebAttributes.ACCESS_DENIED_403);
			
			PrintWriter writer = response.getWriter();
	        //mapper.writeValue(writer, user);
	        mapper.writeValue(writer, returnMap);
	        writer.flush();
	        
		}else{
			//web
			System.out.println("[WEB]ACCESS_DENY");
			if (!response.isCommitted()) {
				if (this.errorPage != null) {
					// Put exception into request scope (perhaps of use to a view)
					request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
					// Set the 403 status code.
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					// forward to error page.
					RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
					dispatcher.forward(request, response);
				}
				else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
				}
			}
		}
    }
	
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}
}
