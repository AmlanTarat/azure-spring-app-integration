package com.search.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.search.app.dto.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor,String>{

}
