package com.gj.miclrn.flyway;

import java.util.Arrays;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class FlywayConfig {

    @Bean
    @DependsOn("entityManagerFactory") 
    public CommandLineRunner flywayDmlRunner(DataSource dataSource) {
        return args -> {
            Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migrations/dml/H2")
//                .table("flyway_dml_history")
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .load();
            
            MigrationInfoService info = flyway.info();
            if (info.pending().length > 0) {
                System.out.println("Applying DML migrations:");
                Arrays.stream(info.pending()).forEach(m -> 
                    System.out.println("-> " + m.getVersion() + " - " + m.getDescription()));
                flyway.migrate();
            } else {
                System.out.println("No pending DML migrations found");
            }
        };
    }
}
