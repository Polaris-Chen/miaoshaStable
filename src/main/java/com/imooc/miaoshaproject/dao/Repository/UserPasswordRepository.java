package com.imooc.miaoshaproject.dao.Repository;

import com.imooc.miaoshaproject.dataobject.UserPasswordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author polarisChen
 * @create 2021/8/2 1:50 下午
 */
@Repository
public interface UserPasswordRepository  extends JpaRepository<UserPasswordDO,Integer> {

    UserPasswordDO findByUserId(int userId);
}
