package ca.serum390.godzilla.experimental;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ComponentBoi.class)
public class ConfigBoi {

    @Bean
    public BigBoi theBiggestBoiOfAll() {
        return BigBoi.builder()
                     .firstName("Jeff")
                     .lastName("Johnson")
                     .id(123)
                     .build();
    }
}
