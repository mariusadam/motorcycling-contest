package com.ubb.mpp.server.crud;

import com.ubb.mpp.model.User;
import com.ubb.mpp.persistence.Repository;
import com.ubb.mpp.persistence.RepositoryException;
import com.ubb.mpp.persistence.UserRepository;
import com.ubb.mpp.server.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Marius Adam
 */
@Service
public class UserService extends BaseCrudService<Integer, User> {
    private final static int SALT_SIZE = 32;
    private UserRepository repository;
    private Random random;

    @Autowired
    public UserService(UserRepository repository, EntityValidator validator) {
        super(validator);
        this.repository = repository;
        this.random = new SecureRandom();
    }

    public User update(User u) throws RepositoryException {
        validator.validate(u);
        repository.update(u);
        return u;
    }

    public String encodePassword(String plainPassword, String salt) {
        String generatedPassword;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(plainPassword.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return generatedPassword;
    }

    public String getRandomSalt() {
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return new String(salt);
    }

    public User getUser(String email, String plainPassword) throws RepositoryException {
        User user = this.repository.findByEmail(email);

        user.eraseCredentials();
        String hashedPass = encodePassword(plainPassword, user.getSalt());
        return hashedPass.equals(user.getPassword()) ?
                user : null;
    }

    @Override
    protected Repository<Integer, User> getRepository() {
        return repository;
    }
}
