//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

import React from 'react';
import request from 'superagent';
import numeral from 'numeral';

//=================================================================================================
/*
  Generic DataGrid component. Used to compose specialized data grids by providing the
  following input properties:

    - title:

        Title of this data grid. Displays at the top of the grid.

    - apiEndPoint: 

        Data provider URL for the GET API end-point used to retrieve data for
        populating this grid's content.

    - schema:

        Array of data columns to be used to populate and render the grid. Each array
        element has the following attributes:

          name: (Required) Name of JSON attribute returned by the API for this column.
          title: (Required) Title to be used for this column's header.
          width: (Required) Width to be used for this column.
          filter: (Optional) Boolean indicating if this column can be filtered on.
          type: (Optional) Used to specify special data renderers such as 'money'.
*/
//=================================================================================================
class DataGrid extends React.Component {

  //-----------------------------------------------------------------------------------------------
  constructor(props) {
    super(props);

    this.state = {
      data: []
    };

    this.query = {
      offset: 0,
      limit: 5
    };

    this.handleSearch = this.handleSearch.bind(this);
    this.handleMoveToPreviousPage = this.handleMoveToPreviousPage.bind(this);
    this.handleMoveToNextPage = this.handleMoveToNextPage.bind(this);
  }

  //-----------------------------------------------------------------------------------------------
  handleSearch(event) {
    this.props['schema'].map((column) => {
      if (column['filter']) {
        this.query[column['name']] = event.target.form[column['name']].value;
      }
    });
    this.query['offset'] = 0;
    this.loadData();
  }

  //-----------------------------------------------------------------------------------------------
  handleMoveToPreviousPage(event) {
    this.query['offset'] = Math.max(0, this.query['offset'] - 1);
    this.loadData();
  }

  //-----------------------------------------------------------------------------------------------
  handleMoveToNextPage(event) {
    this.query['offset']++;
    this.loadData();
  }

  //-----------------------------------------------------------------------------------------------
  canMoveToPreviousPage() {
    return this.query['offset'] > 0;
  }

  //-----------------------------------------------------------------------------------------------
  canMoveToNextPage() {
    return this.state['data'].length > 0 && this.state['data'].length >= this.query['limit'];
  }

  //-----------------------------------------------------------------------------------------------
  loadData() {
    request
      .get(this.props['apiEndPoint'])
      .query(this.query)
      .end((err, res) => {
        // no error handling for now, keeping code simple.
        const data = JSON.parse(res.text);
        this.setState({ data: data });
    });
  }

  //-----------------------------------------------------------------------------------------------
  componentDidMount() {
    this.loadData();
  }

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      <div className='data-list'>
        <h1>{this.props['title']}</h1>

        <DataGridFilterForm
          schema={this.props['schema']}
          searchEventHandler={this.handleSearch}/>

        <table>
          <tbody>

            <DataGridHeaderRow
              schema={this.props['schema']}/>

            <DataGridDataRows
              schema={this.props['schema']}
              data={this.state['data']}/>

          </tbody>
        </table>

        <DataGridNavigationForm
          canMoveToPreviousPage={this.canMoveToPreviousPage()}
          previousPageEventHandler={this.handleMoveToPreviousPage}
          canMoveToNextPage={this.canMoveToNextPage()}
          nextPageEventHandler={this.handleMoveToNextPage}/>

      </div>
    );
  }
};

//=================================================================================================
class DataGridFilterForm extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    const filters = this.props['schema'].map((column) => {
      if (column['filter']) {
        return (
          <label key={column['name']}>
            {column['title']}
            <input type='text' name={column['name']}/>
          </label>
        )
      }
    });

    return (
      <div>
        <form>
          {filters}
          <input type='button' value='Find' onClick={this.props['searchEventHandler']}/>
        </form>
      </div>
    );
  }
};

//=================================================================================================
class DataGridNavigationForm extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      <div>
        <form>
          <input type='button' value='Previous'
            disabled={!this.props['canMoveToPreviousPage']}
            onClick={this.props['previousPageEventHandler']}/>

          <input type='button' value='Next'
            disabled={!this.props['canMoveToNextPage']}
            onClick={this.props['nextPageEventHandler']}/>
        </form>
      </div>
    );
  }
};

//=================================================================================================
class DataGridHeaderRow extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    const columns = this.props['schema'].map((column) => {
      var thStyle = {
        width: column['width']
      };

      return (
        <th key={column['name']} style={thStyle}>{column['title']}</th>
      )
    });

    return (
      <tr>
        {columns}
      </tr>
    );
  }
};

//=================================================================================================
class DataGridDataRows extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      this.props['data'].map((row) => (
        <tr key={row['id']}>
        {
          this.props['schema'].map((column) => {
            if (column['type'] === 'money') {
              return (
                <td key={column['name']}>
                  {numeral(row[column['name']] / 100).format('$0,0.00')}
                </td>
              )
            } else {
              return (
                <td key={column['name']}>
                  {row[column['name']]}
                </td>
              )
            }
          })
        }
        </tr>
      ))
    );
  }
};

export default DataGrid;
