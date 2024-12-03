
package com.mycompany.onlinefoodorderingsystem;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class Order {
    private String userName;
    private List<String> items;
    private String orderStatus;
     private static int orderNumber ;

    static {
        
        orderNumber = FileManager.readOrderIDFromFile("orderid.txt");
    }
    
    public static void placeOrder(User user) {
    List<String> cartItems = user.getUserCart().getCartItems();
    List<Integer> quantities = user.getUserCart().getQuantities();
    List<String> menuItems = FileManager.readFromFile("menu.txt");

    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty! Please add items before placing an order.");
        return;
    }

    String orderId = "ORD" + orderNumber++;
    double total = user.getUserCart().calculateTotal(menuItems);

    StringBuilder receipt = new StringBuilder();
    receipt.append("Receipt for Order ID: ").append(orderId).append("\n");
    receipt.append("Customer: ").append(user.getUserName()).append("\n");
    receipt.append("Items:\n");

    for (int i = 0; i < cartItems.size(); i++) {
        receipt.append("- ").append(cartItems.get(i))
               .append(" x").append(quantities.get(i)).append("\n");
    }

    receipt.append("Total: $").append(String.format("%.2f", total)).append("\n");
    System.out.println(receipt);

    String orderData = orderId + " , " + user.getUserName() + " , " 
                       + String.join(",", cartItems) + " , Pending";
    FileManager.writeToFile("orders.txt", orderData, true);
    FileManager.writeOrderIDToFile("orderid.txt", orderNumber); // Save updated order ID

    System.out.println("Order placed successfully!");
    user.clearCart();
}


    
    public static void ViewOrders() {
        List<String> orders = FileManager.readFromFile("orders.txt");
        if (orders.isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }

        System.out.println("Order ID | User Name | Items | Status");
        for (String order : orders) {
            System.out.println(order);
        }
    }
    
    public static void updateOrderStatus() {
    Scanner input = new Scanner(System.in);
    List<String> orders = FileManager.readFromFile("orders.txt");
    boolean orderFound = false;

    System.out.print("Enter Order ID to update: ");
    String orderId = input.nextLine();
    System.out.print("Enter new status: ");
    String newStatus = input.nextLine();

    for (int i = 0; i < orders.size(); i++) {
        String[] orderDetails = orders.get(i).split(",");
        if (orderDetails.length >= 4 && orderDetails[0].trim().equals(orderId)) {
            if (newStatus.equalsIgnoreCase("Delivered")) {
                orders.remove(i); // Remove delivered orders
            } else {
                orderDetails[orderDetails.length - 1] = newStatus;
                orders.set(i, String.join(",", orderDetails));
            }
            orderFound = true;
            break;
        }
    }

    if (orderFound) {
        FileManager.writeToFile("orders.txt", String.join("\n", orders), false);
        System.out.println("Order status updated successfully.");
    } else {
        System.out.println("Order ID not found.");
    }
}

    
    public static void viewOrderStatus(User user) {
    List<String> orders = FileManager.readFromFile("orders.txt");
    boolean orderFound = false;

    System.out.println("Your Order Status:");
    for (String order : orders) {
        String[] orderDetails = order.split(",");
        if (orderDetails.length >= 4 && orderDetails[1].trim().equals(user.getUserName())) {
            System.out.println(order);
            orderFound = true;
        }
    }

    if (!orderFound) {
        System.out.println("You have no orders yet.");
    }
}

    public static void cancelOrder(User user) {
    Scanner input = new Scanner(System.in);
    List<String> orders = FileManager.readFromFile("orders.txt");
    boolean orderFound = false;

    System.out.print("Enter Order ID to cancel: ");
    String orderId = input.nextLine();

    for (int i = 0; i < orders.size(); i++) {
        String[] orderDetails = orders.get(i).split(",");
        if (orderDetails.length >= 4 && orderDetails[0].trim().equals(orderId) 
                && orderDetails[1].trim().equals(user.getUserName())) {
            orders.remove(i);
            orderFound = true;
            break;
        }
    }

    if (orderFound) {
        FileManager.writeToFile("orders.txt", String.join("\n", orders), false);
        System.out.println("Order canceled successfully.");
    } else {
        System.out.println("Order ID not found or you are not authorized to cancel this order.");
    }
}

    public static void viewOrderHistory(User user) {
    List<String> orderHistory = FileManager.readFromFile("order_history.txt");
    boolean hasHistory = false;

    System.out.println("Your Order History:");
    for (String record : orderHistory) {
        String[] details = record.split(",");
        if (details.length >= 4 && details[1].trim().equals(user.getUserName())) {
            System.out.println(record);
            hasHistory = true;
        }
    }

    if (!hasHistory) {
        System.out.println("You have no order history.");
    }
}

public static void completeOrder(String orderId) {
    List<String> orders = FileManager.readFromFile("orders.txt");
    List<String> orderHistory = FileManager.readFromFile("order_history.txt");

    boolean orderFound = false;
    for (int i = 0; i < orders.size(); i++) {
        String[] details = orders.get(i).split(",");
        if (details.length >= 4 && details[0].trim().equals(orderId)) {
            orderHistory.add(orders.get(i) + ", Completed");
            orders.remove(i);
            orderFound = true;
            break;
        }
    }

    if (orderFound) {
        FileManager.writeToFile("orders.txt", String.join("\n", orders), false);
        FileManager.writeToFile("order_history.txt", String.join("\n", orderHistory), false);
        System.out.println("Order marked as completed and moved to history.");
    } else {
        System.out.println("Order ID not found.");
    }
}
public static void filterOrdersByStatus() {
    Scanner input = new Scanner(System.in);
    List<String> orders = FileManager.readFromFile("orders.txt");

    if (orders.isEmpty()) {
        System.out.println("No orders available.");
        return;
    }

    System.out.print("Enter status to filter (e.g., Pending, Delivered): ");
    String status = input.nextLine().toLowerCase();

    System.out.println("Filtered Orders:");
    boolean found = false;
    for (String order : orders) {
        String[] details = order.split(",");
        if (details.length >= 4 && details[details.length - 1].trim().toLowerCase().equals(status)) {
            System.out.println(order);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No orders found with status: " + status);
    }
}

    
}
