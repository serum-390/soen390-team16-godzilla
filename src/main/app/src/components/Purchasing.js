import { Button, Dialog, makeStyles } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import React from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import MaskedInput from 'react-text-mask';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import PropTypes from 'prop-types';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';

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

function TextMaskCustom(props) {
  const { inputRef, ...other } = props;

  return (
    <MaskedInput
      {...other}
      ref={(ref) => {
        inputRef(ref ? ref.inputElement : null);
      }}
      mask={['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]}
      placeholderChar={'\u2000'}
      showMask
    />
  );
}

TextMaskCustom.propTypes = {
  inputRef: PropTypes.func.isRequired,
};

const PhoneNumberField = () => {
  const [values, setValues] = React.useState({
    textmask: '(  )    -    ',
    numberformat: '1320',
  });

  const handleChange = (event) => {
    setValues({
      ...values,
      [event.target.name]: event.target.value,
    });
  };
  return (
    <FormControl>
        <InputLabel>Phone Number</InputLabel>
        <Input
          value={values.textmask}
          onChange={handleChange}
          name="textmask"
          inputComponent={TextMaskCustom}
        />
      </FormControl>
  );
};

const ProvinceDrawer = () =>{
  const [province, setProvince] = React.useState('');

  const handleChange = (event) => {
    setProvince(event.target.value);
  };
  return(
    <div  style={{ width: "250px" }}>
      <FormControl>
        <Select
          value={province}
          onChange={handleChange}
        >
          <MenuItem value={"QC"}>QC</MenuItem>
          <MenuItem value={"ON"}>ON</MenuItem>SS
        </Select>
      </FormControl>
    </div>
  );
};

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
          <DialogContentText>
            Please Do Not Leave Any Fields Empty!
          </DialogContentText>
          <InputLabel>Vendor Name</InputLabel>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            type="text"
            />
          <InputLabel>Address</InputLabel>
          <TextField
            autoFocus
            margin="dense"
            type="text"
            multiline
            rows={2}
            fullWidth/>

            <Grid container spacing={2}>
            <Grid item md={3}>
            <InputLabel>City</InputLabel>
            <TextField
            autoFocus
            margin="dense"
            type="text"/>
            </Grid>
            <Grid item md={3}>
            <InputLabel>Postal Code</InputLabel>
            <TextField
            autoFocus
            margin="dense"
            type="text"/>
            </Grid>
            <Grid item md={3}>
            <InputLabel>Province</InputLabel>
            <ProvinceDrawer/>
            </Grid>
          </Grid>
          <PhoneNumberField/>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={() => {
            handleClose() ;
            AddNewVendor();
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
        <AddCustomerDialogBox/>
        <DataGrid rows={vendorRows} columns={vendorColumns} pageSize={4} onRowClick={(newSelection) => { ShowVendorDetail(newSelection); } } />
      </div>
      <div style={{ height: 600, width: '45%', float: 'right' }}>
        <div>
          <h2 style={{ float: 'left' }}>Purchase Orders</h2>
          <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={() => { AddNewPurchaseOrder(); } }>
            Add New Purchase Order
          </Button>
        </div>
        <DataGrid rows={orderRows} columns={orderColumns} pageSize={4} onRowClick={(newSelection) => { ShowPurchaseOrderDetail(newSelection); } } />
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

  return (
    <SpinBeforeLoading minLoadingTime={700}>
      <LoadedView classes={classes} />
    </SpinBeforeLoading>
  );
}

export default Purchase;