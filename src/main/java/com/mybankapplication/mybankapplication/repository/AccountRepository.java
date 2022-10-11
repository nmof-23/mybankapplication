package com.mybankapplication.mybankapplication.repository;

import com.mybankapplication.mybankapplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account , String> {
}
