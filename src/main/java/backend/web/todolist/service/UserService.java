package backend.web.todolist.service;

import backend.web.todolist.controller.dto.CreateTaskDto;
import backend.web.todolist.controller.dto.CreateUserDto;
import backend.web.todolist.controller.dto.UpdateTaskDto;
import backend.web.todolist.entities.Task;
import backend.web.todolist.entities.User;
import backend.web.todolist.exception.BadRequestException;
import backend.web.todolist.exception.InvalidUUIDException;
import backend.web.todolist.repository.TaskRepository;
import backend.web.todolist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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

    // Optional<User>
    public User getUserById(String userId){
        if (validateUserById(userId)) {
            System.out.println("UUID válido");
            return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new BadRequestException("ID não encontrado"));
        } else {
            throw new InvalidUUIDException("A string fornecida não é um UUID válido: " + userId);
        }

        // return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new BadRequestException("ID não encontrado"));
    }

    // Validador de UUID
    public boolean validateUserById(String userId){
        var ValidId = userId;
        if(ValidId == null || ValidId.length() != 36){
            return false;
        } else{
            return true;
        }
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void createTask(String userId, CreateTaskDto createTaskDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new BadRequestException("ID não encontrado"));

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

    public void updateTaskUserById(String userId, Long taskId,UpdateTaskDto updateTaskDto){

        var userEntity = userRepository.findById(UUID.fromString(userId));
        var taskEntity = taskRepository.findById(taskId);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(taskEntity.isPresent()){
                var newUserTask = taskEntity.get();

                if(updateTaskDto.titleTask() != null){
                    newUserTask.setTitleTask(updateTaskDto.titleTask());
                }
                if (updateTaskDto.description() != null){
                    newUserTask.setDescription(updateTaskDto.description());
                }

                taskRepository.save(newUserTask);
            }

        }



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
