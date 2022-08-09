package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Роман", "Цаголов", (byte) 32);
        service.saveUser("Иван", "Иванов", (byte) 44);
        service.saveUser("Петр", "Петров", (byte) 18);
        service.saveUser("Игорь", "Игоревич", (byte) 60);
        service.getAllUsers();
        service.cleanUsersTable();
        //service.dropUsersTable();
    }
}
