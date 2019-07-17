package com.celfocus.training.controller.impl;

import com.celfocus.training.business.IUserBusiness;
import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.FindException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.controller.IUserController;
import com.celfocus.training.controller.dtos.UserDTO;
import com.celfocus.training.model.User;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static com.celfocus.training.util.constant.ConstantNumbers.*;

public class UserControllerImp implements IUserController {

    private IUserBusiness userBusiness;

    public UserControllerImp(IUserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @Override
    public List<UserDTO> getAllUserDTO() {
       List<User> userList = userBusiness.getAllUsers();

        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user: userList){
            UserDTO userDTO = new UserDTO(user.getId(),user.getUsername(),user.getBirthDate(), user.isMajor());
            userDTOList.add(userDTO);
        }

        return userDTOList;

    }

    @Override
    public UserDTO findUserByUsername(UserDTO userDTOFromView) {
        UserDTO userDTO = new UserDTO();

        if (isUserDTOFromViewNull(userDTOFromView)) return userDTO;

        User user = this.userBusiness.findUserByName(userDTOFromView.getUsername());

        if (user == null) {
            return userDTO;
        }

        BeanUtils.copyProperties(user,userDTO);

        return userDTO;
    }

    @Override
    public UserDTO createUser(UserDTO userDTOFromView) throws SaveException {
        UserDTO userDTO = new UserDTO();

        if (isUserDTOFromViewNull(userDTOFromView)) return userDTO;

        User user = this.convertUserDtoToUser(userDTOFromView);

        this.userBusiness.saveUser(user);

        return userDTOFromView;
    }

    @Override
    public boolean deleteUser(UserDTO userDTOFromView) throws DeleteException {
        if (isUserDTOFromViewNull(userDTOFromView)) return false;

        User user = this.convertUserDtoToUser(userDTOFromView);

        return this.userBusiness.deleteUser(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTOFromView) throws SaveException, FindException {
        if (isUserDTOFromViewNull(userDTOFromView)) return false;

        User user = this.convertUserDtoToUser(userDTOFromView);

        this.userBusiness.updateUser(user);

        return true;
    }

    private User convertUserDtoToUser(UserDTO userDTOFromView) {
        User user = new User(ID_TO_DEFAULT);

        BeanUtils.copyProperties(userDTOFromView,user);
        return user;
    }

    private boolean isUserDTOFromViewNull(UserDTO userDTO) {
        return userDTO == null || userDTO.getUsername() == null;
    }
}
