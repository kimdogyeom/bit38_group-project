package bit38_7.MapConvertor.config;


import bit38_7.MapConvertor.argumentresolver.LoginMemberArgumentResolver;
import bit38_7.MapConvertor.interceptor.BuildingCheckInterceptor;
import bit38_7.MapConvertor.interceptor.LogInterceptor;
import bit38_7.MapConvertor.interceptor.LoginCheckInterceptor;
import java.util.List;

import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	/**
	 * CORS 설정
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*");
	}


	private final JdbcFileRepository jdbcFileRepository;

	@Autowired
	public WebConfig(JdbcFileRepository jdbcFileRepository) {
		this.jdbcFileRepository = jdbcFileRepository;
	}


	/**
	 * 로그인 체크 인터셉터
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns("/error");

		registry.addInterceptor(new LoginCheckInterceptor())
			.order(2)
			.addPathPatterns("/**")
			.excludePathPatterns("/file/**","/v3/api-docs/**","/swagger-ui/**","/file/save", "/users/id","/users/pw", "/join", "/login", "/logout", "/error", "/*.ico");

		registry.addInterceptor(new BuildingCheckInterceptor(jdbcFileRepository))
				.order(3)
				.addPathPatterns("/{buildingId}/**")
				.excludePathPatterns("/v3/api-docs/**","/swagger-ui/**","/file/save", "/users/id","/users/pw", "/join", "/login", "/logout", "/error", "/*.ico");

	}

}
