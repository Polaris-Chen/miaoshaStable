package com.imooc.miaoshaproject.dao.Repository;

import com.imooc.miaoshaproject.dataobject.ItemStockDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author polarisChen
 * @create 2021/8/2 3:08 下午
 */
@Repository
public interface ItemStockRepository  extends JpaRepository<ItemStockDO,Integer> {
    ItemStockDO findByItemId(Integer id);


    @Transactional
    @Modifying
    @Query(value = "update item_stock set stock =?1 where item_id=?2", nativeQuery = true)
    void decreaseStock(int amount,int itemId);
}
