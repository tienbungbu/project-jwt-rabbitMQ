package com.sapo.ex7.producer;

import com.sapo.ex7.config.MessagingConfig;
import com.sapo.ex7.dto.status.InventoryReportStatus;
import com.sapo.ex7.entity.InventoryReport;
import com.sapo.ex7.repository.InventoryReportRepository;
import com.sapo.ex7.repository.InventoryRepository;
import com.sapo.ex7.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/inventories")
@Sl
public class ProductProducerService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryReportRepository inventoryReportRepository;


    @PostMapping("/totalProductsAtInventory/{inventoryId}")
    public String totalProduct(@RequestBody InventoryReport inventoryReport,@PathVariable Long inventoryId) {

        inventoryReport.setProductNumber(productRepository.countProductsByInventory(inventoryId));
        inventoryReport.setStatisticalDay(new Date(System.currentTimeMillis()));
        inventoryReportRepository.save(inventoryReport);

        // service
        InventoryReportStatus inventoryReportStatus = new InventoryReportStatus(inventoryReport,"PROCCESS" ,"total placed successfully in ");
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, inventoryReport); //String exchange , String routingKey,Object obj
        return "SUCCESS !!";
    }
}



