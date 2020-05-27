package edu.wctc.controller;


import edu.wctc.entity.Item;
import edu.wctc.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/Item")
public class ItemController {
    // Inject the Donut Service
    @Autowired
    private ItemService ItemService;

    @GetMapping("/admin/delete")
    public String deleteItem(@RequestParam("ItemId") int theId) {
        // Delete the donut
        ItemService.deleteItem(theId);

        return "redirect:/donut/list";
    }

    @GetMapping("/list")
    public String listItems(Model theModel) {
        // Get donuts from service
        List<Item> ItemList = ItemService.getItems();

        // Add the list of donuts to the model
        theModel.addAttribute("items", ItemList);

        // Return the name of the view
        return "list-Items";
    }

    @RequestMapping("/user/showAddItemForm")
    public String showAddDonutForm(Model theModel) {
        Item theItem = new Item();

        theModel.addAttribute("aItem", theItem);

        return "Item-form";
    }

    @RequestMapping("/user/showUpdateDonutForm")
    public String showUpdateDonutForm(@RequestParam("ItemId") int theId,
                                      Model theModel) {
        // Get donut from the database
        Item theItem = ItemService.getItem(theId);

        // Set donut as a model attribute to pre-populate the form
        theModel.addAttribute("aItem", theItem);

        // Return the view
        return "Item-form";
    }


    @PostMapping("/user/save")
    public String saveItem(@Valid @ModelAttribute("Item") Item theItem,
                            BindingResult bindingResult,
                            Model theModel) {
        // Any validation errors?
        if (bindingResult.hasErrors()) {
            // Display the errors in the console
            System.err.println(bindingResult);

            // Send back to form with error messages
            return "Item-form";
        }

        // Update the donut
        ItemService.saveItem(theItem);

        // Redirect back to the donut list
        return "redirect:/Item/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchTerm") String theSearchTerm, Model theModel) {
        List<Item> matchingItems = ItemService.getItemsByName(theSearchTerm);

        theModel.addAttribute("Item", matchingItems);

        return "list-Item";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // Trim whitespace from all string form parameters read by this controller
        // If the entire string is whitespace, trim it to null
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        e.printStackTrace();
    }
}
