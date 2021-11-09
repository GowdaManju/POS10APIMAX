package com.Pos10Max.POS10APIMAX.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.Pos10Max.POS10APIMAX.DTO.UserDto;
import com.Pos10Max.POS10APIMAX.Entity.UserEntity;
import com.Pos10Max.POS10APIMAX.Exceptions.UserServiceException;
import com.Pos10Max.POS10APIMAX.Repository.UserRepository;
import com.Pos10Max.POS10APIMAX.Service.UserService;
import com.Pos10Max.POS10APIMAX.Shared.Utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto dto=getUserByName(username);
        return new User(dto.getUsername(),dto.getPassword(),new ArrayList<>());
    }


    @Override
    public UserDto save(UserDto userDto) throws UnknownServiceException {
        UserDto returnValue=new UserDto();

        //checking if User Already exist
        if(userRepository.getUserByName(userDto.getUsername())!=null)
            throw new UnknownServiceException("User Already Exist");

        UserEntity entity=utils.getMapper().map(userDto,UserEntity.class);
        entity.setUserId(utils.generateUserId(10));
//        entity.setLast(new Date());

        //saving the user
        returnValue=utils.getMapper().map(userRepository.save(entity),UserDto.class);
        if(returnValue==null)
            throw new UserServiceException("Failed to Create User Try Again!!");

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userID) {
        System.out.println("Gte User By UserId called");
        UserDto returnvalue=utils.getMapper().map(userRepository.getUserByUserId(userID),UserDto.class);
        if(returnvalue==null)
            throw new UserServiceException("Invalid UserID");
        return returnvalue;
    }

    @Override
    public UserDto getUserByName(String username) {
        UserDto returnValue=utils.getMapper().map(userRepository.getUserByName(username),UserDto.class);
        if(returnValue==null)
            throw new UserServiceException("Invalid UserName");
        return returnValue;
    }

    @Override
    public boolean deleteUserByUserId(String userId) {
        if(userRepository.getUserByUserId(userId)==null)
            throw new UserServiceException("Invalid UserID");

        userRepository.deleteUserByUserId(userId);
        if(userRepository.getUserByUserId(userId)!=null)
            throw new UserServiceException("Failed to delete user try again");

        return true;
    }

    @Override
    public boolean deleteUserByName(String name) {
        if(userRepository.getUserByName(name)==null)
            throw new UserServiceException("Invalid User Name");

        userRepository.deleteUserByName(name);
        if(userRepository.getUserByName(name)!=null)
            throw new UserServiceException("Failed to delete user try again");
        return true;
    }

    @Override
    public UserDto userLogin(String username, String password) {
        UserEntity entity=userRepository.login(username,password);
        if(entity==null)
            throw new UserServiceException("Invalid UserName or Password!!");

        return utils.getMapper().map(entity,UserDto.class);

    }

    @Override
    public List<UserDto> AllUsers() {
List<UserEntity> entites=userRepository.allUsers();
List<UserDto> returnValue=new ArrayList<UserDto>();
for(UserEntity entity:entites)
{
    returnValue.add(utils.getMapper().map(entity,UserDto.class));
}
        return returnValue;
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws UnknownServiceException {

        UserEntity userEntity=userRepository.getUserByUserId(userDto.getUserId());
        if(userEntity==null)
            throw new UserServiceException("Invalid UserID");

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setUsertype(userDto.getUsertype());
        userEntity.setLast(userDto.getLast());

        UserEntity savedUser=userRepository.save(userEntity);
        if(savedUser==null)
            throw new UserServiceException("Failed to Update user try again");
        return utils.getMapper().map(savedUser,UserDto.class);
    }

    private void sendmail() throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tutorialspoint@gmail.com", "<your password>");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("tutorialspoint@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tutorialspoint@gmail.com"));
        msg.setSubject("Tutorials point email");
        msg.setContent("Tutorials point email", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Tutorials point email", "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        attachPart.attachFile("/var/tmp/image19.png");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }


}
