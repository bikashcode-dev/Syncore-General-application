package com.SynCore.Syncore.repository;

import com.SynCore.Syncore.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Object> {
     User findByUsername(String username);

    void deleteByUsername(String name);
}
