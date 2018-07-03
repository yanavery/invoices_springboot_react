//=================================================================================================
// SpringBoot, SpringData & React Simple Invoices App - Copyright (C) 2018, Yan Avery
//=================================================================================================

import React from 'react';
import request from 'superagent';
import numeral from 'numeral';

//=================================================================================================
/*
  Generic DataForm component. Used to compose specialized data forms by providing the
  following input properties:

    - title:

        Title of this data grid. Displays at the top of the grid.

    - apiEndPoint: 

        Data provider URL for the GET API end-point used to retrieve data for
        populating this grid's content.

    - schema:

        Array of data columns to be used to populate and render the grid. Each array
        element has the following attributes:

          name: (Required) Name of API end-point JSON attribute associated with this form field.
          title: (Required) Title to be used for this form field.
          type: (Optional) Used to specify special field inputs such as 'money' or 'date'.
          required: (Optional) Boolean indicating if this field requires a value.
*/
//=================================================================================================
class DataForm extends React.Component {

  //-----------------------------------------------------------------------------------------------
  constructor(props) {
    super(props);

    this.state = {
      canSave: false,
      dataSaved: false
    };

    this.postData = {};

    this.handleChange = this.handleChange.bind(this);
    this.handleSave = this.handleSave.bind(this);
  }

  //-----------------------------------------------------------------------------------------------
  handleChange(event) {
    for (var i in this.props['schema']) {
      const column = this.props['schema'][i];
      const value = event.target.form[column['name']].value;

      if (column['required'] && value === '') {
        // don't call setState() unless necessary, to avoid unecessary render() calls.
        if (this.state['canSave']) {
          this.setState({ canSave: false });
        }
        // as soon as one required field validation fails, stop processing.
        return;
      }
    }

    // if this point is reached, all required field validations haved passed.
    // don't call setState() unless necessary, to avoid unecessary render() calls.
    if (!this.state['canSave']) {
      this.setState({ canSave: true });
    }
  }

  //-----------------------------------------------------------------------------------------------
  handleSave(event) {
    this.props['schema'].map((column) => {
      const value = event.target.form[column['name']].value;
      if (column['type'] === 'money') {
        this.postData[column['name']] = numeral(value).value() * 100;
      } else {
        this.postData[column['name']] = value;
      }
    });
    this.saveData();
  }

  //-----------------------------------------------------------------------------------------------
  saveData() {
    request
      .post(this.props['apiEndPoint'])
      .send(this.postData)
      .end((err, res) => {
        // no error handling for now, keeping code simple.
        this.setState({ dataSaved: true });
    });
  }

  //-----------------------------------------------------------------------------------------------
  render() {
    return this.state['dataSaved'] ? this.renderDataSaved() : this.renderForm();
  }

  //-----------------------------------------------------------------------------------------------
  renderDataSaved() {
    // purposely not calling setState() so not to produce an unnecessary render() call.
    this.state['dataSaved'] = false;

    return (
      <h1>Data was saved!</h1>
    );
  }

  //-----------------------------------------------------------------------------------------------
  renderForm() {
    const fields = this.props['schema'].map((column) => {
      if (column['type'] === 'date') {
        return (
          <DataFormFieldDate key={column['name']} name={column['name']} title={column['title']}
            changeEventHandler={this.handleChange}/>
        );
      } else if (column['type'] === 'money') {
        return (
          <DataFormFieldMoney key={column['name']} name={column['name']} title={column['title']}
            changeEventHandler={this.handleChange}/>
        );
      } else {
        return (
          <DataFormFieldText key={column['name']} name={column['name']} title={column['title']}
            changeEventHandler={this.handleChange}/>
        );
      }
    });
    
    return (
      <div className='data-editor'>
        <h1>{this.props['title']}</h1>
        <form>
          {fields}
          <div className='data-editor-button'>
            <input type='button' value='Save'
              disabled={!this.state['canSave']}
              onClick={this.handleSave}/>
          </div>
        </form>
      </div>
    );
  }
}

//=================================================================================================
class DataFormFieldText extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      <div className='data-editor-field'>
        <label htmlFor={this.props['name']}>{this.props['title']}</label>
        <input id={this.props['name']} type='text' name={this.props['name']}
          onChange={this.props['changeEventHandler']}/>
      </div>
    );
  }
};

//=================================================================================================
class DataFormFieldDate extends React.Component {

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      <div className='data-editor-field'>
        <label htmlFor={this.props['name']}>{this.props['title']}</label>
        <input type='date' id={this.props['name']} name={this.props['name']}
          onChange={this.props['changeEventHandler']}/>
      </div>
    );
  }
};

//=================================================================================================
class DataFormFieldMoney extends React.Component {

  //-----------------------------------------------------------------------------------------------
  constructor(props) {
    super(props);

    this.onBlurHandler = this.onBlurHandler.bind(this);
    this.onFocusHandler = this.onFocusHandler.bind(this);
  }

  //-----------------------------------------------------------------------------------------------
  onBlurHandler(event) {
    var value = event.target.value;
    value = value.indexOf('.') ? value : value /= 100;
    value = numeral(value).format('$0,0.00')
    event.target.value = value;
  }

  //-----------------------------------------------------------------------------------------------
  onFocusHandler(event) {
    var value = event.target.value;
    value = numeral(value).value();
    if (value === 0) {
      value = '';
    }
    event.target.value = value;
  }

  //-----------------------------------------------------------------------------------------------
  render() {
    return (
      <div className='data-editor-field'>
        <label htmlFor={this.props['name']}>{this.props['title']}</label>
        <input type='text' id={this.props['name']}
          pattern='^\\d+(\\.|\\,)\\d{2}$'
          name={this.props['name']}
          onChange={this.props['changeEventHandler']}
          onBlur={this.onBlurHandler}
          onFocus={this.onFocusHandler}/>
      </div>
    );
  }
};

export default DataForm;
