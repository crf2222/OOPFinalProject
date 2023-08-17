import java.util.*;
class Order {
    private String orderNumber;
    private User customer;
    private List<Product> items;
    private double totalAmount;

    public Order(String orderNumber, User customer, List<Product> items) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.items = items;
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        totalAmount = 0.0;
        for (Product item : items) {
            totalAmount += item.getPrice();
        }
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public User getCustomer() {
        return customer;
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
