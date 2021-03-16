import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';
import { DataGrid } from '@material-ui/data-grid';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';


const useStyles = makeStyles((theme) => ({
  button: {
    display: 'block',
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 500,
    minHeight: 400
  },
}));

const customerRows = [
  { id: 1, customerName: 'Customer1' },
  { id: 2, customerName: 'Customer2' },
];




const inventoryCols = [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'ProductName', headerName: 'Product name', width: 150 },
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
    width: 120,
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
]

const inventoryRows = [
  { id: 1, ProductName: 'Product1', price: "400" },
  { id: 2, ProductName: 'Product2', price: "100" },
  { id: 3, ProductName: 'Product3', price: "300" },
  { id: 4, ProductName: 'Product4', price: "250" },
  { id: 5, ProductName: 'Product5', price: "200" },

]

export default function NewSalesOrderForm(props) {
  const sales = props.onSubmit(null, false);

  const [open, setOpen] = React.useState(false);
  const [createdDate, setCreatedDate] = React.useState("");
  const [dueDate, setDueDate] = React.useState("");
  const [deliveryLocation, setDeliveryLocation] = React.useState("");
  const [orderType, setOrderType] = React.useState("");
  const [status, setStatus] = React.useState("");


  const handleClickOpen = () => { setOpen(true); };
  const handleClose = () => { setOpen(false); };

  const handleSubmit = () => {
    let data = {
      id: "",
      createdDate: createdDate,
      dueDate: dueDate,
      deliveryLocation: deliveryLocation,
      orderType: orderType,
      status: status,
      items: {}
    };
    props.onSubmit(data, true);
    setOpen(false);
    // want to update the table after clicking this

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
          <TextField
            autoFocus
            margin="dense"
            id="createdDate"
            label="Created Date"
            type="date"
            defaultValue={sales.createdDate}
            onChange={(event) => setCreatedDate(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="dueDate"
            label="Due Date"
            type="date"
            defaultValue={sales.dueDate}
            onChange={(event) => setDueDate(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="deliveryLocation"
            label="Delivery Location"
            type="string"
            defaultValue={sales.deliveryLocation}
            onChange={(event) => setDeliveryLocation(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="orderType"
            label="Order Type"
            type="string"
            defaultValue={sales.orderType}
            onChange={(event) => setOrderType(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="status"
            label="Status"
            type="String"
            defaultValue={sales.status}
            onChange={(event) => setStatus(event.target.value)}
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleSubmit} color="primary">
            {props.submitButton}
          </Button>
          <Button onClick={handleClose} color="primary">
            Cancel
        </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}