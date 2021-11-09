package com.Pos10Max.POS10APIMAX.Repository;

import com.Pos10Max.POS10APIMAX.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>, CrudRepository<UserEntity,Long> {


    @Transactional
    @Query(value = "select * from user where username= :name",nativeQuery = true)
    UserEntity getUserByName(String name);

    @Transactional
    @Query(value = "select * from user where userid= :userid",nativeQuery = true)
    UserEntity getUserByUserId(String userid);

    @Transactional
    @Query(value = "select * from user where username= :username and password=:password",nativeQuery = true)
    UserEntity login(String username, String password);

    @Transactional
    @Query(value = "select * from user",nativeQuery = true)
    List<UserEntity> allUsers();

    @Transactional
    @Modifying
    @Query(value="Delete from user where username= :username",nativeQuery = true)
    void deleteUserByName(String username);



    @Transactional
    @Modifying
    @Query(value = "Delete from user where userid= :userId",nativeQuery = true)
    void deleteUserByUserId(String userId);

//    @Transactional
////    @Query("Select case when count(s)>0 then true else false end from user u where u.username = ?1")
//    Boolean checkEmailExist(String email);


}
