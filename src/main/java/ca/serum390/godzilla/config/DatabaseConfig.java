package ca.serum390.godzilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig {

    /**
     * Creates a {@link DatabaseClient DatabaseClient Bean} that can be injected
     * anywhere in the program
     */
    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                             .connectionFactory(connectionFactory)
                             .namedParameters(true)
                             .build();
    }

    @Bean
    public ConnectionFactory connectionFactory(Environment env) {

        String host = env.getProperty("DB_HOST");
        int port = extractPortNumber(env, "DB_PORT");
        String username = env.getProperty("DB_USERNAME");
        String password = env.getProperty("DB_PASSWORD");

        host = host != null ? host : "localhost";
        username = username != null ? username : "admin";
        password = password != null ? password : "admin123";

        log.info("\nCONNECTING TO DATABASE - HOST = " + host + ", PORT = " + port);

        return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                                             .host(host)
                                             .port(port)
                                             .database("godzilla")
                                             .username(username)
                                             .password(password)
                                             .build()
        );
    }

    /**
     * If the database is empty, this {@link Bean} will populate the tables and test
     * data.
     *
     * @param connectionFactory The {@link ConnectionFactory ConnectionFactory Bean}
     *                          injected by Spring.
     * @return A {@link ConnectionFactoryInitializer} Bean that Spring will use to
     *         initialize the {@link ConnectionFactory}
     */
    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(
                new ResourceDatabasePopulator(new ClassPathResource("sql/schema.pgsql")),
                new ResourceDatabasePopulator(new ClassPathResource("sql/data.pgsql")));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    private static Integer extractPortNumber(Environment env, String property) {
        try {
            String prop = env.getProperty(property);
            return prop != null ? Integer.parseInt(prop) : 5432;
        } catch (NumberFormatException e) {
            log.error("Could not parse port number from environment variable DB_PORT: ", e);
            return 5432;
        }
    }
}
