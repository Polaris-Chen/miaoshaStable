package com.imooc.miaoshaproject.dataobject;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_info")
public class OrderDO {
    @Id
    private String id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "order_price")
    private Double orderPrice;

    @Column(name = "promo_id")
    private Integer promoId;

}