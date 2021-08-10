package com.imooc.miaoshaproject.service.impl;

import com.imooc.miaoshaproject.dao.Repository.ItemRepository;
import com.imooc.miaoshaproject.dao.Repository.ItemStockRepository;
import com.imooc.miaoshaproject.dataobject.ItemDO;
import com.imooc.miaoshaproject.dataobject.ItemStockDO;
import com.imooc.miaoshaproject.error.BusinessException;
import com.imooc.miaoshaproject.error.EmBusinessError;
import com.imooc.miaoshaproject.service.model.ItemModel;
import com.imooc.miaoshaproject.service.model.PromoModel;
import com.imooc.miaoshaproject.validator.ValidatorImpl;
import com.imooc.miaoshaproject.service.ItemService;
import com.imooc.miaoshaproject.service.PromoService;
import com.imooc.miaoshaproject.validator.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hzllb on 2018/11/18.
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PromoService promoService;

    @Autowired
    private ItemStockRepository itemStockRepository;

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //转化itemmodel->dataobject
        ItemDO itemDO = this.convertItemDOFromItemModel(itemModel);

        //写入数据库
        itemRepository.save(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);

        itemStockRepository.save(itemStockDO);

        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemRepository.findAll();
        List<ItemModel> itemModelList =  itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockRepository.findByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemRepository.findById(id).orElse(null);
        if(itemDO == null){
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockRepository.findByItemId(itemDO.getId());


        //将dataobject->model
        ItemModel itemModel = convertModelFromDataObject(itemDO,itemStockDO);

        //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        //log.info(promoModel.toString());

        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    @Transactional(rollbackFor=BusinessException.class)
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int tempAmount=itemStockRepository.findByItemId(itemId).getStock()-amount;
        log.error(String.valueOf(tempAmount));
        if (tempAmount<0){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH,"库存不足");
        }
        //int affectedRow =
        itemStockRepository.decreaseStock(tempAmount,itemId);
        return true;

    }

    @Override
    @Transactional(rollbackFor=BusinessException.class)
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        int tempAmount=itemRepository.findById(itemId).orElse(null).getSales()+amount;
        itemRepository.increaseSales(tempAmount,itemId);
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

}
