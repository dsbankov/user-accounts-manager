package com.dsb.useraccountsmanager;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAccountsManagerApplication implements CommandLineRunner {
	
	@Autowired
	private UserAccountsRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(UserAccountsManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// populate the database
		repository.save(new UserAccount("Dimitar", "Bankov", "dsbankov@gmail.com", Date.valueOf("1989-10-18")));
		repository.save(new UserAccount("Georgi", "Ivanov", "gigonzo@gmail.com", Date.valueOf("1992-09-22")));
		repository.save(new UserAccount("Stefan", "Dimitrov", "sd1970@abv.bg", Date.valueOf("1970-01-03")));
		repository.save(new UserAccount("Ivelina", "Hristova", "hristova_93@abv.bg", Date.valueOf("2010-02-01")));
		repository.save(new UserAccount("Hristo", "Georgiev", "hristo333@yahoo.com", Date.valueOf("1988-10-24")));
		repository.save(new UserAccount("Maria", "Ivanova", "mivanova@gmail.com", Date.valueOf("2010-02-01")));
		repository.save(new UserAccount("Ivan", "Ivanov", "iivanov@abv.bg", Date.valueOf("2009-02-02")));
		repository.save(new UserAccount("Dimitar", "Ivanov", "dimoiv@yahoo.com", Date.valueOf("2012-03-01")));
		repository.save(new UserAccount("Kaloqn", "Piserov", "kpis@abv.bg", Date.valueOf("2008-05-04")));
		repository.save(new UserAccount("Antoaneta", "Hlqbova", "toniii_@abv.bg", Date.valueOf("1992-03-11")));
	}

}
