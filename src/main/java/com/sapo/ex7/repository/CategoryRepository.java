package com.sapo.ex7.repository;

import com.sapo.ex7.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  @Query(value = "SELECT * FROM categories  WHERE id = ?1", nativeQuery = true)
  CategoryEntity findOneId(Long id);

  Page<CategoryEntity> findByNameContainsOrCodeContainsOrderByCode(String name, String code,
                                                             Pageable pageable);


}
