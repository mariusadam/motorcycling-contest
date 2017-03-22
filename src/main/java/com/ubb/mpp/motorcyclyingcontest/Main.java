package com.ubb.mpp.motorcyclyingcontest;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import com.ubb.mpp.motorcyclyingcontest.repository.Repository;
import com.ubb.mpp.motorcyclyingcontest.repository.UserRepository;
import com.ubb.mpp.motorcyclyingcontest.service.validator.EntityValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

/**
 * @author Marius Adam
 */
public class Main {

    public static void main(String [] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");

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
        User found = userRepository.findOneBy("email", u.getEmail());
        Collection<User> all = userRepository.getAll();
        applicationContext.getBean(EntityValidator.class).validate(u);
        applicationContext.getBean(EntityValidator.class).validate(found);
        userRepository.getAll().forEach(System.out::println);
    }
}
