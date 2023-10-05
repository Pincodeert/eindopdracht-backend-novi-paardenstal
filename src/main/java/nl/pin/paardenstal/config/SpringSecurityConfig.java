package nl.pin.paardenstal.config;

import nl.pin.paardenstal.filter.JwtRequestFilter;
import nl.pin.paardenstal.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    private final PasswordEncoder passwordEncoder;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter,
                                PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }


    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
                //.requestMatchers("/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                //moet USER-role dit ook kunnen opvragen???
                .antMatchers(HttpMethod.GET,"/users/{username}").hasAnyRole("USER","ADMIN")
                //deze toevoegen?
                .antMatchers(HttpMethod.GET,"/users/{username}/authorities").hasRole("ADMIN")
                //kloppen deze rollen?
                .antMatchers(HttpMethod.PUT,"/users/{username}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")


                .antMatchers(HttpMethod.POST, "/customerprofiles").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/customerprofiles").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/customerprofiles/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/customerprofiles/{id}/enrollments").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/customerprofiles/enrollments/{customerProfileId}").hasAnyRole("USER","ADMIN")
                //keuze maken: put of patch
                .antMatchers(HttpMethod.PUT, "/customerprofiles/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PATCH, "/customerprofiles/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/customerprofiles/{id}/user").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/customerprofiles/{id}/users/{username}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/customerprofiles/{id}").hasRole("ADMIN")


                .antMatchers(HttpMethod.POST, "/stalls").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/stalls").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/stalls/{id}").hasAnyRole("USER","ADMIN")
                //hieronder permitAll gekozen zodat iedereen vanuit de frontend kan zien hoeveel lege en dus beschikbare
                // stallen er nog zijn
                .antMatchers(HttpMethod.GET, "/stalls/isOccupied/{isOccupied}").permitAll()
                .antMatchers(HttpMethod.GET, "/stalls/type/{type}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/stalls/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/stalls/{id}/horse").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/stalls/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/stalls/{id}").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/horses").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/horses").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/horses/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/horses/customerprofile").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/horses/{id}/passport").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PATCH, "/horses/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT, "/horses/{id}/customerprofile").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/horses/{id}").hasRole("ADMIN")


                .antMatchers(HttpMethod.POST, "/subscriptions").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/subscriptions").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/subscriptions/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/subscriptions/{id}/enrollments").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/subscriptions/enrollments/{subscriptionId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/subscriptions/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/subscriptions/{id}").hasRole("ADMIN")

                //via dit path gebruik te maken van 2 constructors:
                .antMatchers(HttpMethod.POST, "/enrollments").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/enrollments").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/enrollments/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PATCH, "/enrollments/{id}").hasAnyRole("USER","ADMIN")

                .antMatchers(HttpMethod.DELETE, "/enrollments/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/enrollments/{id}").hasRole("ADMIN")


                .antMatchers(HttpMethod.POST, "/upload").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET, "/download/{fileName}").hasAnyRole("USER","ADMIN")


                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
