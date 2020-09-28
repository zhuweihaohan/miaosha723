package com.lhd.miaosha.rabbitmq;

import com.lhd.vo.MiaoshaOrder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {
    @Autowired
AmqpTemplate amqpTemplate;
    //向消息队列发消息
    public void sendMessage(MiaoshaOrder order){
        System.out.println("准备发送"+order);

        amqpTemplate.convertAndSend("miaosha.direct",order);
        System.out.println("发送成功");
}
}
