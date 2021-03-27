import { Button, makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React, { useState } from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import VendorDetailsForm from "../Forms/VendorDetailsForm";
import NewPurchaseOrderForm from "../Forms/NewPurchaseOrderForm";
import PurchaseOrderDetailsForm from "../Forms/PurchaseOrderDetailsForm";
import axios from "axios";

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
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'companyName', headerName: 'Company Name', width: 130 },
  { field: 'contactName', headerName: 'Vendor', width: 130 },
  { field: 'address', headerName: 'Address', width: 130 },
  { field: 'contact', headerName: 'Contact #', width: 130 },
  {
    field: 'modify',
    headerName: 'Modify',
    width: 100,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          <VendorDetailsForm
            onSubmit={params.value}
            splitAddress={params.getValue('address').split(",")[0] || ''}
            splitCity={params.getValue('address').split(",")[1] || ''}
            splitPostal={params.getValue('address').split(",")[2] || ''}
            splitProvince={params.getValue('address').split(",")[3] || ''}
            initialButton='Edit'
            dialogTitle={'Vendor Information - (' + params.getValue('id') + ") " + params.getValue('contactName')}
            dialogContentText='Please enter any information you would like to modify: '
            submitButton='Update'
          />
        }
      </div>
    ),
  },
  {
    field: 'delete',
    headerName: 'Delete',
    width: 120,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        <Button variant="contained" onClick={params.value} color="primary" style={{ float: 'right' }}>
          Delete
        </Button>
      </div>
    ),
  },
];

const orderColumns = [
  { field: 'id', headerName: 'Order #', width: 70 },
  { field: 'createdDate', headerName: 'Create Date', width: 120 },
  { field: 'dueDate', headerName: 'Due Date', width: 120 },
  { field: 'deliveryLocation', headerName: 'Location', width: 130 },
  { field: 'status', headerName: 'Status', width: 110 },
  { field: 'items', headerName: 'Items', width: 110 },
  {
    field: 'purchaseOrderDetails',
    headerName: 'Details',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          <PurchaseOrderDetailsForm
            orderID={params.getValue('id') || ''}
            createdDate={params.getValue('createdDate') || ''}
            dueDate={params.getValue('dueDate') || ''}
            deliveryLocation={params.getValue('deliveryLocation') || ''}
            status={params.getValue('status') || ''}
            items={params.getValue('items') || ''}
            initialButton='View'
            dialogTitle={'Order Information - Order #' + params.getValue('id')}
            dialogContentText={'Data: '}
            submitButton='Cancel Order'
            TypeName='Company'
          />
        }
      </div>
    ),
  }
];

const getVendors = async () => {
  const api = '/api/vendorcontact/';
  const got = await fetch(api);
  const json = await got.json();
  return json || [];
};

const updateVendor = async data => {
  try {
    const api = `/api/vendorcontact/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const insertVendor = async data => {
  try {
    const api = `/api/vendorcontact/`;
    const inserted = await axios.post(api, data);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const deleteVendor = async id => {
  try {
    const api = `/api/vendorcontact/${id}`;
    const inserted = await axios.delete(api);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const getPurchaseOrders = async () => {
  const api = '/api/orders/?type=purchases';
  const got = await fetch(api);
  const json = await got.json();
  return json || [];
};

const insertPurchaseOrder = async data => {
  try {
    const api = `/api/orders/`;
    const inserted = await axios.post(api, data);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const FilledVendorView = ({ vendors }) => {
  let contacts = [];
  let updateRow = (item, updatedItem, toUpdate) => {
    if (toUpdate) {
      updateVendor({
        id: item.id,
        companyName: updatedItem.companyName === "" ? item.companyName : updatedItem.companyName,
        contactName: updatedItem.contactName === "" ? item.contactName : updatedItem.contactName,
        address: updatedItem.address === "" ? item.address : updatedItem.address,
        contact: updatedItem.contact === "" ? item.contact : updatedItem.contact
      })
    }
    return item;
  };

  vendors.map(item => (
    contacts.push({
      id: item.id,
      companyName: item.companyName,
      contactName: item.contactName,
      address: item.address,
      contact: item.contact,
      modify: (updatedItem, toUpdate) => updateRow(item, updatedItem, toUpdate),
      delete: () => deleteVendor(item.id)
    })));

  return (
    <DataGrid rows={contacts} columns={vendorColumns} pageSize={9} />
  );
};

const FilledOrderView = ({ orders }) => {
  let purchases = [];

  orders.map(item => (
    purchases.push({
      id: item.id,
      createdDate: item.createdDate,
      dueDate: item.dueDate,
      deliveryLocation: item.deliveryLocation,
      status: item.status,
      items: item.items,
    })));

  return (
    <DataGrid rows={purchases} columns={orderColumns} pageSize={9} />
  );
};

function LoadedView({ classes, vendors, orders }) {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Purchase Department</h1>
      <div style={{ height: 720, width: '45%', float: 'left', display: 'table'}}>

        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left'}}>Vendors</h2>
          <div style={{ float: 'right'}}>
            <VendorDetailsForm
              onSubmit={(data) => insertVendor(data)}
              initialButton='Add New Vendor'
              dialogTitle='Add New Vendor'
              dialogContentText='Please enter any information you would like to add: '
              submitButton='Confirm'
            />
          </div>
        </div>

        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <FilledVendorView
            vendors={vendors}
          />
        </div>
      </div>
      <div style={{ height: 720, width: '45%', float: 'right', display: 'table' }}>

        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Purchase Orders</h2>
          <div style={{ float: 'right'}}>
            <NewPurchaseOrderForm
              onSubmit={(data) => insertPurchaseOrder(data)}
              initialButton='Add New Purchase Order'
              dialogTitle='New Purchase Order'
              dialogContentText='Please enter any information you would like to modify: '
              submitButton='Order'
              vendors={vendors}
            />
          </div>
        </div>

        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <FilledOrderView
            orders={orders}
          />
        </div>     
      </div>
    </div>
  );
}

function Purchase() {
  const classes = useStyles();
  const [vendors, setVendors] = useState([]);
  const [orders, setOrders] = useState([]);
  const [loading, setDoneLoading] = useState(true);

  const waitForGetRequest = async () => {
    getVendors().then(ven => setVendors(ven));
    getPurchaseOrders().then(ord => setOrders(ord));
    setDoneLoading(false);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} vendors={vendors} orders={orders} />
    </SpinBeforeLoading> :
    <LoadedView classes={classes} vendors={vendors} orders={orders} />
  );
}

export default Purchase;
