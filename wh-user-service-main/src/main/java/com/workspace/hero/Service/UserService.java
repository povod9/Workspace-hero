package com.workspace.hero.Service;

import com.workspace.hero.Entity.LoginRequest;
import com.workspace.hero.Entity.Manager;
import com.workspace.hero.Entity.User;
import com.workspace.hero.Entity.UserEntity;
import com.workspace.hero.Entity.UserDto;
import com.workspace.hero.Entity.enums.Role;
import com.workspace.hero.Repository.UserRepository;
import com.workspace.hero.Security.JwtCore;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtCore jwtCore;

    public UserService(PasswordEncoder passwordEncoder, UserRepository repository, JwtCore jwtCore) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.jwtCore = jwtCore;
    }

    @PreAuthorize("hasRole('MANAGER')")
    public List<UserDto> getAllUser() {

        List<UserEntity> allUser = repository.findAll();

        return allUser.stream()
                .map(this::toDto)
                .toList();
    }

    @PreAuthorize("hasRole('MANAGER')")
    public UserDto getById(Long id) {

        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user by that id=" + id));

        return toDto(userEntity);
    }

    public void createUser(
            User userToCreate
    ) {

        if (userToCreate.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }

        if(repository.existsByEmail(userToCreate.email())){
            throw new IllegalArgumentException("That email already registered in system");
        }

        var createdEntity = new UserEntity(
                null,
                userToCreate.firstName(),
                userToCreate.lastName(),
                userToCreate.email(),
                passwordEncoder.encode(userToCreate.password()),
                BigDecimal.ZERO,
                Role.USER
        );

        repository.save(createdEntity);


    }

    public void createManager(
            Manager managerToCreate
    )
    {
        if (managerToCreate.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }

        if(repository.existsByEmail(managerToCreate.email())){
            throw new IllegalArgumentException("That email already registered in system");
        }

        var createdEntity = new UserEntity(
                null,
                managerToCreate.firstName(),
                managerToCreate.lastName(),
                managerToCreate.email(),
                passwordEncoder.encode(managerToCreate.password()),
                BigDecimal.ZERO,
                Role.MANAGER
        );

        repository.save(createdEntity);
    }



    public String login(LoginRequest loginRequest) {

        UserEntity user = repository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("Cannot found by email"));

        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            String token = jwtCore.generateToken(user);
            return token;
        }else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public UserDto topUp(
            Long id,
            BigDecimal amount
    )
    {
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userEntity.setBalance(userEntity.getBalance().add(amount));
        repository.save(userEntity);

        return toDto(userEntity);
    }

    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public UserDto deductBalance(
            Long id,
            BigDecimal amount
    )
    {
        UserEntity userEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user by id=" + id));

        if(userEntity.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Not enough money for deduction");
        }

        userEntity.setBalance(userEntity.getBalance().subtract(amount));


        return toDto(repository.save(userEntity));
    }

    private UserDto toDto (
            UserEntity user
    )
    {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBalance(),
                user.getRole()
        );
    }


}
