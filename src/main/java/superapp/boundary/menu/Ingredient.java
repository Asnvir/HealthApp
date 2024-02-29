package superapp.boundary.menu;

public class Ingredient {
    private String name;
    private String quantity;
    private String unit;
    private String category;

    public Ingredient(String category) {
        this.category = category;
    }

    public Ingredient(String name, String quantity, String unit, String category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
