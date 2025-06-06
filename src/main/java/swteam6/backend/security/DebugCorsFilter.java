package swteam6.backend.security;

// DebugCorsFilter.java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 가장 높은 우선순위로 설정하여 다른 필터보다 먼저 실행
public class DebugCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("--- DebugCorsFilter ---");
        System.out.println("Request URI: " + httpRequest.getRequestURI());
        System.out.println("Request Method: " + httpRequest.getMethod());
        System.out.println("Request Origin: " + httpRequest.getHeader("Origin"));

        // 필터 체인을 계속 진행
        chain.doFilter(request, response);

        // 응답 헤더 확인
        System.out.println("Response Status: " + httpResponse.getStatus());
        System.out.println("Response Access-Control-Allow-Origin: " + httpResponse.getHeader("Access-Control-Allow-Origin"));
        System.out.println("Response Access-Control-Allow-Methods: " + httpResponse.getHeader("Access-Control-Allow-Methods"));
        System.out.println("Response Access-Control-Allow-Headers: " + httpResponse.getHeader("Access-Control-Allow-Headers"));
        System.out.println("Response Access-Control-Allow-Credentials: " + httpResponse.getHeader("Access-Control-Allow-Credentials"));
        System.out.println("--- End DebugCorsFilter ---");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 작업 (필요시)
    }

    @Override
    public void destroy() {
        // 소멸 작업 (필요시)
    }
}