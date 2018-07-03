//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

import React from 'react';
import DataForm from './DataForm';

//=================================================================================================
class InvoiceAdd extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    const schema = [
        { name: 'invoice_number', title: 'Invoice #', required: true },
        { name: 'po_number', title: 'PO #', required: true },
        { name: 'due_date', title: 'Due Date', type: 'date', required: true },
        { name: 'amount_cents', title: 'Amount ($)', type: 'money', required: true }
    ];

    return (
      <DataForm title='Add Invoice' apiEndPoint='/v1/invoices' schema={schema}/>
    );
  }
};

export default InvoiceAdd;
