package com.sapo.ex7.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseDTO {

  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date createdDate;

  @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date updatedDate;
}
