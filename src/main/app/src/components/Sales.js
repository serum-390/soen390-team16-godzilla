import { Button } from "@material-ui/core";
import { DataGrid } from "@material-ui/data-grid";
import { useState } from 'react';
import useSalesPageStyles from "../styles/salesPageStyles";
import { SpinBeforeLoading } from "./inventory/Inventory";
import  EditDialog  from "../Forms/EditForm";
import  CustomerForm  from "../Forms/CustomerForm";


const cols = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'vendorName', headerName: 'Customer', width: 130 },
  { field: 'vendorAddress', headerName: 'Address', width: 130 },
  { field: 'vendorPhone', headerName: 'Contact #', width: 130 },
  {
    field: 'salesOrders',
    headerName: 'Modify',
    width: 160,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          /*
          <Button variant='contained'
          color='secondary'
          onClick={params.value.onClick}>
          Show
          </Button>
          */
         <EditDialog/>
          
        } 
        
      </div>
    ),
  },
];

const getSales = async () => {
  const api = '/api/sales/';
  const got = await fetch(api);
  const json = await got.json();

  if (!json.sales) return [];

  return json.sales.map(sale => {
    sale.salesOrders = {
      onClick: () => alert(`Test: ${sale.vendorName}`),
    };
    return sale;
  });
};

const SalesGrid = ({ className, columns, rows, onRowClick }) => {
  return (
    
    <div className={className}>
      <h1>Sales</h1>
      <div style={{ height: 600, width: '45%', float: 'left' }}> 
        <div>
        <h2 style={{ float: 'left' }}>Customer</h2>
        <CustomerForm />
        </div>
        <DataGrid
          columns={columns}
          rows={rows}
          onRowClick={onRowClick}
        />
      </div>
      <div style={{ height: 600, width: '45%', float: 'right' }}>
      <div>
          <h2 style={{ float: 'left' }}>Sales Orders</h2>
          <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={() => { AddNewSaleOrder(); }}>
            Add New Sales Order
          </Button>
      </div>
        <DataGrid
          columns={salesColumns}
          rows={salesRows}
          onRowClick={onRowClick}
        />
      </div>
    </div>
  );
};

const salesColumns = [
  { field: 'id', headerName: 'Order #', width: 130 },
  { field: 'customerName', headerName: 'Customer', width: 130 },
  { field: 'items', headerName: 'Items', width: 130 },
  { field: 'timestamp', headerName: 'Timestamp', width: 130 },
  { field: 'cost', headerName: 'Cost', width: 130 }
];

const salesRows = [
  { id: 1, customerName: 'Customer 1', items: '????', timestamp: "01/31/2021", cost: "$100" },
  { id: 2, customerName: 'Customer 1', items: '????', timestamp: "01/31/2021", cost: "$100" },
  { id: 3, customerName: 'Customer 2', items: '????', timestamp: "01/31/2021", cost: "$200" },
  { id: 4, customerName: 'Customer 3', items: '????', timestamp: "01/31/2021", cost: "$100" }
];


function AddNewSaleOrder() {
  alert('clicked');
}

function Sales() {
  const classes = useSalesPageStyles();
  const [rows, setRows] = useState([]);

  const waitForGetRequest = async () => getSales().then(sales => setRows(sales));
  const handleRowclick = params => console.log(params);

  return (
    <SpinBeforeLoading minLoadingTime={500} awaiting={waitForGetRequest}>
      <SalesGrid
        className={classes.root}
        columns={cols}
        rows={rows}
        onRowClick={handleRowclick}
      />
    </SpinBeforeLoading>
  );
}

export default Sales;
