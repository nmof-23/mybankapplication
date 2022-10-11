package com.mybankapplication.mybankapplication.repository;

import com.mybankapplication.mybankapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
