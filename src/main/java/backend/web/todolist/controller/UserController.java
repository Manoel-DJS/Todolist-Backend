package backend.web.todolist.controller;

import backend.web.todolist.controller.dto.CreateTaskDto;
import backend.web.todolist.controller.dto.CreateUserDto;
import backend.web.todolist.controller.dto.UpdateTaskDto;
import backend.web.todolist.entities.Task;
import backend.web.todolist.entities.User;
import backend.web.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto){
        var userId = userService.createUser(dto);
        return ResponseEntity.created(URI.create("/v1/users" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId){
        var user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }


}
