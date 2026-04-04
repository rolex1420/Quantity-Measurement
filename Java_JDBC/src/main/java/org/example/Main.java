package org.example;

public class Main {
    public static void main(String[] args) {


        DBConnection.getConnection();
        System.out.println("Connected to database successfully!\n");


        DBConnection.createTable("person");


        System.out.println("--- Inserting Persons ---");
        Person p1 = new Person("Alice", "Johnson", 28);
        Person p2 = new Person("Bob", "Smith", 35);
        Person p3 = new Person("Charlie", "Brown", 22);
        DBConnection.insertPerson(p1);
        DBConnection.insertPerson(p2);
        DBConnection.insertPerson(p3);

        // 4. Get all persons
        System.out.println("\n--- All Persons ---");
        for (Person p : DBConnection.getAllPerson()) {
            System.out.println(p);
        }


        System.out.println("\n--- Get Person by ID (1) ---");
        Person found = DBConnection.getPersonById(1);
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("No person found with id 1");
        }


        System.out.println("\n--- Update Person with ID (2) ---");
        Person updated = new Person("Robert", "Smith", 36);
        DBConnection.updatePerson(2, updated);


        System.out.println("\n--- All Persons After Update ---");
        for (Person p : DBConnection.getAllPerson()) {
            System.out.println(p);
        }

        System.out.println("\n--- Delete Person with ID (3) ---");
        DBConnection.deletePersonById(3);

        System.out.println("\n--- All Persons After Deletion ---");
        for (Person p : DBConnection.getAllPerson()) {
            System.out.println(p);
        }

        System.out.println();
        DBConnection.closeConnection();
    }
}