package com.dsb.useraccountsmanager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountsRepository extends CrudRepository<UserAccount, Long> {

	boolean existsByEmailAddress(String emailAddress);
	
}