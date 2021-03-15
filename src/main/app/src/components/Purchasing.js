import { Button, Dialog, makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React, {useState, useEffect} from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import InputLabel from '@material-ui/core/InputLabel';
import AddressInput from '../components/forms/AddressInput';
import PhoneInput from '../components/forms/PhoneInput';
import VendorDetailsForm from "../Forms/VendorDetailsForm";
import NewPurchaseOrderForm from "../Forms/NewPurchaseOrderForm";
import PurchaseOrderDetailsForm from "../Forms/PurchaseOrderDetailsForm";
import DeleteButton from "./forms/DeleteButton";
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
          /*<Button variant='contained'
           color='secondary'
           onClick={params.value.onClick}>
           show</Button> 
           */

          <VendorDetailsForm
            onSubmit={params.value}
            splitAddress={params.getValue('address').split(",")[0] || ''}
            splitCity={params.getValue('address').split(",")[1] || ''}
            splitPostal={params.getValue('address').split(",")[2] || ''}
            splitProvince ={params.getValue('address').split(",")[3] || ''}
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
      // RE-ADD THIS LATER
      /*<DeleteButton
        vendorName={params.getValue('vendorName') || ''}
      />*/
      
      <div style={{margin: 'auto'}}>
        <Button variant="contained" onClick={params.value} color="primary" style={{float: 'right'}}>
          Delete
        </Button>
      </div>
    ),
  },



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

const FilledVendorView = ({vendors}) => {
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

function LoadedView({classes, vendors}) {
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
        <VendorDetailsForm
            onSubmit={(data) => insertVendor(data)}
            initialButton='Add New Vendor'
            dialogTitle='Add New Vendor'
            dialogContentText='Please enter any information you would like to add: '
            submitButton='Confirm'
          />
        <FilledVendorView
          vendors={vendors}
        />
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
  const [vendors, setVendors] = useState([]);
  const waitForGetRequest = async () => getVendors().then(ven => setVendors(ven));

  return (
    <SpinBeforeLoading minLoadingTime={0} awaiting={waitForGetRequest}>
      <LoadedView classes={classes} vendors={vendors}/>
    </SpinBeforeLoading>
  );
}

export default Purchase;