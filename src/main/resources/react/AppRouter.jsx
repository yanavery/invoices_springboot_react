//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

import React from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import InvoiceList from './InvoiceList';
import InvoiceAdd from './InvoiceAdd';

//=================================================================================================
const AppRouter = () => (
  <Router>
    <div>
      <ul>
        <li>
          <Link to='/'>Home</Link>
        </li>
        <li>
          <Link to='/list'>Invoice List</Link>
        </li>
        <li>
          <Link to='/add'>Add Invoice</Link>
        </li>
      </ul>

      <hr />

      <Route exact path='/' component={Home} />
      <Route path='/list' component={InvoiceList} />
      <Route path='/add' component={InvoiceAdd} />
    </div>
  </Router>
);

//=================================================================================================
const Home = () => (
  <div>
    <h1>Welcome to the Simple Invoices Application</h1>
  </div>
);

export default AppRouter;
