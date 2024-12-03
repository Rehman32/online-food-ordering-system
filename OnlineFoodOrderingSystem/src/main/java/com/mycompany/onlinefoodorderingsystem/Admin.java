
package com.mycompany.onlinefoodorderingsystem;
import java.util.Scanner;
import java.util.List;
import java.io.*;
public class Admin {
    private String adminName;
    private String password;
    
    public Admin(String name,String password){
        this.adminName=name;
        this.password=password;
    }
    
    public static boolean AdminLogin(){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter admin name : ");
        String adminName=input.nextLine();
        System.out.println("Enter admin password : ");
        String password=input.nextLine();
        if(adminName.equals("admin")&& password.equals("admin@123")){
            System.out.println("Admin login Successful");
            return true;
        }
        System.out.println("Invalid Credentials for admin Login");
        return false;
    }
    
    public static void ManageMenu() {
    Scanner input = new Scanner(System.in);
    System.out.println("\n=== Manage Menu ===");
    System.out.println("1. Add Item");
    System.out.println("2. Remove Item");
    System.out.println("3. Change Price");
    System.out.println("4. Go Back");

    int choice = input.nextInt();
    input.nextLine(); 

    switch (choice) {
        case 1:
            System.out.print("Enter Item Name: ");
            String newItemName = input.nextLine();
            System.out.print("Enter Price for " + newItemName + ": ");
            double newItemPrice = input.nextDouble();
            input.nextLine(); 

            String menuItem = newItemName + "," + newItemPrice;
            FileManager.writeToFile("menu.txt", menuItem, true);
            System.out.println(newItemName + " added successfully with price $" + newItemPrice);
            break;

        case 2:
            List<String> menuItems = FileManager.readFromFile("menu.txt");
            if (menuItems.isEmpty()) {
                System.out.println("The menu is currently empty.");
                break;
            }
            System.out.print("Enter the name of the item to remove: ");
            String removeItemName = input.nextLine();

            if (menuItems.removeIf(item -> item.split(",")[0].trim().equals(removeItemName))) {
                FileManager.writeToFile("menu.txt", String.join("\n", menuItems), false);
                System.out.println(removeItemName + " removed successfully.");
            } else {
                System.out.println("Item not found.");
            }
            break;

        case 3:
            updateItemPrice();
            break;

        case 4:
            System.out.println("Going back...");
            return;

        default:
            System.out.println("Invalid choice.");
    }
}
    public static void ManageOrders() {
    Scanner input = new Scanner(System.in);
    while (true) {
        System.out.println("\n=== Manage Orders ===");
        System.out.println("1. View All Orders");
        System.out.println("2. Update Order Status");
        System.out.println("3. Delete Specific Order");
        System.out.println("4. Delete All Orders");
        System.out.println("5. Filter Orders by Status");
        System.out.println("6. Go Back");

        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        input.nextLine(); 

        switch (choice) {
            case 1:
                Order.ViewOrders();
                break;

            case 2:
                Order.updateOrderStatus();
                break;

            case 3:
                deleteSpecificOrder();
                break;

            case 4:
                deleteAllOrders();
                break;

            case 5:
                Order.filterOrdersByStatus();
                break;

            case 6:
                System.out.println("Returning to admin menu...");
                return;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

private static void deleteSpecificOrder() {
    Scanner input = new Scanner(System.in);
    List<String> orders = FileManager.readFromFile("orders.txt");

    if (orders.isEmpty()) {
        System.out.println("No orders to delete.");
        return;
    }

    System.out.print("Enter the Order ID to delete: ");
    String orderId = input.nextLine();

    boolean removed = orders.removeIf(order -> order.split(",")[0].trim().equals(orderId));
    if (removed) {
        FileManager.writeToFile("orders.txt", String.join("\n", orders), false);
        System.out.println("Order " + orderId + " deleted successfully.");
    } else {
        System.out.println("Order ID not found.");
    }
}

private static void deleteAllOrders() {
    FileManager.writeToFile("orders.txt", "", false);
    System.out.println("All orders have been deleted.");
}

    private static void updateItemPrice() {
    Scanner input = new Scanner(System.in);
    List<String> menuItems = FileManager.readFromFile("menu.txt");

    if (menuItems.isEmpty()) {
        System.out.println("The menu is currently empty.");
        return;
    }

    System.out.println("Available Items:");
    for (int i = 0; i < menuItems.size(); i++) {
        System.out.println((i + 1) + ". " + menuItems.get(i));
    }

    System.out.print("Enter the name of the item to update: ");
    String itemName = input.nextLine();
    System.out.print("Enter the new price for " + itemName + ": ");
    double newPrice = input.nextDouble();
    input.nextLine(); 

    boolean updated = false;
    for (int i = 0; i < menuItems.size(); i++) {
        String[] itemDetails = menuItems.get(i).split(",");
        if (itemDetails[0].trim().equals(itemName)) {
            menuItems.set(i, itemName + "," + newPrice);
            updated = true;
            break;
        }
    }

    if (updated) {
        FileManager.writeToFile("menu.txt", String.join("\n", menuItems), false);
        System.out.println(itemName + " price updated to $" + newPrice);
    } else {
        System.out.println("Item not found.");
    }
}
public static void deleteOrders() {
    Scanner input = new Scanner(System.in);
    System.out.println("\n=== Delete Orders ===");
    System.out.println("1. Delete Specific Order");
    System.out.println("2. Delete All Orders");
    System.out.println("3. Go Back");

    int choice = input.nextInt();
    input.nextLine(); 

    List<String> orders = FileManager.readFromFile("orders.txt");

    switch (choice) {
        case 1:
            System.out.print("Enter the Order ID to delete: ");
            String orderId = input.nextLine();
            boolean removed = orders.removeIf(order -> order.split(",")[0].trim().equals(orderId));
            if (removed) {
                FileManager.writeToFile("orders.txt", String.join("\n", orders), false);
                System.out.println("Order " + orderId + " deleted successfully.");
            } else {
                System.out.println("Order ID not found.");
            }
            break;

        case 2:
            FileManager.writeToFile("orders.txt", "", false);
            System.out.println("All orders have been deleted.");
            break;

        case 3:
            System.out.println("Going back...");
            return;

        default:
            System.out.println("Invalid choice.");
    }
}

}
