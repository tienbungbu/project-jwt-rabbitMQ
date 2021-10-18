package com.sapo.ex7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_report")
public class InventoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "inventory_id")
//    private InventoryEntity inventoryEntity;

    @Column(name = "product_number")
    private Long productNumber;

    @Column(nullable = false, columnDefinition = "DATETIME default current_timestamp")
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:MM:ss")
    private Date statisticalDay;
}
