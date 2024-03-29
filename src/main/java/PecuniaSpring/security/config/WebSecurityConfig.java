package PecuniaSpring.security.config;

import PecuniaSpring.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig  {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                        ***********************************
//                        *************API*******************
//                        ***********************************
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .antMatchers("/api/v*/country")
                .permitAll()
                .antMatchers("/api/v*/count_country")
                .permitAll()
                .antMatchers("/api/v*/report/**")
                .permitAll()
                .antMatchers("/api/v*/report/method**")
                .permitAll()
                .antMatchers("/api/v*/reportTest/**")
                .permitAll()
//                        ***********************************
//                        *************VIEW******************
//                        ***********************************

                .antMatchers("/", "/registration", "/about")
                .permitAll()
                .antMatchers("/country", "/country/show/**")
                .permitAll()
//                .antMatchers("/continent").hasAnyAuthority("USER", "ADMIN")
//                ********************NOTE COLLECTOIN*****************************************
                .antMatchers("/note/collection/**").hasAnyAuthority("ADMIN", "USER")
//                ********************NOTE FOR SELL ******************************************
                .antMatchers("/note/forSell/**")
                .permitAll()
//                ********************Coin FOR SELL ******************************************
                .antMatchers("/coin/forSell/**")
                .permitAll()
//                ********************CONTINENT*****************************************
                .antMatchers("/continent/**")
                .permitAll()
//                *********************CURRENCY******************************************
                .antMatchers("/currency", "/currency/show/**", "/currency/list/**")
                .permitAll()
                .antMatchers("/search")
                .permitAll()
//                .antMatchers("/test").hasAnyAuthority("USER")
                .anyRequest().hasAnyAuthority("ADMIN");
        http
                .formLogin().permitAll()
                .loginPage("/login").and()
                .exceptionHandling().accessDeniedPage("/error");
        http
                .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**","/flags/**","/reportsChart/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
