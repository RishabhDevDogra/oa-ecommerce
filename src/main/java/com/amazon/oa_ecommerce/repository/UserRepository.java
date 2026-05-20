package com.amazon.oa_ecommerce.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.amazon.oa_ecommerce.model.User;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(counter.incrementAndGet());
        }
        users.add(user);
        return user;
    }
}