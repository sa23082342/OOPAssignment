package Control;

import Model.Inventory;

import java.util.ArrayList;
public class InventoryController {
    private ArrayList<Inventory> inventoryList;
    Inventory inventory;
    public InventoryController(ArrayList<Inventory> inventory) {
        this.inventoryList = inventory;
    }
    public void addInventory(Inventory newinventory) {
        inventoryList.add(newinventory);
    }
    public void updateInventory(int index, String itemName, double itemPrice, int itemQuantity) {
        if (index >= 0 && index < inventoryList.size()) {
            if (itemName != null) {
                inventory.setItemName(itemName);
            }
            if (itemPrice != 0.0) {
                inventory.setItemPrice(itemPrice);
            }
            if (itemQuantity != 0) {
                inventory.setItemQuantity(itemQuantity);
            }
        } else {
            System.out.println("Error");
        }
    }
    public void removeInventory(int index) {
        if (index >= 0 && index < inventoryList.size()) {
            inventoryList.remove(index);
        }
    }

}
