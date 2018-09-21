package org.flow.boot.modeler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;// 如果userDetailService没有扫描到就加上面的@ComponentScan

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()//

                .antMatchers("/**")//
                .permitAll()//
                .anyRequest()//
                .authenticated()//

                .and()//
                .formLogin()//
                .loginPage("/loginP")//
                .permitAll()// 这里程序默认路径就是登陆页面，允许所有人进行登陆
                .loginProcessingUrl("/login")// 登陆提交的处理url
                .failureForwardUrl("/?error=true")// 登陆失败进行转发，这里回到登陆页面，参数error可以告知登陆状态
                .defaultSuccessUrl("/me")// 登陆成功的url，这里去到个人首页

                .and()//
                .logout()//
                .logoutUrl("/logout")//
                .permitAll()//
                .logoutSuccessUrl("/?logout=true")// 按顺序，第一个是登出的url，security会拦截这个url进行处理，所以登出不需要我们实现，第二个是登出url，logout告知登陆状态

                .and()//
                .rememberMe()//
                .tokenValiditySeconds(604800)// 记住我功能，cookies有限期是一周
                .rememberMeParameter("remember-me")// 登陆时是否激活记住我功能的参数名字，在登陆页面有展示
                .rememberMeCookieName("workspace");// cookies的名字，登陆后可以通过浏览器查看cookies名字
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);// 配置自定义userDetailService
    }
}