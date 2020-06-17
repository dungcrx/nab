package com.dung.phan.order.model;

import com.dung.phan.order.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDERS")
public class Orders extends BaseEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "customer_address")
	private String customerAddress;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "customer_phone", nullable = false)
	private String customerPhone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private OrderDetail orderDetail;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;
}