package swteam6.backend.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import swteam6.backend.security.JwtAuthenticationFilter;
import swteam6.backend.security.JwtTokenProvider;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    // 비밀번호 암호화 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // HTTP 보안 필터 체인
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // CSRF는 REST API에선 보통 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션을 사용하지 않고 JWT 같은 토큰 기반 인증 (필요시)
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 요청 인증/인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/review/**").authenticated()
                        .requestMatchers("/user/profile").authenticated()
                        .anyRequest().permitAll()
                )
                // 기본 제공 폼로그인 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic은 선택적으로 유지하거나 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); //필터 등록

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // 허용
        configuration.addAllowedOrigin("http://localhost:3000"); // 허용
        configuration.addAllowedOrigin("http://13.124.170.215:3000");

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용 메서드
        configuration.addAllowedHeader("*");     // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키/Authorization 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위에서 설정한 CORS 정책 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}