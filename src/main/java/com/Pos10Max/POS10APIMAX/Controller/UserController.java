package com.Pos10Max.POS10APIMAX.Controller;

import com.Pos10Max.POS10APIMAX.UiRequest.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.Pos10Max.POS10APIMAX.DTO.UserDto;
import com.Pos10Max.POS10APIMAX.Exceptions.UserServiceException;
import com.Pos10Max.POS10APIMAX.Service.UserService;
import com.Pos10Max.POS10APIMAX.Shared.JwtUtil;
import com.Pos10Max.POS10APIMAX.Shared.Utils;
import com.Pos10Max.POS10APIMAX.UiRequest.AuthenticationRequest;
import com.Pos10Max.POS10APIMAX.UiRequest.UserRequest;
import com.Pos10Max.POS10APIMAX.UiResponse.*;

import java.io.File;
import java.io.FileOutputStream;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Utils utils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
                 consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public UserResponse createUser(@RequestBody UserRequest userRequest) throws UnknownServiceException {
//        System.out.println(userRequest.getUsertype());
//        System.out.println(userRequest.getUsername());
//        System.out.println(userRequest.getPassword());

        if(userRequest.getPassword()==null || userRequest.getUsername()==null || userRequest.getUsertype()==null) {
            throw new RuntimeException("Please Fill All the Details!!");
        }
        UserDto dto=userService.save(utils.getMapper().map(userRequest,UserDto.class));

        UserResponse returnValue=utils.getMapper().map(dto, UserResponse.class);
        returnValue.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),"self"));
//        ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString()
        return returnValue;
    }

    @GetMapping(path="/{userid}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public UserResponse getUserByUserId(@PathVariable("userid") String userID)
    {
        System.out.println("Gte User By UserId called");
        UserDto userDto=userService.getUserByUserId(userID);
        UserResponse returnValue=utils.getMapper().map(userDto,UserResponse.class);
        returnValue.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),"self"));
        return returnValue;
    }

    @GetMapping(path="/name/{username}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public UserResponse getUserByUserName(@PathVariable("username") String userName)
    {
        UserDto userDto=userService.getUserByName(userName);
        UserResponse returnValue=utils.getMapper().map(userDto,UserResponse.class);
        returnValue.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),"self"));

        return returnValue;
    }


//    @PostMapping(path="/login",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE})
//    public LoginSuccessResponse userLogin(@RequestBody UserLoginRequest userLoginRequest)
//    {
//        UserDto userDto=userService.userLogin(userLoginRequest.getUsername(),userLoginRequest.getPassword());
//
//        UserResponse returnValue=utils.getMapper().map(userDto,UserResponse.class);
//
//        LoginSuccessResponse output=utils.getMapper().map(returnValue,LoginSuccessResponse.class);
//        output.setMessage("Login Success");
//
//        Link self = linkTo(methodOn(ItemController.class).getItemByName(returnValue.getUsername())).withRel("Login link");
//        output.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),"self"));
//        return output;
//    }

    @PostMapping(path="/authentication",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest authenticationRequest)
    {
//        UserDto userDto=userService.userLogin(userLoginRequest.getUsername(),userLoginRequest.getPassword());
//
//        UserResponse returnValue=utils.getMapper().map(userDto,UserResponse.class);
//
//        LoginSuccessResponse output=utils.getMapper().map(returnValue,LoginSuccessResponse.class);
//        output.setMessage("Login Success");
//
//        Link self = linkTo(methodOn(ItemController.class).getItemByName(returnValue.getUsername())).withRel("Login link");
//        output.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),"self"));
//        return output;
//    return null;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }catch(BadCredentialsException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Incorrect Username or Password");
        }
        final UserDto userDetails=userService.getUserByName(authenticationRequest.getUsername());
        final String jwt= jwtUtil.generateToken(userDetails);

        return  ResponseEntity.ok(new AuthenticationResponse(jwt,userDetails.getUserId()));
    }






    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public List<UserResponse> Allusers()
    {
        List<UserResponse> returnValue=new ArrayList<UserResponse>();
        List<UserDto> userlist=userService.AllUsers();

        for(UserDto user:userlist) {
            UserResponse u = utils.getMapper().map(user, UserResponse.class);
            Link namelink = linkTo(methodOn(UserController.class).getUserByUserName(u.getUsername())).withRel("by userName");
            Link userIdlink = linkTo(methodOn(UserController.class).getUserByUserId(u.getUserId())).withRel("by UserId");
            u.add(namelink,userIdlink);
            returnValue.add(u);
        }
        return returnValue;
    }

    @DeleteMapping(path = "/{userId}")
    public OperationalStatusModel deleteUserByUserId(@PathVariable("userId") String userId)
    {
        if(userService.getUserByUserId(userId)==null)
            throw new UserServiceException("Invalid User Id");
        OperationalStatusModel returnValue = new OperationalStatusModel();
        if(userService.deleteUserByUserId(userId))
        {
            returnValue.setOperationType(RequestOperationName.DELETE.name());
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        else
        {
            returnValue.setOperationType(RequestOperationName.DELETE.name());
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        return returnValue;
    }
//    @DeleteMapping()
//    @RequestMapping("/name/{username}")
//    public OperationalStatusModel deleteUserByName(@PathVariable("username") String userName)
//    {
//        if(userService.getUserByName(userName)==null)
//            throw new UserServiceException("Invalid User Name");
//        OperationalStatusModel returnValue = new OperationalStatusModel();
//        if(userService.deleteUserByName(userName))
//        {
//            returnValue.setOperationType(RequestOperationName.DELETE.name());
//            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
//        }
//        else
//        {
//            returnValue.setOperationType(RequestOperationName.DELETE.name());
//            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
//        }
//        return returnValue;
//    }


    @PutMapping(path="/{userId}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest) throws UnknownServiceException {

        if(userRequest.getUserId()==null || userRequest.getPassword()==null || userRequest.getUsername()==null || userRequest.getUsertype()==null ||userRequest.getLast()==null) {
            throw new RuntimeException("Please Fill All the Details!!");
        }

        UserDto dto=userService.getUserByUserId(userId);
        dto.setUsername(userRequest.getUsername());
        dto.setUsertype(userRequest.getUsertype());
        dto.setPassword(userRequest.getPassword());
        dto.setLast(userRequest.getLast());

        UserDto returnValue=userService.updateUser(dto);

    return utils.getMapper().map(returnValue,UserResponse.class);
    }

    @PostMapping(value="/login")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        System.out.println("A");
        try {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

}catch(BadCredentialsException e)
{
    e.printStackTrace();
    throw new Exception("Incorrect Username or Password");
}
final UserDto userDetails=userService.getUserByName(authenticationRequest.getUsername());
final String jwt= jwtUtil.generateToken(userDetails);

return  ResponseEntity.ok(new AuthenticationResponse(jwt,userDetails.getUserId()));
    }


    @PostMapping(path="/{id}/profileimg",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> uploadSingleFileExample1(@PathVariable("id") long id, @RequestParam("file") MultipartFile partfile) {
//        log.info("Request contains, File: " + file.getOriginalFilename());
        // Add your processing logic heres
        File f=null;
        try {
            System.out.println(partfile.getName());
            f = new File("images/"+ new Date().getTime()+".png");
            System.out.println(f.getAbsolutePath());
            f.createNewFile();
//
            System.out.println(f.getAbsolutePath());
            FileOutputStream fileOut=new FileOutputStream(f);
            fileOut.write(partfile.getBytes());

//            byte[] base64Image = Base64.getEncoder().encode(partfile.getBytes());
////            System.out.println(new String(base64Image));   // Outputs "SGVsbG8="
//            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(new String(base64Image));
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
//            BufferedImage image = ImageIO.read(bis);
//            bis.close();
//            File outputfile = new File("mgdog.png");
//            ImageIO.write(image, "png", outputfile);




//            byte[] decoded = Base64.getDecoder().decode(encoded);
//            System.out.println(new String(decoded));

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok(f.getPath());

    }


}
