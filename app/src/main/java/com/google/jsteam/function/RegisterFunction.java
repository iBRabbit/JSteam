package com.google.jsteam.function;

public class RegisterFunction {
    GlobalFunction globalFunc = new GlobalFunction();
    public Boolean isRegisterFormInputValid(String username, String password, String region, String phoneNumber, String email) {
        return (
                !username.isEmpty() &&
                        !password.isEmpty() &&
                        !region.isEmpty() &&
                        !phoneNumber.isEmpty() &&
                        email.endsWith(".com") &&
                        password.length() >= 5 &&
                        globalFunc.isStringAlphaNumeric(username) &&
                        globalFunc.isStringAlphaNumeric(password)
        );
    }

    public String getRegisterFormErrorMessage(String username, String password, String region, String phoneNumber, String email) {

        if(username.isEmpty())
            return "Username cannot be empty";

        if(password.isEmpty())
            return "Password cannot be empty";

        if(region.isEmpty())
            return "Region cannot be empty";

        if(phoneNumber.isEmpty())
            return "Phone number cannot be empty";

        if(email.isEmpty())
            return "Email cannot be empty";

        if(!email.endsWith(".com"))
            return "Email must end with .com";

        if(!globalFunc.isStringAlphaNumeric(username))
            return "Username must be alphanumeric";

        if(password.length() < 5)
            return "Password must be at least 5 characters long";

        if(!globalFunc.isStringAlphaNumeric(password))
            return "Password must be alphanumeric";

        return null;
    }
}
