package ca.serum390.godzilla.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

import ca.serum390.godzilla.util.JsonToMapConverter;
import ca.serum390.godzilla.util.MapToJsonConverter;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

/**
 * ENV VARS:
 *
 * <ol>
 * <li><code>DB_HOST:</code> The IP address or hostname of your DB></li>
 * <li><code>DB_PORT:</code> The port that your DB is listening on></li>
 * <li><code>DB_USERNAME:</code> The username for an account with access to the
 * <code>godzilla</code> database</li>
 * <li><code>DB_PASSWORD:</code> The password of the account</li>
 * </ol>
 */
@Log4j2
@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig {

    private final ObjectMapper objectMapper;
    private Environment environment;
    private boolean skipPopulatingDatabase;
    private static boolean databaseInitialized = false;

    DatabaseConfig(ObjectMapper mapper, Environment env) {
        this.objectMapper = mapper;
        this.environment = env;
        skipPopulatingDatabase = Boolean.parseBoolean(
                environment.getProperty("SKIP_POPULATING_DATABASE"));
    }

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
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new JsonToMapConverter(objectMapper));
        converters.add(new MapToJsonConverter(objectMapper));
        return new R2dbcCustomConversions(converters);
    }

    @Bean
    public ConnectionFactory connectionFactory() {

        String host = environment.getProperty("DB_HOST");
        int port = extractPortNumber("DB_PORT");
        String username = environment.getProperty("DB_USERNAME");
        String password = environment.getProperty("DB_PASSWORD");

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
     * initialize the {@link ConnectionFactory}
     */
    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        if (flipDatabaseInitializedFlag() || skipPopulatingDatabase) {
            return initializer;
        }

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(
                new ResourceDatabasePopulator(new ClassPathResource("sql/schema.pgsql")),
                new ResourceDatabasePopulator(new ClassPathResource("sql/data.pgsql")));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    private Integer extractPortNumber(String property) {
        try {
            String prop = environment.getProperty(property);
            return prop != null ? Integer.parseInt(prop) : 5432;
        } catch (NumberFormatException e) {
            log.error("Could not parse port number from environment variable DB_PORT: ", e);
            return 5432;
        }
    }

    private static boolean flipDatabaseInitializedFlag() {
        boolean oldValue = databaseInitialized;
        databaseInitialized = true;
        return oldValue;
    }
}
