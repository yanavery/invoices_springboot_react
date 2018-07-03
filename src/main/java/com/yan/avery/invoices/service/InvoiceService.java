//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.service;

import com.yan.avery.invoices.model.Invoice;
import com.yan.avery.invoices.repository.InvoiceRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

//=================================================================================================
@Component
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ModelMapper modelMapper;

	//---------------------------------------------------------------------------------------------
	public List<InvoiceBean> find(InvoiceCriteria criteria, int offset, int limit) {

		// Although the spec is asking to sort by `createdAt`, purposely sorting by `id`
		// instead as multiple records could share the same `createdAt` value. Sorting
		// by `id` produces the same result with more accuracy.
		PageRequest paging = PageRequest.of(offset, limit, Sort.Direction.DESC, "id");

		// based on what criteria is provided, use different data loading strategy
		Page<Invoice> invoices;
		if (criteria.hasNoCriteria()) {
			invoices = invoiceRepository.findAll(paging);
		} else {
			invoices = invoiceRepository.findByInvoiceNumberContainingAndPoNumberContainingAllIgnoreCase(
				criteria.getInvoiceNumber(), criteria.getPoNumber(), paging);
		}

		return toBeanList(invoices);
	}

	//---------------------------------------------------------------------------------------------
	public InvoiceBean add(InvoiceBean invoiceToAdd) {
		Invoice invoice = toEntity(invoiceToAdd);
		invoice.setCreatedAt(OffsetDateTime.now());

		return toBean(invoiceRepository.save(invoice));
	}

	//---------------------------------------------------------------------------------------------
	private Invoice toEntity(InvoiceBean invoice) {
		return modelMapper.map(invoice, Invoice.class);
	}

	//---------------------------------------------------------------------------------------------
	private InvoiceBean toBean(Invoice invoice) {
		return modelMapper.map(invoice, InvoiceBean.class);
	}

	//---------------------------------------------------------------------------------------------
	private List<InvoiceBean> toBeanList(Page<Invoice> invoices) {
		return invoices.stream()
			.map(invoice -> toBean(invoice))
			.collect(Collectors.toList());
	}
}
