package com.algamoney.algamoneyapi.cors;

import com.algamoney.algamoneyapi.config.property.AlgamoneyApiProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private AlgamoneyApiProperty algamoneyApiProperty;

    public CorsFilter(AlgamoneyApiProperty algamoneyApiProperty) {
        this.algamoneyApiProperty = algamoneyApiProperty;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        res.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOriginPermitida());
        res.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equals(req.getMethod()) && algamoneyApiProperty.getOriginPermitida().equals(req.getHeader("Origin"))){
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            res.setHeader("Access-Control-Maz-Age", "3600");

            res.setStatus(HttpServletResponse.SC_OK);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
