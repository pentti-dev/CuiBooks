package vn.edu.hcmuaf.fit.fahabook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class GraphQLConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return writingBuilder ->
                writingBuilder.scalar(ExtendedScalars.GraphQLBigDecimal).scalar(ExtendedScalars.UUID);
    }
}
