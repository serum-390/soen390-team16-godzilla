import { DataGrid } from "@material-ui/data-grid";
//import CustomToolbar from './tables/CustomToolbar';
import { useState } from 'react';
import { makeStyles } from '@material-ui/core';
import { SpinBeforeLoading } from "./inventory/Inventory";
import CustomerForm from "../Forms/CustomerForm";
import NewSalesOrderForm from "../Forms/NewSalesOrderForm";
import axios from "axios";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    padding: theme.spacing(1),
  },
  sort: {
    margin: theme.spacing(1),
    textTransform: 'none',
  },
}));

const getSales = async () => {
  const api = '/api/orders/?type=sales';
  const got = await fetch(api);
  const json = await got.json();
  return json || [];
};

const updateSales = async data => {
  try {
    const api = `/api/orders/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const insertSales = async data => {
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

const deleteSales = async id => {
  try {
    const api = `/api/orders/${id}`;
    const inserted = await axios.delete(api);
    console.log(`STATUS CODE: ${inserted.status}`);
    console.log(`DATA: ${inserted.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }
};

const salesColumns = [
  { field: 'id', headerName: 'Order #', width: 110 },
  { field: 'createdDate', headerName: 'Created Date', width: 130 },
  { field: 'dueDate', headerName: 'Due Date', width: 110 },
  { field: 'deliveryLocation', headerName: 'Delivery Location', width: 130 },
  { field: 'orderType', headerName: 'Order Type', width: 90 },
  { field: 'status', headerName: 'Status', width: 110 },
  {
    field: 'modify',
    headerName: 'Modify',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        <NewSalesOrderForm
          initialButton='EDIT'
          dialogTitle={'Order Information'}
          dialogContentText={'Please enter the information you would like to modify'}
          submitButton='Submit'
          onSubmit={params.value}
        />
      </div>
    ),
  },
  {
    field: 'delete',
    headerName: 'Delete',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        <Button variant="contained" onClick={params.value} color="primary" style={{ float: 'right' }}>
          Delete
        </Button>
      </div>
    ),
  }
];

const FilledSalesView = ({ salesOrders, classes }) => {
  let orders = [];

  let updateRow = (sales, updatedSales, toUpdate) => {
    if (toUpdate) {
      updateSales({
        id: sales.id,
        createdDate: updatedSales.createdDate === "" ? sales.createdDate : updatedSales.createdDate,
        dueDate: updatedSales.dueDate === "" ? sales.dueDate : updatedSales.dueDate,
        deliveryLocation: updatedSales.deliveryLocation === "" ? sales.deliveryLocation : updatedSales.deliveryLocation,
        orderType: updatedSales.orderType === "" ? sales.orderType : updatedSales.orderType,
        status: updatedSales.status === "" ? sales.status : updatedSales.status,
        items: sales.items
      })
    }
    return sales;
  };
  salesOrders.map(sales => (
    orders.push({
      id: sales.id,
      createdDate: sales.createdDate,
      dueDate: sales.dueDate,
      deliveryLocation: sales.deliveryLocation,
      orderType: sales.orderType,
      status: sales.status,
      modify: (updatedSales, toUpdate) => updateRow(sales, updatedSales, toUpdate),
      delete: () => deleteSales(sales.id)
    })));

  return (
    <DataGrid
      rows={orders}
      columns={salesColumns}
      pageSize={9}
      /*components={{ Toolbar: CustomToolbar}}*/
    />
  );
};

const LoadedSalesView = ({ classes, order, salesContacts, reload}) => {
  return (
  <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Sales Department</h1>
      <div style={{ height: 720, width: '45%', float: 'left', display: 'table'}}>

      <div style={{width: '100%', display: 'table-row' }}>
        <h2 style={{ float: 'left'}}>Customers</h2>
        <div style={{ float: 'right'}}>
            <CustomerForm
            onSubmit={(data) => insertContact(data, reload)}
            initialButton='Add New Customer'
            dialogTitle='Add New Customer '
            dialogContentText='Please enter the following information below to add a new customer: '
            submitButton='Save' />
        </div>
      </div>
      <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
        <FilledContactView
            salesContacts={salesContacts}
            classes={classes}
            reload={reload}
        />
       </div>
      </div>

      <div style={{ height: 720, width: '50%', float: 'right', display: 'table' }}>
        <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Sales Orders</h2>
          <div style={{ float: 'right'}}>
            <NewSalesOrderForm
              initialButton='Add New Sales Order'
              dialogTitle='New Sales Order '
              dialogContentText='Please enter the following information below to add a new sales order: '
              submitButton='Submit'
              onSubmit={(data) => insertSales(data)}
            />
          </div>
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <FilledSalesView
            salesOrders={order}
            classes={classes}
          />
        </div>
      </div>
  </div>
  );
};

const FilledContactView = (props) => {
  let contacts = [];
  let updateRow = (item, updatedItem, toUpdate) => {
    if (toUpdate) {
      updateContact({
        id: item.id,
        companyName: updatedItem.companyName === "" ? item.companyName : updatedItem.companyName,
        contactName: updatedItem.contactName === "" ? item.contactName : updatedItem.contactName,
        address: updatedItem.address === "" ? item.address : updatedItem.address,
        contact: updatedItem.contact === "" ? item.contact : updatedItem.contact
      }, props.reload)
    }
    return item;
  };

  props.salesContacts.map(item => (
    contacts.push({
      id: item.id,
      companyName: item.companyName,
      contactName: item.contactName,
      address: item.address,
      contact: item.contact,
      modify: (updatedItem, toUpdate) => updateRow(item, updatedItem, toUpdate),
      delete: () => deleteContact(item.id, props.reload)
    })));

  return (
    <DataGrid 
      rows={contacts} 
      columns={contactColumn} 
      pageSize={9} 
      /*components={{ Toolbar: CustomToolbar}}*/
    />
  );
};

const contactColumn = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'companyName', headerName: 'Company Name', width: 130 },
  { field: 'contactName', headerName: 'Customer', width: 130 },
  { field: 'address', headerName: 'Address', width: 130 },
  { field: 'contact', headerName: 'Contact #', width: 130 },
  {
    field: 'modify',
    headerName: 'Modify',
    width: 100,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          <CustomerForm
            onSubmit={params.value}
            splitAddress={params.getValue('address').split(",")[0] || ''}
            splitCity={params.getValue('address').split(",")[1] || ''}
            splitPostal={params.getValue('address').split(",")[2] || ''}
            splitProvince={params.getValue('address').split(",")[3] || ''}
            initialButton='Edit'
            dialogTitle={'Customer Information: ID(' + params.getValue('id') + ')'}
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

const getContact = async () => {
  const api = '/api/salescontact/';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
};

const updateContact = async (data, reload) => {
  try {
    const api = `/api/salescontact/${data.id}`;
    const updated = await axios.put(api, data);
    console.log(`STATUS CODE: ${updated.status}`);
    console.log(`DATA: ${updated.data || "Nothing"}`);
  } catch (err) {
    console.log(err);
    return err;
  }

  reload();
};

const insertContact = async (data, reload) => {
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


const deleteContact = async (id, reload) => {
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


const Sales = () => {
  const classes = useStyles();
  const [salesContacts, setContact] = useState([]);
  const [order, setSales] = useState([]);
  const [loading, setDoneLoading] = useState(true); 

  const waitForGetRequest = async () => {
    getContact().then(ven => setContact(ven));
    getSales().then(sales => setSales(sales));
    setDoneLoading(false);
  }

  function reload(){
    setDoneLoading(true);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={500} awaiting={waitForGetRequest}>

      <LoadedSalesView classes={classes} order={order} salesContacts={salesContacts} reload={reload}/> 
    </SpinBeforeLoading> :
    <div>
      
      <LoadedSalesView classes={classes}  order={order}  salesContacts={salesContacts} reload={reload}/>
    </div>
   
  );
}
export { Sales, FilledSalesView, FilledContactView, SpinBeforeLoading };
export default Sales;
