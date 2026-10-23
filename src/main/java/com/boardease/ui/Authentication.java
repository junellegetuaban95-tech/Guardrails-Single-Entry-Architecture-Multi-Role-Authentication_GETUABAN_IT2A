package com.boardease.ui;

import com.boardease.dao.UserDao;
import com.boardease.model.User;
import java.util.Scanner;

public class Authentication {
    private final UserDao userDao;
    private final Scanner scanner;

    public Authentication(UserDao userDao, Scanner scanner) {
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("   Welcome to BoardEase Rental System   ");
            System.out.println("========================================");
            System.out.println("[1] Login");
            System.out.println("[2] Register New Account");
            System.out.println("[3] Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> handleRegistration();
                case "3" -> {
                    System.out.println("Exiting BoardEase. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDao.login(username, password);
        if (user == null) {
            System.out.println("❌ Invalid username or password.");
            return;
        }

        if ("PENDING".equalsIgnoreCase(user.status())) {
            System.out.println("⏳ Your account status is PENDING approval by an administrator.");
            return;
        }

        System.out.println("✅ Login successful! Welcome, " + user.fullName() + " (" + user.role() + ")");
        routeUserMenu(user);
    }

    private void handleRegistration() {
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.println("Select Role:");
        System.out.println("[1] ADMIN");
        System.out.println("[2] STAFF");
        System.out.println("[3] TENANT");
        System.out.print("Choose role number: ");
        String roleChoice = scanner.nextLine();

        String role = switch (roleChoice) {
            case "1" -> "ADMIN";
            case "2" -> "STAFF";
            case "3" -> "TENANT";
            default -> {
                System.out.println("Invalid role choice. Defaulting to TENANT.");
                yield "TENANT";
            }
        };

        boolean success = userDao.registerUser(username, password, role, fullName);
        if (success) {
            System.out.println("🎉 Registration successful! Your account has been created with a PENDING status.");
        } else {
            System.out.println("❌ Registration failed. Username may already exist.");
        }
    }

    private void routeUserMenu(User user) {
        switch (user.role().toUpperCase()) {
            case "ADMIN" -> adminMenu();
            case "STAFF" -> staffMenu();
            case "TENANT" -> tenantMenu();
            default -> System.out.println("Unknown system role assigned.");
        }
    }

    private void adminMenu() {
        System.out.println("\n--- ADMIN CONSOLE MENU ---");
        System.out.println("[1] Manage Rooms & Tenants (Placeholder)");
        System.out.println("[2] Logout");
        System.out.print("Select action: ");
        scanner.nextLine();
    }

    private void staffMenu() {
        System.out.println("\n--- STAFF CONSOLE MENU ---");
        System.out.println("[1] Record Payments / View Rooms (Placeholder)");
        System.out.println("[2] Logout");
        System.out.print("Select action: ");
        scanner.nextLine();
    }

    private void tenantMenu() {
        System.out.println("\n--- TENANT CONSOLE MENU ---");
        System.out.println("[1] View My Balance & Payment History (Placeholder)");
        System.out.println("[2] Logout");
        System.out.print("Select action: ");
        scanner.nextLine();
    }
}