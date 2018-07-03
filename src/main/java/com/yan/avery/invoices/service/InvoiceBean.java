//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//=================================================================================================
@Getter @Setter @NoArgsConstructor
public class InvoiceBean {

	private Long id;
	private String invoiceNumber;
	private String poNumber;
	private LocalDate dueDate;
	private Long amountCents;
	private OffsetDateTime createdAt;
}
