import { DataGrid } from "@material-ui/data-grid";
import CustomToolbar from './tables/CustomToolbar';
import { useState } from 'react';
import { makeStyles } from '@material-ui/core';
import { SpinBeforeLoading } from "./inventory/Inventory";
import CustomerForm from "../Forms/CustomerForm";
import NewSalesOrderForm from "../Forms/NewSalesOrderForm";
import axios from "axios";
import Button from "@material-ui/core/Button";

const cols = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'companyName', headerName: 'Company Name', width: 120 },
  { field: 'customerName', headerName: 'Customer', width: 120 },
  { field: 'customerAddress', headerName: 'Address', width: 120 },
  { field: 'customerPhone', headerName: 'Contact #', width: 120 },
  {
    headerName: 'Modify',
    width: 120,
    renderCell: params => (


      <div style={{ margin: 'auto' }}>
        {
          /*<Button variant='contained'
           color='secondary'
           onClick={params.value.onClick}>
           show</Button>
           */
          <CustomerForm
            initialButton='Edit'
            dialogTitle={'Customer Information: ID(' + params.getValue('id') + ')'}
            dialogContentText='Please enter any information you would like to modify: '
            customerCompanyName={params.getValue('companyName') || ''}
            customerName={params.getValue('customerName') || ''}
            customerAddress={params.getValue('customerAddress').split(",")[0] || ''}
            customerCity={params.getValue('customerAddress').split(",")[1] || ''}
            customerPostal={params.getValue('customerAddress').split(",")[2] || ''}
            customerProvince={params.getValue('customerAddress').split(",")[3] || ''}
            customerPhone={params.getValue('customerPhone') || ''}
            submitButton='Update'
            deleteButton='Delete'
          />
        }
      </div>
    ),
  },
];

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
  const api = '/api/orders/?type=sale';
  const got = await fetch(api);
  const json = got.status === 200 ? await got.json() : [];
  return json;
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


const SalesGrid = ({ className, columns, rows, onRowClick }) => {
  return (
    <div className={className}>
      <h1 style={{ textAlign: "center" }}>Sales</h1>
      <div style={{ height: 720, width: '45%', float: 'left', display: 'table' }}>
       <div style={{width: '100%', display: 'table-row' }}>
          <h2 style={{ float: 'left' }}>Customer</h2>
          <div style={{ float: 'right'}}>
            <CustomerForm
              initialButton='Add New Customer'
              dialogTitle='Add New Customer '
              dialogContentText='Please enter the following information below to add a new customer: '
              submitButton='Save' />
          </div>  
        </div>
        <div style={{ height: '100%', width: '100%', display: 'table-row' }}>
          <DataGrid
            columns={columns}
            rows={customerRows}
            onRowClick={onRowClick}
            components={{ Toolbar: CustomToolbar}}
          />
        </div>
      </div>
    </div>
  );
};

const customerRows = [
  { id: 1, companyName: 'Company A', customerName: 'Customer1', customerAddress: '1444 Rue Mackay,Montreal,H3G2M2,QC', customerPhone: "1234567000" },
  { id: 2, companyName: 'Company B', customerName: 'Customer2', customerAddress: '666 Ontario St,Toronto,M4X1N1,ON', customerPhone: "1233207000" }
];

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
      components={{ Toolbar: CustomToolbar}}
    />
  );
};

const LoadedSalesView = ({ classes, order }) => {
  return (
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
  );
};

const Sales = () => {
  const classes = useStyles();
  const [order, setSales] = useState([]);
  const [loading, setDoneLoading] = useState(true);

  const waitForGetRequest = async () => {
    getSales().then(sales => setSales(sales));
    setDoneLoading(false);
  }

  return (
    (loading) ?
    <SpinBeforeLoading minLoadingTime={500} awaiting={waitForGetRequest}>
      <SalesGrid
        className={classes.root}
        columns={cols}
      />
      <LoadedSalesView classes={classes} order={order} />
    </SpinBeforeLoading> :
    <div>
      <SalesGrid
        className={classes.root}
        columns={cols}
      />
      <LoadedSalesView classes={classes} order={order} />
    </div>
   
  );
}
export { Sales, FilledSalesView, SpinBeforeLoading };
export default Sales;
