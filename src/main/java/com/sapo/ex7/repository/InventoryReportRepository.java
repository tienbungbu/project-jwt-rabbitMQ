package com.sapo.ex7.repository;

import com.sapo.ex7.entity.InventoryEntity;
import com.sapo.ex7.entity.InventoryReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InventoryReportRepository extends JpaRepository<InventoryReport, Long> {


}
