package Program2;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final CatalogImpl catalog = new CatalogImpl();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedSampleData(); 
        boolean running = true;
        while (running) {
            printMenu();
            try {
                System.out.print("Choose option: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1: addProductCLI(); break;
                    case 2: removeProductCLI(); break;
                    case 3: searchByIdCLI(); break;
                    case 4: searchByNameCLI(); break;
                    case 5: listAllCLI(); break;
                    case 6: listSortedCLI(); break;
                    case 0: running = false; break;
                    default: System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter numeric option.");
            }
        }
        System.out.println("Exiting Product Catalog. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n=== Product Catalog Menu ===");
        System.out.println("1. Add product");
        System.out.println("2. Remove product by id");
        System.out.println("3. Search product by id");
        System.out.println("4. Search products by name");
        System.out.println("5. List all products");
        System.out.println("6. List sorted products");
        System.out.println("0. Exit");
    }

    private static void addProductCLI() {
        try {
            System.out.print("Enter id (int >0): ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter category: ");
            String category = sc.nextLine().trim();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Enter stock quantity (int): ");
            int stock = Integer.parseInt(sc.nextLine().trim());

            Product p = new Product(id, name, category, price, stock);
            catalog.addProduct(p);
            System.out.println("Product added successfully: " + p);
        } catch (DuplicateProductException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input. Aborting add.");
        }
    }

    private static void removeProductCLI() {
        try {
            System.out.print("Enter product id to remove: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            catalog.removeProduct(id);
            System.out.println("Product removed (id=" + id + ")");
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id input.");
        }
    }

    private static void searchByIdCLI() {
        try {
            System.out.print("Enter product id to search: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            Product p = catalog.searchById(id);
            System.out.println("Found: " + p);
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id input.");
        }
    }

    private static void searchByNameCLI() {
        System.out.print("Enter name keyword: ");
        String key = sc.nextLine().trim();
        List<Product> list = catalog.searchByName(key);
        if (list.isEmpty()) System.out.println("No products match: " + key);
        else {
            System.out.println("Matches:");
            for (Product p : list) System.out.println("  " + p);
        }
    }

    private static void listAllCLI() {
        List<Product> list = catalog.listAll();
        if (list.isEmpty()) System.out.println("Catalog empty.");
        else {
            System.out.println("All products:");
            for (Product p : list) System.out.println("  " + p);
        }
    }

    private static void listSortedCLI() {
        System.out.println("Sort by: 1) Price asc 2) Price desc 3) Name");
        try {
            int opt = Integer.parseInt(sc.nextLine().trim());
            List<Product> list;
            if (opt == 1) list = catalog.listSortedByPrice(true);
            else if (opt == 2) list = catalog.listSortedByPrice(false);
            else list = catalog.listSortedByName();

            System.out.println("Sorted list:");
            for (Product p : list) System.out.println("  " + p);
        } catch (NumberFormatException e) {
            System.out.println("Invalid option.");
        }
    }

    // seed some products to demonstrate
    private static void seedSampleData() {
        try {
            catalog.addProduct(new Product(1, "Wireless Mouse", "Electronics", 599.0, 25));
            catalog.addProduct(new Product(2, "USB-C Cable", "Accessories", 199.0, 100));
            catalog.addProduct(new Product(3, "Bluetooth Speaker", "Electronics", 2499.0, 10));
        } catch (Exception e) {
            
        }
    }
}