package com.nhlstenden.student.vigmo.repositories;

import com.nhlstenden.student.vigmo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
