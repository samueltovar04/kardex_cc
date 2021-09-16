package org.example.kardex.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "purchases")
public class Purchase {
	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime date;
	private Double total;

	@ManyToOne
	private Customer customer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
	private List<PurchaseDetail> purchaseDetailList;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
