//package onboardingMarcos.tinelli.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//        .csrf(Customizer.withDefaults())
//        .authorizeHttpRequests(authorize -> authorize
//            .anyRequest().authenticated())
//        .httpBasic(Customizer.withDefaults())
//        .formLogin(Customizer.withDefaults());
//    return http.build();
//  }
//
//
//  @Bean
//  public InMemoryUserDetailsManager userDetailsService() {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    UserDetails user = User.withUsername("diego")
//        .password(encoder.encode("maradona"))
//        .roles("USER")
//        .build();
//    return new InMemoryUserDetailsManager(user);
//  }
//}
