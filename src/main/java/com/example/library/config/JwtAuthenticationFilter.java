package com.example.library.config;

import com.example.library.services.CustomUserDetailsService;
import com.example.library.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Lấy header Authorization từ yêu cầu
        String header = request.getHeader("Authorization");

        // Kiểm tra header có tồn tại và bắt đầu bằng "Bearer "
        if (header != null && header.startsWith("Bearer ")) {
            // Trích xuất token từ header (bỏ phần "Bearer " ở đầu)
            String token = header.substring(7);
            try {
                // Lấy tên người dùng từ token
                String phone = jwtUtil.extractPhone(token);

                // Kiểm tra username hợp lệ và chưa có xác thực trong SecurityContext
                if (phone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Tải thông tin chi tiết người dùng từ UserDetailsService
                    UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

                    // Xác thực token
                    if (jwtUtil.validateToken(token, userDetails)) {
                        // Tạo đối tượng xác thực và thiết lập vào SecurityContext
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Nếu có lỗi (token không hợp lệ, hết hạn, v.v.), xóa SecurityContext
                SecurityContextHolder.clearContext();
            }
        }

        // Tiếp tục chuỗi filter
        chain.doFilter(request, response);
    }
}