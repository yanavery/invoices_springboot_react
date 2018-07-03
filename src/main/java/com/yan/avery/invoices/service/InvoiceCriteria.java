//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.service;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;

//=================================================================================================
@Getter @Builder
public class InvoiceCriteria {

	private String invoiceNumber;
	private String poNumber;

	public boolean hasInvoiceCriteria() {
		return StringUtils.isNotBlank(getInvoiceNumber());
	}

	public boolean hasPoNumberCriteria() {
		return StringUtils.isNotBlank(getPoNumber());
	}

	public boolean hasNoCriteria() {
		return !hasInvoiceCriteria() && !hasPoNumberCriteria();
	}
}
