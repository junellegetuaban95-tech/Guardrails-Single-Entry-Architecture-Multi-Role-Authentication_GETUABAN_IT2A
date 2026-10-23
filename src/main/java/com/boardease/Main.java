package com.boardease;

import com.boardease.dao.DatabaseInitializer;
import com.boardease.dao.UserDao;
import com.boardease.ui.Authentication;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Database & Tables automatically
        DatabaseInitializer.initializeDatabase();

        // 2. Instantiate DAOs & Shared Scanner Utilities
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);

        // 3. Delegate execution to Authentication UI handler
        Authentication auth = new Authentication(userDao, scanner);
        auth.start();
    }
}