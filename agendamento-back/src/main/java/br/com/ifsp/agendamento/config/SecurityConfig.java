package br.com.ifsp.agendamento.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Configuration
    public class ModelMapperConfig {

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

}