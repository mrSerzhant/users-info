package by.serzhant.usersinfo.service.validator;

import by.serzhant.usersinfo.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    public boolean isNumber(String inputValue) {
        if (inputValue.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(inputValue);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isValidPhoneNumber(String inputValue) {
        final String CONST_PHONE_CODE = "375";

        if (inputValue.isEmpty()) {
            return false;
        }

        for (int i = 0; i < inputValue.length(); i++) {
            try {
                Integer.parseInt(String.valueOf(inputValue.charAt(i)));
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        return inputValue.startsWith(CONST_PHONE_CODE) && isValidPhoneNumberSize(inputValue);
    }

    private boolean isValidPhoneNumberSize(String inputValue) {
        final int SIZE_PHONE_NUMBER = 12;
        int countElement = 0;

        for (int i = 0; i < inputValue.length(); i++) {
            countElement++;
        }

        return countElement == SIZE_PHONE_NUMBER;
    }

    public boolean isValidEmail(String inputValue) {
        if (inputValue.isEmpty()) {
            return false;
        }

        if (!inputValue.startsWith("@") && !inputValue.endsWith("@") && inputValue.contains("@")) {
            return inputValue.substring(inputValue.length() - 4).contains(".");
        }
        return false;
    }

    public boolean isValidRole(List<Role> rolesList, Role inputRole, String position) {
        if (inputRole.getGrade() == 3 && rolesList.size() > 1) {
            return false;
        }

        List<Role> tempRolesList = new ArrayList<>(rolesList);
        tempRolesList.set(Integer.parseInt(position) - 1, inputRole);

        if (tempRolesList.size() == 2) {
            return tempRolesList.get(0).getGrade() != tempRolesList.get(1).getGrade();

        }

        return true;
    }

    public boolean isValidRole(List<Role> rolesList, Role inputRole) {
        if (inputRole.getGrade() == 3 && rolesList.size() > 1) {
            return false;
        }

        for (Role role : rolesList) {
            if (role.getGrade() == 3) {
                return false;
            }
        }

        List<Role> tempRolesList = new ArrayList<>(rolesList);
        tempRolesList.add(inputRole);

        return tempRolesList.get(0).getGrade() != tempRolesList.get(1).getGrade();
    }
}
