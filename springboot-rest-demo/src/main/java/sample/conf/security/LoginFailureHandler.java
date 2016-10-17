package sample.conf.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 인증 실패
 * @author Charles.Lee
 *
 */
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	/**
	 * 인증 실패 처리
	 */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 
        PrintWriter writer = response.getWriter();
        writer.write(exception.getMessage());
        writer.flush();
    }
}
