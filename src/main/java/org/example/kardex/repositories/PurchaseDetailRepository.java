package org.example.kardex.repositories;

import org.example.kardex.domain.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailRepository  extends JpaRepository<PurchaseDetail, Long> {

}
