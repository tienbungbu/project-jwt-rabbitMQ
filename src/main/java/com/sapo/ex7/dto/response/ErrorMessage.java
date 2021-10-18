package com.sapo.ex7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorMessage {

  private String message;
  private Map<String, String> errors;
  private Date timestamp;
  private Integer status;

}
