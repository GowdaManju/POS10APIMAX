package com.Pos10Max.POS10APIMAX.Repository;

import com.Pos10Max.POS10APIMAX.Entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {


    @Autowired
    private UserRepository underTest;

    @Test
    void getUserByName() {
        //given
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername("mn3118110@gmail.com");
        userEntity.setPassword("Mn3118110");
        userEntity.setUserId("Ahggysbehui28ghdj");
        userEntity.setUsertype("user");
        userEntity.setLast(new Date());



        underTest.save(userEntity);

        //when
       UserEntity us= underTest.getUserByName(userEntity.getUsername());

        //then
        assertThat(us).isEqualTo(userEntity);


    }
    @Test
    void testUserNameNotExist() {
        //given
        String userName="mg@gmail.com";

        //when
        UserEntity us= underTest.getUserByName(userName);

        //then
        assertThat(us).isEqualTo(null);
    }



    @Test
    void getUserList() {
        //given
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername("mn3118110@gmail.com");
        userEntity.setPassword("Mn3118110");
        userEntity.setUserId("Ahggysbehui28ghdj");
        userEntity.setUsertype("user");
        userEntity.setLast(new Date());


        underTest.save(userEntity);

        //when
        List<UserEntity> us= underTest.findAll();

        //then
        assertThat(us).contains(userEntity);
    }

    @Test
    void deleteUserByUserName() {
        //given
        UserEntity userEntity=new UserEntity();
        userEntity.setUsername("mn3118110@gmail.com");
        userEntity.setPassword("Mn3118110");
        userEntity.setUserId("Ahggysbehui28ghdj");
        userEntity.setUsertype("user");
        userEntity.setLast(new Date());
        underTest.save(userEntity);

        //when
        underTest.deleteUserByName(userEntity.getUsername());
        UserEntity us=underTest.getUserByName(userEntity.getUsername());

        //then
        assertThat(us).isEqualTo(null);
    }

//    @Test
//    void checkEmailexist() {
//        //given
//        String email="mn3118110@gmail.com";
//
//        //when
//
//        boolean expected=underTest.checkEmailExist(email);
//
//        //then
//        assertThat(expected).isFalse();
//    }
}