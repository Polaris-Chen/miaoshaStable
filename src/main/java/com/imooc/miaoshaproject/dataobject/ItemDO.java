package com.imooc.miaoshaproject.dataobject;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="item")
public class ItemDO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "img_url")
    private String imgUrl;


}