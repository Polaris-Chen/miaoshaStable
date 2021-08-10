package com.imooc.miaoshaproject.dataobject;



import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name ="user_password")
@Data
public class UserPasswordDO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "encrpt_password")
    private String encrptPassword;
    @Column(name = "user_id")
    private Integer userId;
}