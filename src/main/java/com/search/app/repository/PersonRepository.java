package com.search.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.search.app.dto.Person;

public interface PersonRepository extends CrudRepository<Person,String>{

}
