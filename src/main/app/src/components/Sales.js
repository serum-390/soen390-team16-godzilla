import { Button } from "@material-ui/core";
import { DataGrid } from "@material-ui/data-grid";
import { useState } from 'react';
import useSalesPageStyles from "../styles/salesPageStyles";
import { SpinBeforeLoading } from "./inventory/Inventory";
import  CustomerForm  from "../Forms/CustomerForm";
import PurchaseOrderDetailsForm from "../Forms/PurchaseOrderDetailsForm";
import NewSalesOrderForm from "../Forms/NewSalesOrderForm";


const cols = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'customerName', headerName: 'Customer', width: 130 },
  { field: 'customerAddress', headerName: 'Address', width: 130 },
  { field: 'customerPhone', headerName: 'Contact #', width: 130 },
  {
    field: 'salesOrders',
    headerName: 'Modify',
    width: 160,
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
         dialogTitle= {'Customer Information: ID('+ params.getValue('id')+')'}
         dialogContentText='Please enter any information you would like to modify: ' 
         customerName={params.getValue('customerName') || ''}
         customerAddress={params.getValue('customerAddress').split(",")[0] || ''}
         customerCity={params.getValue('customerAddress').split(",")[1] || ''}
         customerPostal={params.getValue('customerAddress').split(",")[2] || ''}
         customerProvince ={params.getValue('customerAddress').split(",")[3] || ''} //not yet done.
         customerPhone={params.getValue('customerPhone') || ''}//not yet done.
         submitButton='Update'  
         deleteButton='Delete' 
         /> 
        }  
      </div>
    ),
  },
];


const getSales = async () => {
  const api = '/api/Sales/';
  const got = await fetch(api);
  const json = await got.json();

  if (!json.sales) return [];

  return json.sales.map(sale => {
    sale.salesOrders = {
      onClick: () => {},
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
        <CustomerForm  
        initialButton='Add New Customer'  
        dialogTitle='Add New Customer '  
        dialogContentText='Please enter the following information below to add a new customer: ' 
        submitButton='Save'/>
        </div>
        <DataGrid
          columns={columns}
          rows={customerRows}
          onRowClick={onRowClick}
        />
      </div>
      <div style={{ height: 600, width: '50%', float: 'right' }}>
      <div >
          <h2 style={{ float: 'left' }}>Sales Orders</h2>
          <NewSalesOrderForm  
            initialButton='Add New Sales Order'  
            dialogTitle='New Sales Order '  
            dialogContentText='Please enter the following information below to add a new sales order: '
          />

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

const customerRows = [
  { id: 1, customerName: 'Customer1', customerAddress: '1444 Rue Mackay,Montreal,H3G2M2,QC', customerPhone: "1234567000" },
  { id: 2, customerName: 'Customer2', customerAddress: '666 Ontario St,Toronto,M4X1N1,ON', customerPhone: "1233207000" }
];

const salesColumns = [
  { field: 'id', headerName: 'Order #', width: 110 },
  { field: 'customerName', headerName: 'Customer', width: 130 },
  { field: 'items', headerName: 'Products', width: 110 },
  { field: 'timestamp', headerName: 'Timestamp', width: 130 },
  { field: 'cost', headerName: 'Cost', width: 90 },
  { field: 'status', headerName: 'Status', width: 110 },
  { field: 'SalesOrderDetails',
    headerName: 'Details',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        <PurchaseOrderDetailsForm
                    orderID={params.getValue('id') || ''}
                    vendorName={params.getValue('customerName') || ''}
                    orderItems={params.getValue('items') || ''}
                    orderTimestamp={params.getValue('timestamp') || ''}
                    orderCost={params.getValue('cost') || ''}
                    orderStatus={params.getValue('status') || ''}
                    initialButton='View'
                    dialogTitle={'Order Information - Order #' + params.getValue('id')}
                    dialogContentText={'Data: '}
                    submitButton='Cancel Order'
                    TypeName = 'Customer'
         />
      </div>
    ),
  }
];

const salesRows = [
  { id: 1, customerName: 'Customer 1', items: '????', timestamp: "01/31/2021", cost: "$100", status: "Completed" },
  { id: 2, customerName: 'Customer 1', items: '????', timestamp: "01/31/2021", cost: "$100", status: "Completed"},
  { id: 3, customerName: 'Customer 2', items: '????', timestamp: "01/31/2021", cost: "$200", status: "Ongoing"},
  { id: 4, customerName: 'Customer 3', items: '????', timestamp: "01/31/2021", cost: "$100",status: "Ongoing" }
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
