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
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    // Criar task pelo usuário
    @PostMapping("/{userId}/tasks")
    public ResponseEntity<User> createTaskUser(@PathVariable("userId")
                                               String userId,
                                               @RequestBody CreateTaskDto createTaskDto
                                               ){

        userService.createTask(userId, createTaskDto);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/{userId}/tasks")
    public ResponseEntity<Void> updateTaskUserById(@PathVariable("userId") String userId, Long taskId,
                                                   @RequestBody UpdateTaskDto updateTaskDto){
        userService.updateTaskUserById(userId,taskId, updateTaskDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/tasks")
    public ResponseEntity<Void> deleteTaskUserById(@PathVariable("userId")
                                                   String userId, Long taskId){
        userService.deleteTaskUserById(userId,taskId);
        return ResponseEntity.noContent().build();
    }




}
