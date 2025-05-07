package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    public static final Integer USER_ID = 1;
    public static final Integer ADMIN_ID = 2;

    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    {
        save(new User(USER_ID, "User", "user@gmail.com", "user", Role.USER));
        save(new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return users.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (getByEmail(user.getEmail()) != null) {
            user.setId(id.incrementAndGet());
            users.put(user.getId(), user);
            return user;
        }
        return users.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return users.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing((User::getEmail)))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
