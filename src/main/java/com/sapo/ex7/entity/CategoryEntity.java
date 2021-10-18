package com.sapo.ex7.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 20, unique = true)
  @NonNull
  @NotBlank
  @Size(min = 3, max = 20)
  private String code;



  @Column(nullable = false, length = 50)
  @NonNull
  @NotBlank
  @Size(min = 3, max = 50)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  @NonNull
  @NotBlank
  private String description;

  @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<ProductEntity> products;

  @Override
  public String toString() {
    return "Category{" +
        "id='" + getId() + '\'' +
        "code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", created_date='" + getCreatedDate() + '\'' +
        ", updated_date='" + getUpdatedDate() + '\'' +
        '}';
  }
}
