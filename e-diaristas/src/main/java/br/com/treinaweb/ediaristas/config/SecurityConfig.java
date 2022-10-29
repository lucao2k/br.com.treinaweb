package br.com.treinaweb.ediaristas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.treinaweb.ediaristas.core.enums.TipoUsuario;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${br.com.treinaweb.ediaristas.rememberMe.key}")
    private String RememberMeKey;

    @Value("${br.com.treinaweb.ediaristas.rememberMe.validitySeconds}")
    private int RememberMeValiditySeconds;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().anyRequest().permitAll(); //permito acesso a todas paginas para qualquer um
        http.authorizeRequests()
            .antMatchers("/admin/**").hasAuthority(TipoUsuario.ADMIN.toString())
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/admin/login")
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/admin/servicos")
            .permitAll();

        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout", "GET"))
            .logoutSuccessUrl("/admin/login");

        http.rememberMe()
            .rememberMeParameter("lembrar-me")
            .tokenValiditySeconds(RememberMeValiditySeconds)
            .key(RememberMeKey);
    }

    @Override //Arquivos ESTATICOS ficam aqui.
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/webjars/**")
            .antMatchers("/img/**");
    }
    
}