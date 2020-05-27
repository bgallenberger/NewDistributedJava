package edu.wctc.Service;

import edu.wctc.dao.ItemDao;
import edu.wctc.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    // inject Donut DAO
    @Autowired
    private ItemDao ItemDAO;

    @Override
    @Transactional
    public void deleteItem(int theId) {
        ItemDAO.deleteItem(theId);
    }

    @Override
    @Transactional
    public Item getItem(int theId) {
        return ItemDAO.getItem(theId);
    }

    @Override
    // with @Transactional annotation, no need to begin or commit transaction
    @Transactional
    public List<Item> getItems() {
        // Delegate call to DAO
        return ItemDAO.getItems();
    }

    @Override
    @Transactional
    public List<Item> getItemsByName(String theSearchTerm) {
        return ItemDAO.getItemsByName(theSearchTerm);
    }

    @Override
    @Transactional
    public void saveItem(Item theItem) {

        // Delegate call to DAO
        ItemDAO.saveItem(theItem);
    }
}
