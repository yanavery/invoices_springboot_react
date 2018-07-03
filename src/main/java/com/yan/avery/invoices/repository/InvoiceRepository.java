//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.repository;

import com.yan.avery.invoices.model.Invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//=================================================================================================
@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {

	//---------------------------------------------------------------------------------------------
	Page<Invoice> findByInvoiceNumberContainingAndPoNumberContainingAllIgnoreCase(
		String invoiceNumber, String poNumber, Pageable pageable);
}
