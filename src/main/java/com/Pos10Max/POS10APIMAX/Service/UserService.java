package com.Pos10Max.POS10APIMAX.Service;

import com.Pos10Max.POS10APIMAX.DTO.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.net.UnknownServiceException;
import java.util.List;

public interface UserService extends UserDetailsService {

UserDto save(UserDto userDto) throws UnknownServiceException;
UserDto getUserByUserId(String userID);
UserDto getUserByName(String name);
boolean deleteUserByUserId(String userId);
boolean deleteUserByName(String name);

UserDto userLogin(String username, String password);

List<UserDto> AllUsers();
UserDto updateUser(UserDto userDto)throws UnknownServiceException;

}
