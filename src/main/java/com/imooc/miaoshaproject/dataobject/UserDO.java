package com.imooc.miaoshaproject.dataobject;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@Data
public class UserDO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "telphone")
    private String telphone;

    @Column(name = "register_mode")
    private String registerMode;

    @Column(name = "third_party_id",nullable = false)
    private String thirdPartyId;

}