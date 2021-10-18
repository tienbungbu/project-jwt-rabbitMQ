//package com.sapo.ex7.consumer;
//
//import com.sapo.ex7.config.MessagingConfig;
//import com.sapo.ex7.dto.status.InventoryReportStatus;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class ProductConsumer {
//    @RabbitListener(queues = MessagingConfig.QUEUE)
//    public void consumerMessageFromQueue(InventoryReportStatus inventoryReportStatus) {
//        log.info("Message recieved from queue : " + inventoryReportStatus);
//    }
//}
