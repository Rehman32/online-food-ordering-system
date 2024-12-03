package com.mycompany.onlinefoodorderingsystem;

import java.util.Scanner;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private Cart userCart;

    public User(String name, String password) {
        this.userName = name;
        this.password = password;
        this.userCart = new Cart();
    }

    public static void registerUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Username:");
        String userName = input.nextLine();
        List<String> users = FileManager.readFromFile("users.txt");
        for (String user : users) {
            if (user.split(",")[0].trim().equalsIgnoreCase(userName)) {
                System.out.println("Username already taken, try another.");
                return;
            }
        }
        System.out.println("Create your Password:");
        String password = input.nextLine();
        String userData = userName + "," + password;
        FileManager.writeToFile("users.txt", userData, true);
        System.out.println("User registered successfully");
    }

    public static User loginUser() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Username:");
        String username = input.nextLine();

        System.out.println("Enter Password:");
        String password = input.nextLine();

        List<String> users = FileManager.readFromFile("users.txt");

        for (String userRecord : users) {
            String[] userDetails = userRecord.split(",");
            if (userDetails.length == 2) {
                String storedUsername = userDetails[0].trim();
                String storedPassword = userDetails[1].trim();

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println("Login successful.");
                    return new User(storedUsername, storedPassword);
                }
            }
        }

        System.out.println("Invalid username or password. Please try again.");
        return null;
    }
    public static void searchMenu() {
    List<String> menuItems = FileManager.readFromFile("menu.txt");
    if (menuItems.isEmpty()) {
        System.out.println("The menu is currently empty.");
        return;
    }

    Scanner input = new Scanner(System.in);
    System.out.print("Enter a keyword to search for an item: ");
    String keyword = input.nextLine().toLowerCase();

    System.out.println("Search Results:");
    boolean found = false;
    for (String item : menuItems) {
        if (item.toLowerCase().contains(keyword)) {
            System.out.println(item);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No matching items found.");
    }
}

    public void viewMenu() {
        List<String> menu = FileManager.readFromFile("menu.txt");
        System.out.println("Available Food Items:");
        for (String menuItem : menu) {
            System.out.println(menuItem);
        }
    }

    public void addItemToCart() {
        userCart.AddItemTOCart();
    }

    public void viewCart() {
        userCart.ViewCart();
    }

    public void removeItemFromCart() {
        userCart.RemoveItemFromCart();
    }

    public void clearCart() {
        userCart.clearCart();
    }

    public void viewOrderStatus() {
        Order.viewOrderStatus(this);
    }

    public void cancelOrder() {
        Order.cancelOrder(this);
    }

    String getUserName() {
        return this.userName;
    }

    Cart getUserCart() {
        return this.userCart;
    }
}
