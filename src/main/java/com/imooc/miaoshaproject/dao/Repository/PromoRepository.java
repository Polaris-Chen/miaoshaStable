package com.imooc.miaoshaproject.dao.Repository;

import com.imooc.miaoshaproject.dataobject.PromoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author polarisChen
 * @create 2021/8/2 5:02 下午
 */
@Repository
public interface PromoRepository extends JpaRepository<PromoDO,Integer> {
    PromoDO findByItemId(Integer itemId);
}
