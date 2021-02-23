import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Grid } from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

const useStyles = makeStyles((theme) => ({
  button: {
    display: 'block',
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 500,
    minHeight: 500
  },
}));

const vendorRows = [
    {id: 1, vendorName: 'Vendor1'},
    {id: 2, vendorName: 'Vendor2'},
]

const inventoryCols = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'itemName', headerName: 'Item name', width: 130 },
    {
        field: 'price',
        headerName: 'Price',
        type: 'number',
        width: 90,
      },
    {
      field: 'quantity',
      headerName: 'Quantity',
      type: 'number',
      width: 90,
      renderCell: params => (
        <TextField
            id="quantityVal"
            label="Quantity"
            type="number"
            defaultValue="0"
            InputLabelProps={{
            shrink: true,
            }}
        />
      )
    },
  ];


const inventoryRows = [
    {id: 1, itemName: 'Item1', price: "20"},
    {id: 2, itemName: 'Item2', price: "20"},
    {id: 3, itemName: 'Item3', price: "30"},
    {id: 4, itemName: 'Item4', price: "50"},
    {id: 5, itemName: 'Item5', price: "90"},
    {id: 6, itemName: 'Item6', price: "10"},
    {id: 7, itemName: 'Item7', price: "20"},
]

function SelectTableRow({ row }) {
    alert('clicked ' + row.id + " ");
    
}

function InventoryTable () {
    const classes = useStyles();
    return (
        <FormControl className={classes.formControl}>
            <DataGrid rows={inventoryRows} columns={inventoryCols} pageSize={4} onRowClick={(newSelection) => {SelectTableRow(newSelection)}}/>   
        </FormControl>
    );
}



export default function PurchaseOrderForm(props) {
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event) => {
    // CHANGE INVENTORY ROWS BY FETCHING VENDOR'S INVENTORY FROM DB
    alert(event.target.value);
  };

  return (
    <div>
      <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={handleClickOpen}>
      {props.initialButton}
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">{props.dialogTitle}</DialogTitle>
        
        <DialogContent>
            <DialogContentText>
            {props.dialogContentText}
            </DialogContentText>

            <FormControl variant="outlined" >
                <InputLabel htmlFor="outlined-age-native-simple">Vendors</InputLabel>
                <Select
                native
                onChange={handleChange}
                label="Vendors"
                inputProps={{
                    name: 'vendor',
                    id: 'outlined-vendor-native-simple',
                }}
                >

                {vendorRows.map(item =>
                    <option value={item.id}>{item.vendorName}</option>
                )}
                </Select>
            </FormControl>  
            
      
          <InventoryTable/>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            {props.submitButton}
          </Button>
          <Button onClick={handleClose} color="primary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
