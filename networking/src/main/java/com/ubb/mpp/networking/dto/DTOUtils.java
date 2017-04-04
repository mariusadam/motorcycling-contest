package com.ubb.mpp.networking.dto;

import com.ubb.mpp.model.User;

/**
 * @author Marius Adam
 */
public class DTOUtils {
    public static User getFromDTO(UserDTO usdto) {
        User user = new User();
        user.setId(usdto.getId());
        //TODO add all the setters
        return user;
    }

    public static UserDTO getDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getSalt()
        );
    }

    public static UserDTO[] getDTO(User[] users) {
        UserDTO[] frDTO = new UserDTO[users.length];
        for (int i = 0; i < users.length; i++)
            frDTO[i] = getDTO(users[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users) {
        User[] friends = new User[users.length];
        for (int i = 0; i < users.length; i++) {
            friends[i] = getFromDTO(users[i]);
        }
        return friends;
    }
}
