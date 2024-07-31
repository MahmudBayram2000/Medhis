package Example.example.repository;

import Example.example.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "CALL create_user(:username, :password, :email)", nativeQuery = true)
    void createUser(@Param("username") String username,
                    @Param("password") String password,
                    @Param("email") String email);



    @Modifying
    @Transactional
    @Query(value = "CALL delete_user(:id)", nativeQuery = true)
    void deleteUser(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "CALL update_user(:id, :username, :password, :email)", nativeQuery = true)
    void updateUser(@Param("id") Long id,
                    @Param("username") String username,
                    @Param("password") String password,
                    @Param("email") String email);

    @Query(value = "CALL get_user_by_credentials(:username, :password)", nativeQuery = true)
    Optional<User> getUserByCredentials(@Param("username") String username,
                                        @Param("password") String password);

    @Query(value = "CALL get_user_by_username(:username)", nativeQuery = true)
    Optional<User> getUserByUsername(@Param("username") String username);
}

