package edu.wctc.dao;
        import edu.wctc.entity.Item;

        import java.util.List;

public interface ItemDao {
    List<Item> getItems();

    void saveItem(Item theItem);

    Item getItem(int theId);

    void deleteItem(int theId);

    List<Item> getItemsByName(String theSearchTerm);
}
