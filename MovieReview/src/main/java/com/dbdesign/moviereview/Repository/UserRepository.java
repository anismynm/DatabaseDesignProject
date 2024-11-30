package com.dbdesign.moviereview.Repository;

import com.dbdesign.moviereview.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
