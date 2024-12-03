
package com.mycompany.onlinefoodorderingsystem;
import java.util.Scanner;

public class OnlineFoodOrderingSystem {

    public static void displayAdminMenu() {
    
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Manage Menu");
            System.out.println("2. Manage Orders");
            System.out.println("3. Search Menu");
            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");
            Scanner input = new Scanner(System.in);
            int adminChoice = input.nextInt();

            switch (adminChoice) {
                case 1:
                    Admin.ManageMenu();
                    break;
                case 2:
                    Admin.ManageOrders();
                    break;
                case 3:
                   User.searchMenu();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    
}


    public static void displayUserMenu(User user) {
    Scanner input = new Scanner(System.in);
    while (true) {
        System.out.println("\n=== User Menu ===");
        System.out.println("1. View Menu");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Place Order");
        System.out.println("5. View Order Status");
        System.out.println("6. View Order History");
        System.out.println("7. Logout");

        System.out.print("Enter your choice: ");
        int userChoice = input.nextInt();

        switch (userChoice) {
            case 1:
                user.viewMenu();
                break;

            case 2:
                user.addItemToCart();
                break;

            case 3:
                user.viewCart();
                break;

            case 4:
                Order.placeOrder(user);
                break;

            case 5:
                user.viewOrderStatus();
                break;

            case 6:
                Order.viewOrderHistory(user);
                break;

            case 7:
                System.out.println("Logging out...");
                return;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

    public static void AdminFlow() {
        if (Admin.AdminLogin()) {
            displayAdminMenu();
        } else {
            System.out.println("Admin login failed. Please try again.");
        }
    }

    public static void UserFlow() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== User Flow ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please enter your choice: ");

            if (input.hasNextInt()) {
                int userChoice = input.nextInt();

                switch (userChoice) {
                    case 1:
                        User.registerUser();
                        break;
                    case 2:
                        User loggedInUser = User.loginUser();
                        if (loggedInUser != null) {
                            displayUserMenu(loggedInUser);
                        } else {
                            System.out.println("Login failed. Please try again.");
                        }
                        break;
                    case 3:
                        System.out.println("Exiting the system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Please enter a number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== Welcome to Online Food Ordering System ===");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Please select your role: ");

            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine(); 

                switch (choice) {
                    case 1:
                        AdminFlow();
                        break;
                    case 2:
                        UserFlow();
                        break;
                    case 3:
                        System.out.println("Thank you for using our system. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } else {
                System.out.println("Please enter a number.");
                input.nextLine(); 
            }
        }
    }
}
