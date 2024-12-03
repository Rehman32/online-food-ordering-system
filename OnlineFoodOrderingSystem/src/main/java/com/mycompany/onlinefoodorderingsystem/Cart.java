
package com.mycompany.onlinefoodorderingsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Cart {
    private List<String> cartItems;
private List<Integer> quantities;

    public Cart() {
        this.cartItems = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public void AddItemTOCart() {
        List<String> menuItems = FileManager.readFromFile("menu.txt");
        if (menuItems.isEmpty()) {
            System.out.println("The menu is currently empty. Please try again later.");
            return;
        }

        System.out.println("Available Items (Item, Price):");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ". " + menuItems.get(i));
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Select an item by its number: ");
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine(); 

            if (choice > 0 && choice <= menuItems.size()) {
                String selectedItem = menuItems.get(choice - 1).split(",")[0].trim();
                System.out.print("Enter quantity: ");
                if (input.hasNextInt()) {
                    int quantity = input.nextInt();
                    input.nextLine(); 

                    cartItems.add(selectedItem);
                    quantities.add(quantity);
                    System.out.println(quantity + "x " + selectedItem + " added to your cart.");
                } else {
                    System.out.println("Invalid quantity. Item not added.");
                }
            } else {
                System.out.println("Invalid choice. Please select a valid item number.");
            }
        } else {
            System.out.println("Please enter a valid number.");
            input.nextLine(); 
        }
    }

    public void ViewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your Cart:");
            for (int i = 0; i < cartItems.size(); i++) {
                System.out.println("- " + quantities.get(i) + "x " + cartItems.get(i));
            }
        }
    }

    public double calculateTotal(List<String> menuItems) {
        double total = 0.0;
        for (int i = 0; i < cartItems.size(); i++) {
            for (String menuItem : menuItems) {
                String[] details = menuItem.split(",");
                if (details.length >= 2 && details[0].trim().equals(cartItems.get(i))) {
                    double price = Double.parseDouble(details[1].trim());
                    total += price * quantities.get(i);
                }
            }
        }
        return total;
    }

    public void RemoveItemFromCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Nothing to remove.");
            return;
        }

        System.out.println("Items in your cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println((i + 1) + ". " + cartItems.get(i));
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Select an item to remove by its number: ");
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine(); 

            if (choice > 0 && choice <= cartItems.size()) {
                String removedItem = cartItems.remove(choice - 1);
                System.out.println(removedItem + " removed from your cart.");
            } else {
                System.out.println("Invalid choice. Please select a valid item number.");
            }
        } else {
            System.out.println("Please enter a valid number.");
            input.nextLine(); // clear invalid input
        }
    }

   public List<String> getCartItems() {
        return cartItems;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void clearCart() {
        cartItems.clear();
        quantities.clear();
        System.out.println("Cart cleared.");
    }
}
