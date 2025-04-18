package com.gunggeumap.ggm.user.repository;


import com.gunggeumap.ggm.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long id);

}
