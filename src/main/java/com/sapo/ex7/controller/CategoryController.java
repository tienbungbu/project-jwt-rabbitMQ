package com.sapo.ex7.controller;


import com.sapo.ex7.dto.CategoryDTO;
import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("admin/categories")
public class CategoryController {

  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  @GetMapping("")
  public ResponseEntity<AbstractResponse> findAll(
      @RequestParam(name = "key", required = false, defaultValue = "") String key,
      @RequestParam(name = "page", required = false) Optional<Integer> page,
      @RequestParam(name = "limit", required = false) Optional<Integer> limit
  ) {
    return new ResponseEntity<>(service.findAll(key, page.orElse(1), limit.orElse(
        Math.toIntExact(service.totalItem()))), HttpStatus.OK);

  }

  @PostMapping("")
  public ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO dto) {
    return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto,
                                            @PathVariable("id") Long id) {
    dto.setId(id);
    return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AbstractResponse> findById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AbstractResponse> delete(@PathVariable("id") Long id) {
    return service.delete(id);
  }
}
