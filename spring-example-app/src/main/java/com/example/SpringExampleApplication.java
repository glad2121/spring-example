package com.example;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.boot.web.servlet.server.Encoding.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExampleApplication.class, args);
    }

    @Autowired
    ServerProperties properties;

    static final Pattern PATTERN = Pattern.compile(String.join("|",
            "/greeting",
            "/person(?:/.*)?"));

    /**
     * 特定のパスのみ文字コードを ISO-8859-1 にする。
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter() {
            @Override
            protected void doFilterInternal(
                    HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain)
                    throws ServletException, IOException {

                log.info("requestURI: {}", request.getRequestURI());

                if (PATTERN.matcher(request.getRequestURI()).matches()) {
                    request.setCharacterEncoding("ISO-8859-1");
                    log.info("characterEncoding: {}", request.getCharacterEncoding());
                    filterChain.doFilter(request, response);
                } else {
                    super.doFilterInternal(request, response, filterChain);
                }
            }
        };
        Encoding encoding = properties.getServlet().getEncoding();
        filter.setEncoding(encoding.getCharset().name());
        filter.setForceRequestEncoding(encoding.shouldForce(Type.REQUEST));
        filter.setForceResponseEncoding(encoding.shouldForce(Type.RESPONSE));
        return filter;
    }

    /**
     * Tomcat の URIEncoding に {@link ServletRequest#getCharacterEncoding()} を使用する。
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.setUseBodyEncodingForURI(true);
        });
        return factory;
    }
}
