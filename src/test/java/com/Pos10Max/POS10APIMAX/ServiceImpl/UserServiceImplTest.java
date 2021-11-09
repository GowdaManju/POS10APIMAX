package com.Pos10Max.POS10APIMAX.ServiceImpl;

import com.Pos10Max.POS10APIMAX.Repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private AutoCloseable autoCloseabl;

    private  UserServiceImpl underTest;

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseabl.close();
//    }

//    @BeforeEach
//    void setup()
//    {
//        autoCloseabl= MockitoAnnotations.openMocks(this);
//        underTest=new UserServiceImpl();
//    }

//    @Test
//    void canGetAllUser()
//    {
//        //when
//        underTest.AllUsers();
//        //then
//        verify(userRepository.findAll());
//
//
//    }


    @Test
    void loadUserByUsername() {

    }

}