package com.sapo.ex7.repository;

import com.sapo.ex7.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  Page<ProductEntity> findByNameContainsOrCodeContainsOrderByCode(String name, String code,
                                                            Pageable pageable);

  @Query(value = "select count(id) from products where inventory_id = :inventoryId",nativeQuery = true)
  Long countProductsByInventory(@Param("inventoryId") Long inventoryId);


}
