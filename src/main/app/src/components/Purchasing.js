import { Button, Dialog, makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import AddressInput from '../Forms/AddressInput';
import PhoneInput from '../Forms/PhoneInput';
import VendorDetailsForm from "../Forms/VendorDetailsForm";
import NewPurchaseOrderForm from "../Forms/NewPurchaseOrderForm";
import PurchaseOrderDetailsForm from "../Forms/PurchaseOrderDetailsForm";
import DeleteButton from "../Forms/DeleteButton";

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
  { field: 'vendorPhone', headerName: 'Contact #', width: 130 },
  {
    field: 'vendorDetails',
    headerName: 'Modify',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          /*<Button variant='contained'
           color='secondary'
           onClick={params.value.onClick}>
           show</Button> 
           */

          <VendorDetailsForm
            vendorID={params.getValue('id') || ''}
            vendorName={params.getValue('vendorName') || ''}
            vendorAddress={params.getValue('vendorAddress') || ''}
            vendorPhone={params.getValue('vendorPhone') || ''}
            initialButton='Edit'
            dialogTitle={'Vendor Information - (' + params.getValue('id') + ") " + params.getValue('vendorName')}
            dialogContentText='Please enter any information you would like to modify: '
            submitButton='Update'
          />
        }
      </div>
    ),
  },
  {
    field: 'deleteVendor',
    headerName: 'Delete',
    width: 130,
    renderCell: params => (
      <DeleteButton
        vendorName={params.getValue('vendorName') || ''}
      />
    ),
  },



];

const vendorRows = [
  { id: 1, vendorName: 'Vendor1', vendorAddress: '????', vendorPhone: "123-456-7890" },
  { id: 2, vendorName: 'Vendor2', vendorAddress: '????', vendorPhone: "123-456-7890" }
];

const orderColumns = [
  { field: 'id', headerName: 'Order #', width: 100 },
  { field: 'vendorName', headerName: 'Vendor', width: 100 },
  { field: 'items', headerName: 'Items', width: 140 },
  { field: 'timestamp', headerName: 'Timestamp', width: 130 },
  { field: 'cost', headerName: 'Cost', width: 100 },
  { field: 'status', headerName: 'Status', width: 110 },
  {
    field: 'purchaseOrderDetails',
    headerName: 'Details',
    width: 130,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
        {
          <PurchaseOrderDetailsForm
            orderID={params.getValue('id') || ''}
            vendorName={params.getValue('vendorName') || ''}
            orderItems={params.getValue('items') || ''}
            orderTimestamp={params.getValue('timestamp') || ''}
            orderCost={params.getValue('cost') || ''}
            orderStatus={params.getValue('status') || ''}
            initialButton='View'
            dialogTitle={'Order Information - Order #' + params.getValue('id')}
            dialogContentText={'Data: '}
            submitButton='Cancel Order'
            TypeName = 'Vendor'
          />
        }
      </div>
    ),
  }
];

const orderRows = [
  { id: 1, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100", status: "Completed" },
  { id: 2, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100", status: "Completed" },
  { id: 3, vendorName: 'Vendor2', items: '????', timestamp: "01/31/2021", cost: "$200", status: "Ongoing" },
  { id: 5, vendorName: 'Vendor1', items: '????', timestamp: "01/31/2021", cost: "$100", status: "Ongoing" }
];

function LoadedView() {
  const [open, setOpen] = React.useState(false);
  
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const AddCustomerDialogBox = () => {
    return (
      <Dialog open={open} onClose={handleClose} fullWidth maxWidth='sm'>
        <DialogTitle>Add New Vendor</DialogTitle>
        <DialogContent>
          <InputLabel>Vendor Name</InputLabel>
          <TextField
            required
            autoFocuss
            margin="dense"
            id="name"
            label="Required"
            type="text"
          />
          <AddressInput />
          <PhoneInput />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={() => {
            handleClose();
          }} color="primary">
            Add Vendor
          </Button>
        </DialogActions>
      </Dialog>
    );
  };

  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Purchase Department</h1>
      <div style={{ height: 600, width: '45%', float: 'left' }}>
        <h2 style={{ float: 'left' }}>Vendors</h2>
        <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={handleClickOpen}>
          Add New Vendor
        </Button>
        <AddCustomerDialogBox />
        <DataGrid rows={vendorRows} columns={vendorColumns} pageSize={4} />
      </div>
      <div style={{ height: 600, width: '45%', float: 'right' }}>
        <div>
          <h2 style={{ float: 'left' }}>Purchase Orders</h2>
          <NewPurchaseOrderForm
            initialButton='Add New Purchase Order'
            dialogTitle='New Purchase Order'
            dialogContentText='Please enter any information you would like to modify: '
            submitButton='Order'
          />
        </div>
        <DataGrid rows={orderRows} columns={orderColumns} pageSize={4} />

      </div>
    </div>
  );
}

function Purchase() {
  const classes = useStyles();

  return (
    <SpinBeforeLoading minLoadingTime={700}>
      <LoadedView classes={classes} />
    </SpinBeforeLoading>
  );
}

export default Purchase;