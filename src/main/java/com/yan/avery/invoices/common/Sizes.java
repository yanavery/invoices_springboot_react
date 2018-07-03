//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

package com.yan.avery.invoices.common;

//=================================================================================================
public class Sizes {

	public static final int MIN_LENGTH_INVOICE_NUMBER = 1;
	public static final int MAX_LENGTH_INVOICE_NUMBER = 64;

	public static final int MIN_LENGTH_PO_NUMBER = 1;
	public static final int MAX_LENGTH_PO_NUMBER = 64;

	public static final int MIN_AMOUNT_CENTS = 0;
	public static final int MAX_AMOUNT_CENTS = 99999999; // basically $1,000,000
}
