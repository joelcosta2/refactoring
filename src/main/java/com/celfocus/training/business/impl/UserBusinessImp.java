package com.celfocus.training.business.impl;


import com.celfocus.training.business.exception.FindException;
import com.celfocus.training.model.User;
import com.celfocus.training.business.IUserBusiness;
import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.SaveException;

import java.util.List;


public class UserBusinessImp extends AbstractOperations<User> implements IUserBusiness {


    @Override
    public User findUserByName(String username) {

        for (User userIteration : super.getAll()) {
            if (userIteration.getUsername().equals(username)) {
                return userIteration;
            }
        }

        return null;
    }

    @Override
    public void saveUser(User user) throws SaveException {
        if (existingUser(user)) throw new SaveException("This username already exist.");

        user.setId(super.getNextIndex());

        super.save(user);
    }

    @Override
    public boolean updateUser(User userWithChange) throws FindException {
        if (!this.existingUsername(userWithChange.getUsername())) throw new FindException("Don´t exist the user.");

        User userFound = this.findUserByName(userWithChange.getUsername());
        int userIndex = super.getIndex(userFound);

        super.update(userIndex, userWithChange);

        return true;
    }

    @Override
    public boolean deleteUser(User user) throws DeleteException {
        if (!existingUser(user)) throw new DeleteException("User don´t exist.");

        int userIndex = super.getIndex(user);

        super.delete(userIndex);

        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return super.getAll();
    }

    @Override
    public boolean existingUser(User user) {
        return super.getAll().contains(user) || existingUsername(user.getUsername());
    }

    private boolean existingUsername(String username) {
        return super.getAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

}
