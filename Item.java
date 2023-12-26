import java.util.Objects;

public class Item {
    private String itemName;
    private String itemDescription;

    public Item(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public String getName() {
        return itemName;
    }

    public String getDescription() {
        return itemDescription;
    }
}
