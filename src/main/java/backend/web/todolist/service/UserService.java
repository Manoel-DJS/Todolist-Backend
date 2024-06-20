package backend.web.todolist.service;

import backend.web.todolist.controller.dto.CreateTaskDto;
import backend.web.todolist.controller.dto.CreateUserDto;
import backend.web.todolist.entities.Task;
import backend.web.todolist.entities.User;
import backend.web.todolist.repository.TaskRepository;
import backend.web.todolist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private UserRepository userRepository;

    private TaskRepository taskRepository;

    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    public UUID createUser(CreateUserDto createUserDto){

        // DTO -> Entity
        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null
        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserid();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void createTask(String userId, CreateTaskDto createTaskDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // DTO -> entity

        // Consertar entrada id
        var taskUser = new Task(
                // createTaskDto.id(), // Vai entrar o número automaticamente
                user, // pronto
                createTaskDto.titleTask(),
                createTaskDto.description(),
                null,
                false
        );

        var taskCreated =taskRepository.save(taskUser);

    }

    public void deleteTaskUserById(String userId, Long taskId){

        var userExists = userRepository.findById(UUID.fromString(userId));
        var taskExists = taskId;


        // DTO --> Entity

        var taskDelete = new Task(
                taskExists,
                userExists.get()
        );

        if(userExists.isPresent()){
            taskRepository.delete(taskDelete);
        }
    }
}
