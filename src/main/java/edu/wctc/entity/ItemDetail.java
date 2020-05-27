package edu.wctc.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table(name = "ITEM_DETAILS")
public class ItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private int itemId;

    @Min(value = 1, message = "greater than 0")
    @Column(name = "PRICE")
    private double price;

    @Size(min = 1, max = 30, message = "1-30 characters")
    @Column(name = "ITEM_COLOR")
    private String color;

    public ItemDetail() {
    }

}
