package com.zb.cms.user.config.filter;

import com.zb.cms.user.service.customer.CustomerService;
import com.zb.doamin.common.UserVo;
import com.zb.doamin.config.JwtAuthenticationProvider;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@WebFilter(urlPatterns = "/customer/*")
@RequiredArgsConstructor
public class CustomerFilter implements Filter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKE");
        if (!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("Invalid Access");
        }
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);

        //토큰 파싱해서 얻은 사용자 정보가 DB 에 있는지 확인
        customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new ServletException("토큰이 유효하지 않니다."));

        chain.doFilter(req, response);

    }
}
