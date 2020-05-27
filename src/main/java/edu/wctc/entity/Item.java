package edu.wctc.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "ALL_ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private int itemId;

    @NotNull(message = "required")
    @Size(min = 1, max = 30, message = "1-30 characters")
    @Column(name = "ITEM_NAME")
    private String name;

    @NotNull
    @Size(min = 1, max = 8, message = "must be a valid category 1-8")
    @Column(name = "CATEGORY_ID")
    private int categoryId;

    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DETAIL_ID")
    private ItemDetail detail;

*/
    public Item(){
    }

    public Item(String name, int categoryId){
        this.name = name;
        this.categoryId = categoryId;
    }
}
