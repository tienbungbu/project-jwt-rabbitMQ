package com.sapo.ex7.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends BaseDTO {

  @NotBlank
  @Size(min = 3, max = 20)
  private String code;

  @NotBlank
  @Size(min = 5, max = 50)
  private String name;

  @NonNull
  @NotBlank
  private String description;

  @Override
  public String toString() {
    return "wareHouse{" +
        "id='" + getId() + '\'' +
        "name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", address='" + description + '\'' +
        ", created_date='" + getCreatedDate() + '\'' +
        ", updated_date='" + getUpdatedDate() + '\'' +
        '}';
  }
}