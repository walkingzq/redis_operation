package com.zq.redis.controller;

import com.zq.redis.utils.RedisUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by qaa on 2017/9/26.
 */
@Controller
public class RedisController {
    private Logger logger = Logger.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * get操作
     * @return
     */
    @RequestMapping(value = "/get")
    @ResponseBody
    public String getValue(){
        //拼接key值
        String[] keys = new String[60];
        for (int i=0;i<60;i++){
            if (i<10){
                keys[i] = "str-0" + i;
            }else {
                keys[i] = "str-" + i;
            }
        }
        //从redis中取值
        Long sum = 0L;
        Number[] values = new Number[60];
        try {
            for (int i=0;i<60;i++){
                values[i] = (Number) redisTemplate.opsForValue().get(keys[i]);
                logger.info("value[" + i +"]:" + values[i]);
                sum+=values[i].longValue();
            }
        }catch (Exception exc){
            logger.error("redis exception:" + exc.getMessage());
            return "-1";
        }

        return sum.toString();
    }

    /**
     * set操作
     * @return
     */
    @RequestMapping(value = "/set")
    @ResponseBody
    public String get(){
        String[] keys = new String[60];
        for (int i=0;i<60;i++){
            if (i<10){
                keys[i] = "str-0" + i;
            }else {
                keys[i] = "str-" + i;
            }
        }

        for (int i=0;i<60;i++){
            try {
                RedisUtil.set(keys[i],100);
            }catch (Exception exc){
                logger.error("Exception in redis setting:" + exc.getMessage());
                return "failed.";
            }
        }

        return "success!";
    }

}
