package bit38_7.MapConvertor.config;


import bit38_7.MapConvertor.argumentresolver.LoginMemberArgumentResolver;
import bit38_7.MapConvertor.interceptor.LogInterceptor;
import bit38_7.MapConvertor.interceptor.LoginCheckInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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


	/**
	 * 로그인한 사용자 정보를 HandlerMethodArgumentResolver 에서 사용할 수 있도록 설정
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
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
			.excludePathPatterns("/test/save","/join", "/users/id","/users/pw", "/login", "/logout", "/error", "/*.png");
	}

}
