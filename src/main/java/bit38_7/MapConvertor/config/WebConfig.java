package bit38_7.MapConvertor.config;


import bit38_7.MapConvertor.interceptor.BuildingCheckInterceptor;
import bit38_7.MapConvertor.interceptor.LogInterceptor;
import bit38_7.MapConvertor.interceptor.LoginCheckInterceptor;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	public WebConfig(JdbcFileRepository jdbcFileRepository) {
		this.jdbcFileRepository = jdbcFileRepository;
	}

	private final JdbcFileRepository jdbcFileRepository;

	/**
	 * CORS 설정
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*");
	}


//	/**
//	 * 로그인한 사용자 정보를 HandlerMethodArgumentResolver 에서 사용할 수 있도록 설정
//	 */
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//		resolvers.add(new LoginMemberArgumentResolver());
//	}


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
			.addPathPatterns("/file/{buildingId}/**")
			.excludePathPatterns("/v3/api-docs/**","/swagger-ui/**", "/users/pw", "/join", "/login", "/logout", "/error", "/*.ico");// 여기 사용자가 qr로 접근할때 문제있을듯 나중에 검토
	}

}
