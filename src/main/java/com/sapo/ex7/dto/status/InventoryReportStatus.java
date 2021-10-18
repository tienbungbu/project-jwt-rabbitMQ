package com.sapo.ex7.dto.status;

import com.sapo.ex7.dto.ProductDTO;
import com.sapo.ex7.entity.InventoryReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReportStatus {
        private InventoryReport inventoryReport;
        private String status;
        private String message;
}
