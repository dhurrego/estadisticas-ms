package co.com.sofka.sb.listener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new CustomMessageConverter();
    }
}
