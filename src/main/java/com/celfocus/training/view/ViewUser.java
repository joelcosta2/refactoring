package com.celfocus.training.view;

import com.celfocus.training.business.exception.DeleteException;
import com.celfocus.training.business.exception.FindException;
import com.celfocus.training.business.exception.SaveException;
import com.celfocus.training.controller.IUserController;
import com.celfocus.training.controller.dtos.UserDTO;
import com.celfocus.training.util.Utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.celfocus.training.util.constant.ConstantNumbers.SIXTY_FIVE_YEAROLD;


public class ViewUser {

    public static final String SPAN = "<span>";
    private IUserController userController;


    private static final Logger LOGGER = Logger.getLogger( "ViewUser" );

    public ViewUser(IUserController userController) {
        this.userController = userController;
    }

    public void createUser(String username, String year, String month, String day) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);
        userDTO.setBirthDate(Utils.parseToDate(year, month, day));

        int userAge = Utils.getAgeFromDate(userDTO.getBirthDate());
        boolean haveLess65YearsOld = userAge > SIXTY_FIVE_YEAROLD;

        userDTO.setMajor(haveLess65YearsOld);

        this.saveUserDto(userDTO);
    }

    private void saveUserDto(UserDTO userDTO) {
        try {

            this.userController.createUser(userDTO);

        } catch (SaveException e) {
            e.printStackTrace();
        }
    }

    public UserDTO getUserDTO(String username) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);

        return userController.findUserByUsername(userDTO);
    }

    public void printUsers(){
        List<UserDTO> userDTOList = userController.getAllUserDTO();

        LOGGER.log(Level.FINE ,"All Users");
        LOGGER.log(Level.FINE ,"----------------");

        for (UserDTO userDTO : userDTOList) {
            LOGGER.log(Level.FINE ,userDTO.toString());
        }

        LOGGER.log(Level.FINE ,"----------------");
    }

    private String buildFileUserXml(UserDTO userDTO) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>")
                .append("<productName>").append(userDTO.getUsername()).append("<productName>")
                .append("<birthDate>").append(userDTO.getBirthDate().toString()).append("<birthDate>")
                .append("<older>").append(userDTO.isMajor()).append("<older>");

        return stringBuilder.toString();
    }

    private String buildFileUserHtml(UserDTO userDTO) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<div>")
                .append("<h1>User</h1>")
                .append(SPAN).append(userDTO.getUsername()).append(SPAN)
                .append(SPAN).append(userDTO.getBirthDate().toString()).append(SPAN)
                .append(SPAN).append(userDTO.isMajor()).append(SPAN)
                .append("</div>");

        return stringBuilder.toString();
    }

    public void updateUser(UserDTO userDTO) {
        try {
            userController.updateUser(userDTO);
        } catch (SaveException e) {
            e.printStackTrace();
        } catch (FindException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(UserDTO userDTO) {
        try {
            userController.deleteUser(userDTO);
        } catch (DeleteException e) {
            e.printStackTrace();
        }
    }


}
