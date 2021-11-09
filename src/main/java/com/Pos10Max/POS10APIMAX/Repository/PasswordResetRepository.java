package com.Pos10Max.POS10APIMAX.Repository;

import com.Pos10Max.POS10APIMAX.Entity.PasswordResetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity,Long> {


//    @Query(value = "select * from resetpassword where token=:token",nativeQuery = true)
//    public PasswordResetEntity getUserNameByToken(@Param("token") String token);
//
//    @Query(value = "select * from resetpassword where userName=:userName",nativeQuery = true)
//    public PasswordResetEntity getUserNameByUserName(@Param("userName") String userName);

//    @Modifying
//    @Transactional
//    @Query(value="delete from resetpassword where token=:token",nativeQuery = true)
//    public void deleteByToken(@Param("token") String token);

//    @Modifying
//    @Transactional
//    @Query(value="delete from resetpassword where username=:username",nativeQuery = true)
//    public void deleteByUsername(@Param("username") String userName);


//    @Transactional
//    @Modifying
//    @Query(value="Delete from resetpassword where userName= :username",nativeQuery = true)
//    void deleteByUsername(@Param("username") String username);


}
