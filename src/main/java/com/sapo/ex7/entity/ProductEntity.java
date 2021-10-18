package com.sapo.ex7.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Transactional
public class ProductEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(length = 20, nullable = false, unique = true)
  @NonNull
  @NotBlank
  @Size(min = 3, max = 20)
  private String code;
  @Column(length = 50, nullable = false)
  @NonNull
  @NotBlank
  @Size(min = 3, max = 50)
  private String name;

  @Column(columnDefinition = "TEXT", nullable = false)
  @NonNull
  @NotBlank
  private String description;

  @Column(nullable = false)
  @NonNull
  private BigDecimal price;

  @Column(length = 100, nullable = false)
  @NonNull
  @NotBlank
  @Size(min = 3, max = 100)
  private String image;

  @Column(nullable = false, name = "sell_number")
  @NonNull
  private Integer sellNumber;

  @Column(nullable = false)
  @NonNull
  private Integer quantity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private CategoryEntity categoryEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_id")
  private InventoryEntity inventoryEntity;

  @Override
  public String toString() {
    return "Product{" + "\n" +
        "id='" + this.getId() + '\'' +
        "code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", category=" + categoryEntity.getName() +
        ", inventory=" + inventoryEntity.getName() +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", image='" + image + '\'' +
        ", sell_number=" + sellNumber +
        ", number=" + quantity +
        ", created_date='" + getCreatedDate() + '\'' +
        ", updated_date='" + getUpdatedDate() + '\'' + "\n" +
        '}';
  }
}
