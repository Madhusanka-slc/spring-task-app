package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.UserDTO;

import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
//    public void createUserAccount(@Valid @RequestBody UserDTO user, Errors errors){
    public void createUserAccount(@Validated(ValidationGroups.Create.class) @RequestBody UserDTO user){
        System.out.println(user);
        userService.createNewUserAccount(user);
//        Optional<FieldError> firstError = errors.getFieldErrors().stream().findFirst();
//        if (firstError.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,firstError.get().getDefaultMessage());
//        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/me",consumes = "application/json")
    public void updateUserAccountDetails(@Validated(ValidationGroups.Update.class) @RequestBody UserDTO user,
                                         @AuthenticationPrincipal(expression = "username") String username){
        System.out.println(user);
        user.setUsername(username);
        userService.updateUserAccountDetails(user);


    }

    @GetMapping(value = "/me",produces = "application/json")
    public UserDTO getUserAccountDetails(@AuthenticationPrincipal(expression = "username") String username){
        return userService.getUserAccountDetails(username);
//        System.out.println("getUserAccountDetails()");
//        return new UserDTO();

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me")
    public void deleteUserAccount(@AuthenticationPrincipal(expression = "username") String username){
        System.out.println("deleteUserAccount()");
        userService.deleteUserAccount(username);

    }
}
