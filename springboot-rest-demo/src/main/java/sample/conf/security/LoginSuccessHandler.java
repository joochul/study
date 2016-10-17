package sample.conf.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import sample.user.model.CurrentUser;
import sample.user.model.Role;
import sample.user.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    private final ObjectMapper mapper;
 
    @Autowired
    LoginSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.mapper = messageConverter.getObjectMapper();
    }
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
 
        //로그인 사용자 정보
        CurrentUser userDetails = (CurrentUser) authentication.getPrincipal();
        User user = userDetails.getUser();
        System.out.println("####### "+userDetails.getUsername() + " got is connected ");
        
        /*
		String userName = request.getParameter("userid");
		String password = request.getParameter("password");
		String apiMode  = request.getParameter("mode");
		*/
   		String apiMode  = request.getParameter("mode");//접근 모드
   		
   		//사용자 정보
		String userName = user.getUsername();
		String password = user.getPassword();
		Role   role     = user.getRole();
		
		HttpSession session = request.getSession();
		session.setAttribute("userid", userName);
		session.setAttribute("password", password);
		session.setAttribute("mode", apiMode);
		
		System.out.println("[SUCCESS]로그인 성공 : "+userName+"[session]"+session.getId());
		
		
		if("true".equals(apiMode)){
			System.out.println("[API LOGIN]");
			
			Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
			returnMap.put("MESSAGE", "success-login");
			returnMap.put("JSESSIONID", session.getId());
			
			PrintWriter writer = response.getWriter();
	        //mapper.writeValue(writer, user);
	        mapper.writeValue(writer, returnMap);
	        writer.flush();
			
		}else{
			System.out.println("[WEB LOGIN]");
			response.sendRedirect(request.getContextPath() + "/");
		}
    }
}