package com.imooc.miaoshaproject.dao.Repository;

import com.imooc.miaoshaproject.dataobject.ItemDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author polarisChen
 * @create 2021/8/2 3:07 下午
 */
@Repository
public interface ItemRepository extends JpaRepository<ItemDO, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update item set sales =?1 where id=?2", nativeQuery = true)
    void increaseSales(Integer amount,Integer itemId);
}
