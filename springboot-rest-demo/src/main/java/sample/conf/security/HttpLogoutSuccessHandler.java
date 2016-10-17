package sample.conf.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class HttpLogoutSuccessHandler implements LogoutSuccessHandler {
	
	
	
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
    	
    	System.out.println("[SUCCESS]로그아웃 성공 ");
    	
        response.setStatus(HttpServletResponse.SC_OK);

        //특정 액션으로 넘길 수 있음
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/yaml");
		//dispatcher.forward(request, response);
        
		response.sendRedirect(request.getContextPath() + "/login");
    }
}