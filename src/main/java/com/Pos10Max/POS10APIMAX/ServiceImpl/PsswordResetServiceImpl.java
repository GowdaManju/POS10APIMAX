package com.Pos10Max.POS10APIMAX.ServiceImpl;

import com.Pos10Max.POS10APIMAX.Shared.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Pos10Max.POS10APIMAX.Repository.PasswordResetRepository;
import com.Pos10Max.POS10APIMAX.Repository.UserRepository;
import com.Pos10Max.POS10APIMAX.Service.PasswordService;
import com.Pos10Max.POS10APIMAX.Shared.JwtUtil;
import com.Pos10Max.POS10APIMAX.Shared.MailService;

@Service
public class PsswordResetServiceImpl implements PasswordService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordResetRepository passwordResetRepository;

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean resetRequestSave(String userName) {
        return false;
    }

//    @Override
//    public boolean resetRequestSave(String userName)
//    {
////        System.out.println("MANJU");
////        passwordResetRepository.deleteByUsername(userName);
//
////        System.out.println("MANJU");
////        PasswordResetEntity entity=new PasswordResetEntity();
////        entity.setUserName(userName);
////        Map<String,Object>  map=new HashMap<String,Object>();
////        map.put("userName",userName);
////        String resetToken=jwtUtil.passwordResetToken(map,"passwordreset");
////        entity.setResetToken(resetToken);
////
////
////        if(passwordResetRepository.save(entity)!=null)
////        {
////
////            if(mailService.sendPasswordResetRequest("User",userName,resetToken))
////            {
////                return true;
////            }
////        }
//        return false;
//    }


}
