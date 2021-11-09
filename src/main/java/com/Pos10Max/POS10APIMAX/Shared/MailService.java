package com.Pos10Max.POS10APIMAX.Shared;

import org.springframework.stereotype.Component;

@Component
public class MailService {

    final String FROM="johncenaphone26@gmail.com";

    final String SUBJECT="One last step to complete your registration with MG API";

    final String PASSWORD_RESET_SUBJECT="Password reset Request";

    final String HTMLBODY="<html><body><h1> Please verify your email address</h1>"
            +"<p>Thank you for registration with our API. To Complete registration "+
            "click on the following link:"
            +"<a href='http://localHost:8080/verification-service/email-verification.html?token=$tokenValue'>"
            +"Finala step to complete your registration</p>"
            +"</a><br/><br/></body></html>"
            +"Thank you! And we are waiting for you inside!";


    final String TEXTBODY="Please verify your email address"
            +"Thank you for registration with our API. To Complete registration "+
            "click on the following link:"
            +"http://localHost:8080/verification-service/email-verification.html?token=$tokenValue"
            +"Finala step to complete your registration"
            +"Thank you! And we are waiting for you inside!";




    String PASSWORD_RESET_HTMLBODY="<html><body><h1> request to reset your Password</h1>"
            +"<p>Hill, $firestName!</p>"
            +"<p> Someone has requested to reset your password with our project. If it"
            + "ere you Click the link below to reset password"
            +"<a href=http://localhoset:8080/verification-service/password-reset.html?token=$tokenValue"
            +"Click this link to reset Password"
            +"</a><br/><br/></body></html>"
            +"thank you!";



//    public void verifyEmail(UserDto userDto)
//    {
//        try
//        {
//
//            String body=HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
//
//            new EmailSenderService().sendSimpleEmail(userDto.getEmail(), body,SUBJECT);
//        }catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//    }


    public boolean sendPasswordResetRequest(String firstName,String email,String token)
    {
        boolean returnValue=false;
        try
        {

            PASSWORD_RESET_HTMLBODY=PASSWORD_RESET_HTMLBODY.replace("$firestName",firstName);

            PASSWORD_RESET_HTMLBODY=PASSWORD_RESET_HTMLBODY.replace("$tokenValue",token);

            new EmailSenderService().sendSimpleEmail(email, PASSWORD_RESET_HTMLBODY, PASSWORD_RESET_SUBJECT);
            returnValue=true;

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return returnValue;
        }

        return returnValue;
    }
}
