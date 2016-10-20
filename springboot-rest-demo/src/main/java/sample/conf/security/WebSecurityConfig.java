package sample.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//private static final String LOGIN_PATH = ApiPaths.ROOT + ApiPaths.User.ROOT + ApiPaths.User.LOGIN;
	
	private static final String LOGIN_PATH = "/login";
	
	/**
	 * success login
	 */
	@Autowired
    private LoginSuccessHandler loginSuccessHandler;
	/**
	 * fail login
	 */
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    /**
     * success logout
     */
	@Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;
	/**
	 * no authentication
	 */
	@Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
	/**
	 * access denied
	 */
	@Autowired
	private HttpAccessDeniedHandler accessDeniedHandler;
	
    /**
     * user info 
     */
	@Autowired
    private UserDetailsService userDetailsService;
	
	
	/**
	 * security configure
	 * 
	 * @param http
	 * @throws Exception
	 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
            .authorizeRequests()
                .antMatchers("/jpa/**").permitAll()
                .antMatchers("/carmongo/**").permitAll()
                .antMatchers("/carjpa/**").permitAll()
                .antMatchers("/mongo/**").permitAll()
                .antMatchers("/mybatis/**").permitAll()
                .antMatchers("/yaml/**").permitAll()
                .antMatchers("/test/**").permitAll()
                //.antMatchers("/user/**").permitAll()

                .antMatchers("/", "/welcom").permitAll()
                .antMatchers("/web/main").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                .antMatchers("/web/admin").access("hasRole('ROLE_ADMIN')")
                
                .anyRequest().authenticated()     // 인증 시 접근
                .and()
                
            .formLogin()
            	.loginPage(LOGIN_PATH)               // login url 정의
            	.usernameParameter("userid")         // 로그인 username 파라메터 정의
            	.passwordParameter("password")       // 로그인 password 파라메터 정의
                .successHandler(loginSuccessHandler) // 로그인 성공 시 액션
                .failureHandler(loginFailureHandler) // 로그인 실패 시 액션
                .permitAll()
                .and()
                
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) //인증 안된 접근 시(로그인 전 접근)
                .accessDeniedHandler(accessDeniedHandler)           //접근 권한이 없을 시(로그인 후 접근 권한 없음)
                .and()
                
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGIN_PATH, "DELETE"))
                .permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
            .csrf() 
            	.disable() //disable CSRF
             ;
    }
    
    

	/**
	 * DB에서 사용자 정보를 가져와 세팅
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}