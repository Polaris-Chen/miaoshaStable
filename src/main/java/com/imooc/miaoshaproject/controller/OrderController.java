package com.imooc.miaoshaproject.controller;

import com.imooc.miaoshaproject.dao.Repository.UserRepository;
import com.imooc.miaoshaproject.dataobject.UserDO;
import com.imooc.miaoshaproject.service.OrderService;
import com.imooc.miaoshaproject.error.BusinessException;
import com.imooc.miaoshaproject.error.EmBusinessError;
import com.imooc.miaoshaproject.response.CommonReturnType;
import com.imooc.miaoshaproject.service.model.OrderModel;
import com.imooc.miaoshaproject.service.model.UserModel;
import com.mysql.cj.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzllb on 2018/11/18.
 */
@Controller("order")
@RequestMapping("/order")
@Slf4j
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    //封装下单请求
    @RequestMapping(value = "/createorder",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId",required = false)Integer promoId) throws BusinessException {

        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }

        //获取用户的登陆信息
        int userId = (int)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        int i=1;
        UserDO user=userRepository.findById(userId).orElse(null);
        String id=userId+user.getName();
        log.error(id);
        if (redisTemplate.hasKey(id)){
            log.error(String.valueOf(redisTemplate.opsForValue().get(id)));
            if((int)redisTemplate.opsForValue().get(id)>=3){
                throw new BusinessException(EmBusinessError.TOO_MANY_BUYS,"买太多了");
            }
            i+=(int)redisTemplate.opsForValue().get(id);
        }
        redisTemplate.opsForValue().set(id,i, 10000, TimeUnit.MILLISECONDS);


        OrderModel orderModel = orderService.createOrder(userId,itemId,promoId,amount);

        return CommonReturnType.create(null);
    }
}
