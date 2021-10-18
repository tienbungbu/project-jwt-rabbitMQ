package com.sapo.ex7.controller;


import com.sapo.ex7.dto.InventoryDTO;
import com.sapo.ex7.dto.response.AbstractResponse;
import com.sapo.ex7.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("admin/inventories")
public class InventoryController {

  private final InventoryService service;

  public InventoryController(InventoryService service) {
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
  public ResponseEntity<InventoryDTO> save(@Valid @RequestBody InventoryDTO dto) {
    return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InventoryDTO> update(
      @Valid @RequestBody InventoryDTO dto,
      @PathVariable("id") Long id) {

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
