import java.util.*;
import java.util.Scanner;

public class OnlineShopping {

	public static void main(String[] args) {
		List<Product> products = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		List<User> users = new ArrayList<>();
		List<Product> cart = new ArrayList<>();

		products.add(new Product(1, "Headphones", 49.99, "Wireless"));
		products.add(new Product(2, "Laptop", 799.99, "Portable"));
		products.add(new Product(3, "Sneakers", 89.99, "Athletic"));
		products.add(new Product(4, "Smartwatch", 149.99, "Fitness"));
		products.add(new Product(5, "Backpack", 39.99, "Travel"));
		products.add(new Product(6, "Speaker", 69.99, "Bluetooth"));
		products.add(new Product(7, "Camera", 299.99, "Compact"));
		products.add(new Product(8, "Gaming Mouse", 49.99, "Ergonomic"));
		products.add(new Product(9, "Smartphone", 599.99, "Flagship"));
		products.add(new Product(10, "Coffee Maker", 79.99, "Programmable"));

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Online Shopping Center!");

		while (true) {
			System.out.println("\nSelect an option:");
			System.out.println("1. Customer");
			System.out.println("2. Admin");
			System.out.println("3. Exit");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				customerMenu(users, products,cart, orders, scanner);
				break;
			case 2:
				adminMenu(products, scanner);
				break;
			case 3:
				System.out.println("Thank you for using the Online Shopping Center. Goodbye!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please select a valid option.");
			}
		}
	}

	public static void customerMenu(List<User> users, List<Product> products, List<Product> cart, List<Order> orders, Scanner scanner) {
		System.out.println("\nCustomer Menu:");
		System.out.println("1. Log In");
		System.out.println("2. Sign Up");
		System.out.println("3. Back to Main Menu");
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			User loggedInUser = logIn(users, scanner);
			if (loggedInUser instanceof User) {
				customerSubMenu((User) loggedInUser, products, cart, orders, scanner);
			}
			break;
		case 2:
			signUp(users, scanner);
			break;
		case 3:
			return;
		default:
			System.out.println("Invalid choice. Please select a valid option.");
		}
	}

	public static void customerSubMenu(User user, List<Product> products, List<Product> cart, List<Order> orders, Scanner scanner) {
		System.out.println("\nWelcome, " + user.getUsername() + "!");
		System.out.println("Customer Menu:");
		System.out.println("1. Browse Products");
		System.out.println("2. View Cart");
		System.out.println("3. Log Out");
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			browseProducts(products, cart, scanner);
			break;
		case 2:
			viewCart(cart);	
			break;
		case 3:
			System.out.println("Logged out.");
			return;
		default:
			System.out.println("Invalid choice. Please select a valid option.");
		}
	}

	public static void browseProducts(List<Product> products, List<Product> cart, Scanner scanner) {
	    System.out.println("\nBrowse Products:");
	    System.out.println("ID | Name | Price | Description");

	    for (Product product : products) {
	        System.out.println(product.getId() + " | " + product.getName() + " | " +
	                product.getPrice() + " | " + product.getDescription());
	    }

	    System.out.println("Enter the ID of the product you want to add to your cart (or enter 0 to go back): ");
	    int selectedProductId = scanner.nextInt();

	    if (selectedProductId == 0) {
	        return;
	    }

	    Product selectedProduct = null;
	    for (Product product : products) {
	        if (product.getId() == selectedProductId) {
	            selectedProduct = product;
	            break;
	        }
	    }

	    if (selectedProduct != null) {
	        System.out.println(selectedProduct.getName() + " added to your cart.");

	        System.out.print("Enter the quantity: ");
	        int quantity = scanner.nextInt();

	        // Check if the item is already in the cart
	        boolean alreadyInCart = false;
	        for (Product productInCart : cart) {
	            if (productInCart.getId() == selectedProduct.getId()) {
	                alreadyInCart = true;
	                break;
	            }
	        }

	        if (alreadyInCart) {
	            System.out.println("Product is already in your cart. Quantity updated.");
	            // Update quantity of the product in the user's cart
	            for (Product productInCart : cart) {
	                if (productInCart.getId() == selectedProduct.getId()) {
	                    // You might want to add logic to limit the quantity or merge quantities, etc.
	                    productInCart.setQuantity(productInCart.getQuantity() + quantity);
	                    break;
	                }
	            }
	        } else {
	            // Product not in the cart, add a new instance to the cart
	            Product cartProduct = new Product(selectedProduct.getId(), selectedProduct.getName(),
	                    selectedProduct.getPrice(), selectedProduct.getDescription());
	            cartProduct.setQuantity(quantity);
	            cart.add(cartProduct);
	        }
	    } else {
	        System.out.println("Product not found.");
	    }
	}


	public static User logIn(List<User> users, Scanner scanner) {
		System.out.print("Enter your username: ");
		String inputUsername = scanner.next();
		System.out.print("Enter your password: ");
		String inputPassword = scanner.next();

		for (User user : users) {
			if (user.authenticate(inputUsername, inputPassword)) {
				System.out.println("Logged in as " + user.getUsername());
				return user;
			}
		}

		System.out.println("Invalid username or password.");
		return null;
	}

	public static void signUp(List<User> users, Scanner scanner) {
		System.out.print("Enter a new username: ");
		String newUsername = scanner.next();

		// Check if the username is already taken
		for (User user : users) {
			if (user.getUsername().equals(newUsername)) {
				System.out.println("Username already taken. Please choose a different username.");
				return;
			}
		}

		System.out.print("Enter a password: ");
		String newPassword = scanner.next();
		System.out.print("Enter your email: ");
		String newEmail = scanner.next();

		users.add(new User(newUsername, newPassword, newEmail));
		System.out.println("Account created successfully. You can now log in.");
	}

	public static void adminMenu(List<Product> products, Scanner scanner) {
		System.out.println("\nAdmin Menu:");
		System.out.println("1. Add Product");
		System.out.println("2. Remove Product");
		System.out.println("3. Back to Main Menu");
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			addProduct(products, scanner);
			break;
		case 2:
			removeProduct(products, scanner);
			break;
		case 3:
			return;
		default:
			System.out.println("Invalid choice. Please select a valid option.");
		}
	}

	public static void addProduct(List<Product> products, Scanner scanner) {
		System.out.print("Enter 1 Word product name: ");
		String productName = scanner.next();
		System.out.print("Enter product price: ");
		double productPrice = scanner.nextDouble();
		System.out.print("Enter 1 Word product description: ");
		String productDescription = scanner.next();

		int nextId = products.size() + 1; // Generate the next available ID
		Product newProduct = new Product(nextId, productName, productPrice, productDescription);
		products.add(newProduct);

		System.out.println("Product added successfully.");
	}

	public static void removeProduct(List<Product> products, Scanner scanner) {
		System.out.print("Enter the ID of the product to remove: ");
		int productId = scanner.nextInt();

		Product productToRemove = null;
		for (Product product : products) {
			if (product.getId() == productId) {
				productToRemove = product;
				break;
			}
		}

		if (productToRemove != null) {
			products.remove(productToRemove);
			System.out.println("Product removed successfully.");
		} else {
			System.out.println("Product not found.");
		}
	}
	public static void viewCart(List<Product> cart) {
	    if (cart.isEmpty()) {
	        System.out.println("Your cart is empty.");
	        return;
	    }

	    System.out.println("\nYour Cart:");
	    System.out.println("ID | Name | Price | Description | Quantity | Total");

	    double totalAmount = 0;
	    for (Product cartProduct : cart) {
	        int quantity = cartProduct.getQuantity();
	        double total = cartProduct.getPrice() * quantity;

	        totalAmount += total;

	        System.out.println(cartProduct.getId() + " | " + cartProduct.getName() + " | " +
	                cartProduct.getPrice() + " | " + cartProduct.getDescription() + " | " +
	                quantity + " | " + total);
	    }

	    System.out.println("Total Amount: $" + totalAmount);

	    System.out.println("\nSelect an option:");
	    System.out.println("1. Checkout");
	    System.out.println("2. Remove Item");
	    System.out.println("3. Back to Customer Menu");

	    Scanner scanner = new Scanner(System.in);
	    int choice = scanner.nextInt();

	    switch (choice) {
	        case 1:
	            cart.clear();
	            break;
	        case 2:
	            removeCartItem(cart, scanner);
	            break;
	        case 3:
	            return;
	        default:
	            System.out.println("Invalid choice. Please select a valid option.");
	            viewCart(cart);
	            break;
	    }
	}

	public static void removeCartItem(List<Product> cart, Scanner scanner) {
	    System.out.print("Enter the ID of the product to remove from your cart: ");
	    int productId = scanner.nextInt();

	    Product productToRemove = null;
	    for (Product cartProduct : cart) {
	        if (cartProduct.getId() == productId) {
	            productToRemove = cartProduct;
	            break;
	        }
	    }

	    if (productToRemove != null) {
	        cart.remove(productToRemove);
	        System.out.println("Product removed from your cart.");
	    } else {
	        System.out.println("Product not found in your cart.");
	    }
	}



}