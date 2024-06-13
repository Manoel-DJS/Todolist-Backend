package backend.web.todolist.service;

import backend.web.todolist.controller.dto.CreateUserDto;
import backend.web.todolist.entities.User;
import backend.web.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
