//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.api.v1;

import static com.yan.avery.invoices.common.Sizes.MIN_LENGTH_INVOICE_NUMBER;
import static com.yan.avery.invoices.common.Sizes.MAX_LENGTH_INVOICE_NUMBER;
import static com.yan.avery.invoices.common.Sizes.MIN_LENGTH_PO_NUMBER;
import static com.yan.avery.invoices.common.Sizes.MAX_LENGTH_PO_NUMBER;
import static com.yan.avery.invoices.common.Sizes.MIN_AMOUNT_CENTS;
import static com.yan.avery.invoices.common.Sizes.MAX_AMOUNT_CENTS;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

//=================================================================================================
@Getter @Setter @NoArgsConstructor
public class InvoiceResource {

	@JsonProperty("id")
	private Long id;

	@NotNull
	@Size(min = MIN_LENGTH_INVOICE_NUMBER, max = MAX_LENGTH_INVOICE_NUMBER)
	@JsonProperty("invoice_number")
	private String invoiceNumber;

	@NotNull
	@Size(min = MIN_LENGTH_PO_NUMBER, max = MAX_LENGTH_PO_NUMBER)
	@JsonProperty("po_number")
	private String poNumber;

	@NotNull
	@JsonProperty("due_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	@NotNull
	@Min(MIN_AMOUNT_CENTS)
	@Max(MAX_AMOUNT_CENTS)
	@JsonProperty("amount_cents")
	private Long amountCents;

	@JsonProperty("created_at")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime createdAt;
}
