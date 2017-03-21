package com.ubb.mpp.motorcyclyingcontest;

import com.ubb.mpp.motorcyclyingcontest.config.DIConfiguration;
import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.Repository;
import com.ubb.mpp.motorcyclyingcontest.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

/**
 * @author Marius Adam
 */
public class Main {

    public static void main(String [] args) throws Exception {
        String basePackage = Main.class.getPackage().getName();
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackage);
        applicationContext.register(DIConfiguration.class);

        Repository<Integer, User> userRepository = applicationContext.getBean(UserRepository.class);

        Random random = new SecureRandom();

        User u = new User();
        u.setFirstName("fname");
        u.setLastName("lname");
        u.setEmail(random.nextInt() + "aw@asdasdas.com");
        u.setPassword("this should be some hash");
        u.setSalt("a very random salt");
        u.setIsActive(true);
        u.setLoggedIn(false);

        userRepository.insert(u);

        Collection<User> all = userRepository.getAll();

        userRepository.getAll().forEach(System.out::println);
    }
}
