package org.example.kardex.repositories;

import java.util.Optional;
import org.example.kardex.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query(value = "SELECT id, user_name, password FROM customer WHERE id = :id_user and password = :password", nativeQuery = true)
	Optional<Customer> findByIdUser (@Param("id_user") Long idUser,@Param("password") String password);

}
