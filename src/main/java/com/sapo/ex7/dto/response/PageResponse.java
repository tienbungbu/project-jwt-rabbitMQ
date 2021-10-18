package com.sapo.ex7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageResponse {

  private int currentPage;
  private int totalPage;
  private Long totalElements;
}
