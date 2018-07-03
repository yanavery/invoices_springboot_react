//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.api.v1;

import static com.yan.avery.invoices.common.Defaults.CRITERION;
import static com.yan.avery.invoices.common.Defaults.OFFSET;
import static com.yan.avery.invoices.common.Defaults.LIMIT;

import com.yan.avery.invoices.service.InvoiceBean;
import com.yan.avery.invoices.service.InvoiceCriteria;
import com.yan.avery.invoices.service.InvoiceService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//=================================================================================================
@Controller
@RequestMapping(value = "/v1/invoices")
public class InvoiceApi {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private ModelMapper modelMapper;

	//---------------------------------------------------------------------------------------------
	/*	GET API to retrieve paged list of matching invoices. Retrieves using the following logic:

		- uses `And` between each of the criteria values (if more than one is provided).
		- uses `Containing` for matching each of the criteria values.
		- uses `IgnoreCase` for matching each of the criteria values.

		Sample cURL usages:
		-------------------

		curl http://localhost:8080/v1/invoices
		curl http://localhost:8080/v1/invoices?invoice_number=INV-001&po_number=PO-0
		curl http://localhost:8080/v1/invoices?invoice_number=inv-001&po_number=po-0
		curl http://localhost:8080/v1/invoices?invoice_number=INV-003
		curl http://localhost:8080/v1/invoices?invoice_number=inv-004
		curl http://localhost:8080/v1/invoices?invoice_number=inv
		curl http://localhost:8080/v1/invoices?po_number=PO-005
		curl http://localhost:8080/v1/invoices?po_number=po-006
		curl http://localhost:8080/v1/invoices?po_number=po
		curl http://localhost:8080/v1/invoices?invoice_number=INV&po_number=PO&offset=0&limit=10
		curl http://localhost:8080/v1/invoices?invoice_number=inv&po_number=po&offset=1&limit=10
		curl http://localhost:8080/v1/invoices?invoice_number=INV&po_number=PO&offset=2&limit=10
	*/
	//---------------------------------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<InvoiceResource>> find(
		@RequestParam(name = "invoice_number", required = false, defaultValue = CRITERION) String invoiceNumber,
		@RequestParam(name = "po_number", required = false, defaultValue = CRITERION) String poNumber,
		@RequestParam(name = "offset", required = false, defaultValue = OFFSET) Integer offset,
		@RequestParam(name = "limit", required = false, defaultValue = LIMIT) Integer limit) {

		InvoiceCriteria criteria = InvoiceCriteria.builder()
			.invoiceNumber(invoiceNumber)
			.poNumber(poNumber)
			.build();

		List<InvoiceBean> invoices = invoiceService.find(criteria, offset, limit);

		return new ResponseEntity<>(toResourceList(invoices), HttpStatus.OK);
	}

	//---------------------------------------------------------------------------------------------
	/*	POST API to add an invoice. Returns the added invoice, with `id` and `created_at` values.

		Sample cURL usage:
		-------------------

		curl -X POST -H 'Content-Type: application/json' -d \
		'{ 
			"invoice_number": "ABC12345", 
			"po_number": "X1B23C4D5E", 
			"due_date": "2017-03-15", 
			"amount_cents": 100000
		}' \
		http://localhost:8080/v1/invoices
	*/
	//---------------------------------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<InvoiceResource> add(@Valid @RequestBody InvoiceResource invoiceToAdd) {

		InvoiceBean addedInvoice = invoiceService.add(toBean(invoiceToAdd));

		return new ResponseEntity<>(toResource(addedInvoice), HttpStatus.OK);
	}

	//---------------------------------------------------------------------------------------------
	private InvoiceBean toBean(InvoiceResource invoice) {
		return modelMapper.map(invoice, InvoiceBean.class);
	}	

	//---------------------------------------------------------------------------------------------
	private InvoiceResource toResource(InvoiceBean invoice) {
		return modelMapper.map(invoice, InvoiceResource.class);
	}	

	//---------------------------------------------------------------------------------------------
	private List<InvoiceResource> toResourceList(List<InvoiceBean> invoices) {
		return invoices.stream()
			.map(invoice -> toResource(invoice))
			.collect(Collectors.toList());
	}	
}
