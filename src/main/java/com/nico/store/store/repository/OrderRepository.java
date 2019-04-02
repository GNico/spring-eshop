package com.nico.store.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.nico.store.store.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long> { 

}
