//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.model;

import static com.yan.avery.invoices.common.Sizes.MAX_LENGTH_INVOICE_NUMBER;
import static com.yan.avery.invoices.common.Sizes.MAX_LENGTH_PO_NUMBER;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//=================================================================================================
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

	@Column(name = "invoice_number", nullable = false, length = MAX_LENGTH_INVOICE_NUMBER)
	private String invoiceNumber;

	@Column(name = "po_number", nullable = false, length = MAX_LENGTH_PO_NUMBER)
	private String poNumber;

	@Column(name = "amount_cents", nullable = false)
	private Long amountCents;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;
}
