package com.sapo.ex7.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class InventoryEntity extends BaseEntity implements Serializable {


    @Column(length = 20, nullable = false, unique = true)
    @NonNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String code;

    @Column(length = 50, nullable = false)
    @NonNull
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    @Column(length = 100, nullable = false)
    @NonNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String address;

    @OneToMany(mappedBy = "inventoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductEntity> products;


    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", created_date='" + getCreatedDate() + '\'' +
                ", updated_date='" + getUpdatedDate() + '\'' +
                '}';
    }
}
