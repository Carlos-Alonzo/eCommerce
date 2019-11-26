package com.example.demo.model.persistence.repositories;

import com.example.demo.model.persistence.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByName(String name);
	Optional<Item> findById(Long id);
}
