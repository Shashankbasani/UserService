//package com.scaler.userservicejuly.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SpringSecurityconfiguration {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
//        http.authorizeHttpRequests((request)->{
//            try {
//                request.anyRequest().permitAll()
//                        .and().cors().disable()
//                        .csrf().disable();
//            }catch (Exception e){
//                throw new RuntimeException(e);
//            }
//        });
//        return http.build();
//    }
//}
