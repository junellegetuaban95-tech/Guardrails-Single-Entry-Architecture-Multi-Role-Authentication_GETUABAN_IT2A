package com.boardease.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String[] createTableSqls = {
                """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL,
                full_name TEXT NOT NULL,
                status TEXT NOT NULL
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS rooms (
                room_id INTEGER PRIMARY KEY AUTOINCREMENT,
                room_number TEXT UNIQUE NOT NULL,
                room_type TEXT NOT NULL,
                capacity INTEGER NOT NULL,
                monthly_rate REAL NOT NULL,
                status TEXT NOT NULL
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS tenants (
                tenant_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                name TEXT NOT NULL,
                contact_number TEXT NOT NULL,
                address TEXT NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(user_id)
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS rentals (
                rental_id INTEGER PRIMARY KEY AUTOINCREMENT,
                tenant_id INTEGER NOT NULL,
                room_id INTEGER NOT NULL,
                start_date TIMESTAMP NOT NULL,
                end_date TIMESTAMP,
                monthly_rent REAL NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (tenant_id) REFERENCES tenants(tenant_id),
                FOREIGN KEY (room_id) REFERENCES rooms(room_id)
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS payments (
                payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                rental_id INTEGER NOT NULL,
                amount_paid REAL NOT NULL,
                payment_date TIMESTAMP NOT NULL,
                billing_month TEXT NOT NULL,
                receipt_number TEXT NOT NULL,
                FOREIGN KEY (rental_id) REFERENCES rentals(rental_id)
            );
            """,
                """
            CREATE TABLE IF NOT EXISTS activity_logs (
                log_id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                action TEXT NOT NULL,
                table_affected TEXT NOT NULL,
                description TEXT NOT NULL,
                log_timestamp TIMESTAMP NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(user_id)
            );
            """
        };

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            for (String sql : createTableSqls) {
                stmt.execute(sql);
            }
            System.out.println("[DatabaseInitializer] All database tables verified/created successfully.");
        } catch (SQLException e) {
            System.err.println("[DatabaseInitializer Error] Failed to initialize database: " + e.getMessage());
        }
    }
}