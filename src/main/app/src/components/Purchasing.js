import { Button, makeStyles } from '@material-ui/core';
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

const vendorColumns = [
  { field: 'id', headerName: 'ID', width: 130 },
  { field: 'vendorName', headerName: 'Vendor', width: 130 },
  { field: 'vendorAddress', headerName: 'Address', width: 130 },
  { field: 'vendorPhone', headerName: 'Contact #', width: 130 }
];

const vendorRows = [
  { id: 1, vendorName: 'Vendor1', vendorAddress: '????', vendorPhone: "123-456-7890" },
  { id: 2, vendorName: 'Vendor2', vendorAddress: '????', vendorPhone: "123-456-7890" }
];

const orderColumns = [
  { field: 'id', headerName: 'Order #', width: 130 },
  { field: 'vendorName', headerName: 'Vendor', width: 130 },
  { field: 'items', headerName: 'Items', width: 130 },
  { field: 'timestamp', headerName: 'Timestamp', width: 130 },
  { field: 'cost', headerName: 'Cost', width: 130 }
];

const orderRows = [
  { id: 1, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100" },
  { id: 2, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100" },
  { id: 3, vendorName: 'Vendor2', items: '????', timestamp: "01/31/2021", cost: "$200" },
  { id: 4, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100" }
];

const LoadedView = () => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Purchase Department</h1>
      <div style={{ height: 600, width: '45%', float: 'left' }}>
        <h2 style={{ float: 'left' }}>Vendors</h2>
        <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={() => { AddNewVendor(); }}>
          Add New Vendor
                    </Button>
        <DataGrid rows={vendorRows} columns={vendorColumns} pageSize={4} onRowClick={(newSelection) => { ShowVendorDetail(newSelection); }} />
      </div>
      <div style={{ height: 600, width: '45%', float: 'right' }}>
        <div>
          <h2 style={{ float: 'left' }}>Purchase Orders</h2>
          <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={() => { AddNewPurchaseOrder(); }}>
            Add New Purchase Order
                    </Button>
        </div>
        <DataGrid rows={orderRows} columns={orderColumns} pageSize={4} onRowClick={(newSelection) => { ShowPurchaseOrderDetail(newSelection); }} />
      </div>
    </div>
  );
}

function AddNewVendor() {
  alert('clicked');
}

function AddNewPurchaseOrder() {
  alert('clicked');
}

function ShowVendorDetail({ row }) {
  alert('clicked ' + row.id + " " + row.vendorName);
}

function ShowPurchaseOrderDetail({ row }) {
  alert('clicked ' + row.id + " " + row.vendorName);
}

function Purchase() {

  const classes = useStyles();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(false);
  }, []);


  return loading ? <Spinner />
                 : <LoadedView classes={classes} />;
}

export default Purchase;