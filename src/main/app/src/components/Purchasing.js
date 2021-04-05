import { Button, makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import CustomToolbar from './tables/CustomToolbar';
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

const getVendors = async () => {
  const api = '/api/vendorcontact/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const updateVendor = async (data, reload) => {
  try {
    const api = `/api/vendorcontact/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};

const insertVendor = async (data, reload) => {
  try {
    const api = `/api/vendorcontact/`;
    const inserted = await axios.post(api, data);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
  
  reload();
};

const deleteVendor = async (id, reload) => {
  try {
    const api = `/api/vendorcontact/${id}`;
    const inserted = await axios.delete(api);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};

const getPurchaseOrders = async () => {
  const api = '/api/orders/?type=purchases';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const insertPurchaseOrder = async (data, reload) => {
  try {
    const api = `/api/orders/`;
    const inserted = await axios.post(api, data);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};

const getInventory = async () => {
  const api = '/api/inventory/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const FilledVendorView = (props) => {
  let contacts = [];
  let updateRow = (item, updatedItem, toUpdate) => {
    if (toUpdate) {
      updateVendor({
        id: item.id,
        companyName: updatedItem.companyName === "" ? item.companyName : updatedItem.companyName,
        contactName: updatedItem.contactName === "" ? item.contactName : updatedItem.contactName,
        address: updatedItem.address === "" ? item.address : updatedItem.address,
        contact: updatedItem.contact === "" ? item.contact : updatedItem.contact
      }, props.reload)
    }
    return item;
  };

  props.vendors.map(item => (
    contacts.push({
      id: item.id,
      companyName: item.companyName,
      contactName: item.contactName,
      address: item.address,
      contact: item.contact,
      modify: (updatedItem, toUpdate) => updateRow(item, updatedItem, toUpdate),
      delete: () => deleteVendor(item.id, props.reload)
    })));

  return (
    <DataGrid rows={contacts} columns={vendorColumns} pageSize={9} components={{ Toolbar: CustomToolbar}}/>
  );
};

const FilledOrderView = ({ orders, orderColumns, inventory }) => {
  let purchases = [];

  orders.map(item => (
    purchases.push({
      id: item.id,
      createdDate: item.createdDate,
      dueDate: item.dueDate,
      deliveryLocation: item.deliveryLocation,
      status: item.status,
      itemsVisible: ShowItems(item.items, inventory),
      items: item.items,
    })));

  return (
    <DataGrid rows={purchases} columns={orderColumns} pageSize={9} components={{ Toolbar: CustomToolbar}}/>
  );
};

function ShowItems(items, inventory){
  let allItems = "";
  let count = 0;

  for(let i = 0; i < inventory.length; i++){
    let id = inventory[i]["id"];
    if(items[id] !== undefined){
      if(count > 0)
        allItems += ", ";

      allItems += inventory[i]["itemName"] + " ("+items[id]+")";

      count++;
    }
  }

  return allItems;
}

function LoadedView(props) {
  const orderColumns = [
    { field: 'id', headerName: '#', width: 60 },
    { field: 'createdDate', headerName: 'Create Date', width: 120 },
    { field: 'dueDate', headerName: 'Due Date', width: 120 },
    { field: 'deliveryLocation', headerName: 'Location', width: 130 },
    { field: 'status', headerName: 'Status', width: 110 },
    { field: 'itemsVisible', headerName: 'Items', width: 260 },
    { field: 'items', headerName: 'Items', width: 0, hide: true},
    {
      field: 'purchaseOrderDetails',
      headerName: 'Details',
      width: 120,
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
              inventory={props.inventory}
            />
          }
        </div>
      ),
    }
  ];

  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Purchase Department</h1>
      <div style={{ height: 720, width: '45%', float: 'left', display: 'table'}}>

        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left'}}>Vendors</h2>
          <div style={{ float: 'right'}}>
            <VendorDetailsForm
              onSubmit={(data, insert) => (insert) ? insertVendor(data, props.reload) : ''}
              initialButton='Add New Vendor'
              dialogTitle='Add New Vendor'
              dialogContentText='Please enter any information you would like to add: '
              submitButton='Confirm'
            />
          </div>
        </div>

        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <FilledVendorView
            vendors={props.vendors}
            reload={props.reload}
          />
        </div>
      </div>
      <div style={{ height: 720, width: '52%', float: 'right', display: 'table' }}>

        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Purchase Orders</h2>
          <div style={{ float: 'right'}}>
            <NewPurchaseOrderForm
              onSubmit={(data) => insertPurchaseOrder(data, props.reload)}
              initialButton='Add New Purchase Order'
              dialogTitle='New Purchase Order'
              dialogContentText='Please enter any information you would like to modify: '
              submitButton='Order'
              vendors={props.vendors}
              inventory={props.inventory}
            />
          </div>
        </div>

        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <FilledOrderView
            orders={props.orders}
            orderColumns={orderColumns}
            inventory={props.inventory}
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
  const [inventory, setInventory] = useState([]);
  const [loading, setLoading] = useState(true);

  const waitForGetRequest = async () => {
    getVendors().then(ven => setVendors(ven));
    getPurchaseOrders().then(ord => setOrders(ord));
    getInventory().then(inv => setInventory(inv));
    setLoading(false);
  }

  function reload(){
    setLoading(true);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} vendors={vendors} orders={orders} inventory={inventory} reload={reload} />
    </SpinBeforeLoading> :
    <LoadedView classes={classes} vendors={vendors} orders={orders} inventory={inventory} reload={reload} />
  );
}

export default Purchase;
