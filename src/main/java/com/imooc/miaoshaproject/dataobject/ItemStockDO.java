package com.imooc.miaoshaproject.dataobject;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="item_stock")
public class ItemStockDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "item_id")
    private Integer itemId;

}