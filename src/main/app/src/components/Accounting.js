import { makeStyles, TextField } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React, { useEffect, useState } from 'react';
import { Spinner } from './inventory/Inventory';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    padding: theme.spacing(2),
  },
  sort: {
    margin: theme.spacing(1),
    textTransform: 'none',
  },
}));

const salesColumns = [
  { field: 'id', headerName: 'Sales Order', width: 130 },
  { field: 'customerName', headerName: 'Customer Name', width: 130 },
  { field: 'invoiceDate', headerName: 'Invoice Date', width: 130 },
  { field: 'dueDate', headerName: 'Due Date', width: 130 },
  { field: 'status', headerName: 'Status', width: 130 },
  { field: 'amount', headerName: 'Amount', width: 130 }
];

const salesRows = [
  { id: 1, customerName: 'Customer 1', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Paid', amount: '$1000'},
  { id: 2, customerName: 'Customer 2', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Overdue', amount: '$600'},
  { id: 3, customerName: 'Customer 3', invoiceDate: '05/10/19', dueDate: '05/10/21', status: 'Pending', amount: '$7700'},
  { id: 4, customerName: 'Customer 4', invoiceDate: '05/11/19', dueDate: '05/11/22', status: 'Paid', amount: '$1750'},
  { id: 5, customerName: 'Customer 5', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Overdue', amount: '$500'},
  { id: 6, customerName: 'Customer 6', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Paid', amount: '$4900'}
];

const purchaseColumns = [
  { field: 'id', headerName: 'Purchase Order', width: 160 },
  { field: 'vendorName', headerName: 'Vendor Name', width: 130 },
  { field: 'invoiceDate', headerName: 'Invoice Date', width: 130 },
  { field: 'dueDate', headerName: 'Due Date', width: 130 },
  { field: 'status', headerName: 'Status', width: 130 },
  { field: 'amount', headerName: 'Amount', width: 130 }
];

const purchaseRows = [
  { id: 1, vendorName: 'Vendor 1', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Paid', amount: '$1000'},
  { id: 2, vendorName: 'Vendor 2', invoiceDate: '05/10/19', dueDate: '05/10/19', status: 'Pending', amount: '$600'},
  { id: 3, vendorName: 'Vendor 3', invoiceDate: '05/10/19', dueDate: '05/10/21', status: 'Overdue', amount: '$7700'},
  { id: 4, vendorName: 'Vendor 4', invoiceDate: '05/11/19', dueDate: '05/11/22', status: 'Paid', amount: '$1750'},
];

const LoadedView = () => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Accounting Department</h1>
      <div style={{ height: 600, width: '45%', float: 'left' }}>
        <h2 style={{ float: 'left' }}>Sales Invoices</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }}/>
        <DataGrid rows={salesRows} columns={salesColumns} pageSize={9} />
      </div>
      <div style={{ height: 600, width: '45%', float: 'right' }}>
        <h2 style={{ float: 'left' }}>Purchase Invoice</h2>
        <TextField id="filled-basic" label="Search" variant="filled" style={{ float: 'right' }}/>
        <DataGrid rows={purchaseRows} columns={purchaseColumns} pageSize={9}/>
      </div>
    </div>
  );
}

function Accounting() {

  const classes = useStyles();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, []);


  return loading ? <Spinner />
                 : <LoadedView classes={classes} />;
}

export default Accounting;