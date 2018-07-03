---------------------------------------------------------------------------------------------
-- SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
---------------------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------
-- Create invoices DB table.
---------------------------------------------------------------------------------------------

CREATE TABLE invoices (
	id SERIAL,
	invoice_number VARCHAR(64) NOT NULL,
	po_number VARCHAR(64) NOT NULL,
	due_date DATE NOT NULL,
	amount_cents BIGINT NOT NULL,
	created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);
