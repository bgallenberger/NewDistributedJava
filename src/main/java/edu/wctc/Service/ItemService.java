package edu.wctc.Service;

import edu.wctc.entity.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    void deleteItem(int theId);

    Item getItem(int theId);

    List<Item> getItems();

    List<Item> getItemsByName(String theSearchTerm);

    void saveItem(Item theItem);

}
