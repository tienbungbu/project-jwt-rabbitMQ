package com.sapo.ex7.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends BaseDTO {


  @NonNull
  @NotBlank
  @Size(min = 3, max = 20)
  private String code;

  @NonNull
  @NotBlank
  @Size(min = 3, max = 50)
  private String name;

  @NonNull
  private Long categoryId;

  @NonNull
  private Long inventoryId;

  @NonNull
  @NotBlank
  private String description;

  @NonNull

  private BigDecimal price;

  @NonNull
  @Size(min = 3, max = 100)
  private String image;

  @NonNull
  private Integer sellNumber;

  @NonNull
  private Integer quantity;
}
