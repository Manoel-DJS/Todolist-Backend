package backend.web.todolist.controller;

import backend.web.todolist.controller.dto.CreateTaskDto;
import backend.web.todolist.controller.dto.UpdateTaskDto;
import backend.web.todolist.entities.User;
import backend.web.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/")
public class TaskController {
    private UserService userService;

    public TaskController(UserService userService) {
        this.userService = userService;
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

    // Alterar Task Pelo ID
    @PutMapping("/{userId}/tasks")
    public ResponseEntity<Void> updateTaskUserById(@PathVariable("userId") String userId, Long taskId,
                                                   @RequestBody UpdateTaskDto updateTaskDto){
        userService.updateTaskUserById(userId,taskId, updateTaskDto);
        return ResponseEntity.noContent().build();
    }

    // Deletar Task Pelo ID
    @DeleteMapping("/{userId}/tasks")
    public ResponseEntity<Void> deleteTaskUserById(@PathVariable("userId")
                                                   String userId, Long taskId){
        userService.deleteTaskUserById(userId,taskId);
        return ResponseEntity.noContent().build();
    }

}
