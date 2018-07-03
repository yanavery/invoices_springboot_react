//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

import React from 'react';
import DataGrid from './DataGrid';

//=================================================================================================
class InvoiceList extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    const schema = [
        { name: 'invoice_number', title: 'Invoice #', width: '100px', filter: true },
        { name: 'po_number', title: 'PO #', width: '100px', filter: true },
        { name: 'due_date', title: 'Due Date', width: '100px' },
        { name: 'amount_cents', title: 'Amount ($)', width: '125px', type: 'money' },
        { name: 'created_at', title: 'Creation Date & Time', width: '200px' }
    ];

    return (
      <DataGrid title='Invoice List' apiEndPoint='/v1/invoices' schema={schema}/>
    );
  }
};

export default InvoiceList;
