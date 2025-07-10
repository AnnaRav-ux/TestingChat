package com.survey.service;

import com.survey.model.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users;
    private User currentUser;

    public AuthService() {
        this.users = new HashMap<>();
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        // Добавляем тестовых пользователей
        users.put("student", new User("student", "password", "student"));
        users.put("admin", new User("admin", "admin123", "admin"));
        users.put("ivanov", new User("ivanov", "123456", "student"));
        users.put("petrov", new User("petrov", "654321", "student"));
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.getRole());
    }
}