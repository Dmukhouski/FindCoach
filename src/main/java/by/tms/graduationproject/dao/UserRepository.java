package by.tms.graduationproject.dao;

import by.tms.graduationproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByMainActivity(String mainActivity);

    @Query(value = "select u from User u where u.active =:active")
    List<User> findAllActiveUsers(boolean active);
}
