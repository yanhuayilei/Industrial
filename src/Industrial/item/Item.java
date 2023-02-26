package Industrial.item;

public class Item {

    protected final String name;
    public Item(String name){
        this.name = name;
        Items.add(this);
    }
    public String getName() {
        return name;
    }

}
