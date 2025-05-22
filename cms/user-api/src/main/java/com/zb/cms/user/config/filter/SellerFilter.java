package com.zb.cms.user.config.filter;

import com.zb.cms.user.service.seller.SellerService;
import com.zb.doamin.common.UserVo;
import com.zb.doamin.config.JwtAuthenticationProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SellerFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN");

        // 토큰 유효성 검증
        if (!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("토큰이 유효하지 않습니다.");
        }

        // 사용자의 Id, 이메일 정보
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);

        // 사용자 존재 여부 파악
        sellerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new ServletException("토큰이 유효하지 않니다."));

        chain.doFilter(request, response);
    }

}
