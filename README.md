# Pharma Security - Spring Boot TP2

Securing a pharmacy management web application with Spring Boot,
Spring Security, Thymeleaf and MySQL.

## Technologies
- Spring Boot, Spring Security 6+, Spring Data JPA
- Thymeleaf and thymeleaf-extras-springsecurity6
- MySQL (XAMPP / phpMyAdmin)

## Roles and Access
| Username | Password | Role | Access |
|----------|----------|------|--------|
| admin | adminPW | ADMIN | Everything |
| pharma | pharmaPW | PHARMACIEN (pharmacist) | Medications (add, edit), sales |
| caisse | caissePW | CAISSIER (cashier) | Sales only |

## Features
- Form-based authentication, passwords hashed with BCrypt
- Role-based authorization (SecurityConfig)
- Users loaded from the database (CustomUserDetailsService)
- Initial data seeding (InitData)
- Medication management (CRUD) and sales registration
- Custom "Access Denied" page
