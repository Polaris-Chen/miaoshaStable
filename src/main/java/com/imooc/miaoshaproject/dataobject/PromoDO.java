package com.imooc.miaoshaproject.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="promo")
public class PromoDO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "promo_name")
    private String promoName;

    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "promo_item_price")
    private Double promoItemPrice;


}