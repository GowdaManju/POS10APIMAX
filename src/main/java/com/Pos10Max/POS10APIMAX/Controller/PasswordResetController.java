package com.Pos10Max.POS10APIMAX.Controller;

import com.Pos10Max.POS10APIMAX.ServiceImpl.PsswordResetServiceImpl;
import com.Pos10Max.POS10APIMAX.UiRequest.PasswordResetRequest;
import com.Pos10Max.POS10APIMAX.UiResponse.PasswordResetRequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reset")
public class PasswordResetController {

    @Autowired
    PsswordResetServiceImpl PsswordResetService;

@GetMapping("/map")
public String welcome() {
    return "passwordreset";
     }


@PostMapping("/request")
    public PasswordResetRequestResponse resetRequeset(@RequestBody PasswordResetRequest passwordResetRequest)
    {
        PasswordResetRequestResponse returnValue=new PasswordResetRequestResponse();

        if(PsswordResetService.resetRequestSave(passwordResetRequest.getUserName()))
        {
            returnValue.setMsg("Password Reset Link Sent to Your Email :"+passwordResetRequest.getUserName());
        }
        else
        {
            throw new RuntimeException("Password Reset Request Failed Try Again!!!");
        }
        return returnValue;
    }
}
