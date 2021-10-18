package com.sapo.ex7.service;



import com.sapo.ex7.dto.response.AbstractResponse;
import org.springframework.http.ResponseEntity;


public interface IService<E, T> {

  AbstractResponse findAll(String key, int page, int limit);

  E save(E entity);

  ResponseEntity<AbstractResponse> delete(T id);

  AbstractResponse getById(T id);

  Long totalItem();





}
