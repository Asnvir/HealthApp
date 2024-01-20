package superapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import superapp.service.UserService;

@RestController
@RequestMapping(path = "superapp/users")
public class UserController {

    @Autowired
    private UserService userService;


}
