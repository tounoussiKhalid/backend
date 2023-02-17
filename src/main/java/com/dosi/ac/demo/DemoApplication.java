package com.dosi.ac.demo;

import com.dosi.ac.demo.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class DemoApplication implements RepositoryRestConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(User.class);
    }
/*
    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

        System.out.println("\n1.findAll()...");
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }
        System.out.println("Done!");
    }
*/
}
