//package onboardingMarcos.tinelli.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//  private final UserDetailsService userDetailsService;
//
//  public SecurityConfig(UserDetailsService userDetailsService) {
//    this.userDetailsService = userDetailsService;
//  }
//
//
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//        .and()
//        .authorizeRequests(authorizeRequests ->
//            authorizeRequests
//                .anyRequest().authenticated()
//        )
//        .httpBasic(Customizer.withDefaults())
//        .userDetailsService(userDetailsService)
//        .formLogin(Customizer.withDefaults());
//    return http.build();
//  }
//
//  public PasswordEncoder passwordEncoder() {
//    return NoOpPasswordEncoder.getInstance();
//  }
//}
