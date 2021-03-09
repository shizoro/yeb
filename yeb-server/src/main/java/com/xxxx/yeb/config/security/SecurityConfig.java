package com.xxxx.yeb.config.security;

import com.xxxx.yeb.pojo.Admin;
import com.xxxx.yeb.service.IAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置
 * @author arthur
 * @date 2021/3/8 21:20
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private IAdminService adminService;
    @Resource
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 无需经过过滤链，直接放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 放行静态资源
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "/img/**",
                "/fonts/**",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/user/**",
                "/ws/ep/**",
                "/ws/chat/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用JWt令牌，不需要csrf
        http.csrf().disable()
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 放行登录和注销登录
                .antMatchers("/login","/logout")
                .permitAll()
                // 除了上面的请求其他都需要认证
                .anyRequest()
                .authenticated()
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // JWT登录拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }


    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username ->{
            Admin admin = adminService.getAdminByUserName(username);
            if (null == admin){
                return null;
            }
            return admin;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    
}
