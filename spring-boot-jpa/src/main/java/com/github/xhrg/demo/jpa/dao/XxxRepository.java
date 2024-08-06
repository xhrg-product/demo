package com.github.xhrg.demo.jpa.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XxxRepository extends JpaRepository<XxxEntity, Integer> {

	List<XxxEntity> findAll();

	@Query(value = "select * from xxx where name like %:word% ", nativeQuery = true)
	Page<XxxEntity> findByPage(@Param("word") String word, Pageable pageable);

	XxxEntity findByName(String name);

}