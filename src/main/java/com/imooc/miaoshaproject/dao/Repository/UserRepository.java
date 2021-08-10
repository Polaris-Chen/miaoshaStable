package com.imooc.miaoshaproject.dao.Repository;

import com.imooc.miaoshaproject.dataobject.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author polarisChen
 * @create 2021/8/2 12:45 下午
 */
@Repository
public interface UserRepository  extends JpaRepository<UserDO,Integer> {

    UserDO findByTelphone(String telPhone);
}
