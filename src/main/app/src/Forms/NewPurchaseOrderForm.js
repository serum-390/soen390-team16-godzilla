import React, {useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { DataGrid } from '@material-ui/data-grid';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Snackbar from '@material-ui/core/Snackbar';
import Fade from '@material-ui/core/Fade';

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
            onChange={(e) => {ChangeRowQuantity(e, params.getValue('id'), params.getValue('price'))}}
            defaultValue="0"
            InputLabelProps={{
            shrink: true,
            }}
        />
      )
    },
  ];

let dict = {};
let totalCost = 0;

function ChangeRowQuantity(qty, id, price){
    dict[id] = [];
    dict[id][0] = qty.target.value;
    dict[id][1] = price;

    totalCost = 0;

    for(var key in dict) {
        var value = dict[key][0];
        if (value <= 0) { continue; }
        totalCost += value * dict[key][1];
    }
}

const inventoryRows = [
    {id: 1, itemName: 'Item1', price: "20"},
    {id: 2, itemName: 'Item2', price: "20"},
    {id: 3, itemName: 'Item3', price: "30"},
    {id: 4, itemName: 'Item4', price: "50"},
    {id: 5, itemName: 'Item5', price: "90"},
    {id: 6, itemName: 'Item6', price: "10"},
    {id: 7, itemName: 'Item7', price: "20"},
]


export default function NewPurchaseOrderForm(props){
  return <LoadedView props={props}/>;
}

 function LoadedView({props, inventory}) {
  const [open, setOpen] = useState(false);

  let today = new Date().toISOString().slice(0, 10);
  let tomorrow = new Date();
  tomorrow.setDate(tomorrow.getDate() + 1);
  tomorrow = tomorrow.toISOString().slice(0, 10);

  const [createdDate, setCreatedDate] = React.useState(today);
  const [dueDate, setDueDate] = React.useState(tomorrow);
  const [deliveryLocation, setDeliveryLocation] = React.useState("Montreal");
  const [openAlert, setOpenAlert] = useState(false);

  const AlertSnackbar = () => {
    return (
      <div>
        <Snackbar
          open={openAlert}
          onClose={closeAlert}
          TransitionComponent={Fade}
          message={"alertText"}
          autoHideDuration={6000}
        />
      </div>
    );
  };

  function closeAlert(){
    setOpenAlert(false);
  }

  const displayTotal = () => {
    document.getElementById('total').value = totalCost;
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleSubmit = () => {
    // THIS IS WHERE WE CALL DB TO CREATE NEW ENTRY
    var itemsData = {};

    for(var key in dict) {
        var value = dict[key][0];

        // Skip if invalid quantity amount
        if(value <= 0)
            continue;

        itemsData[key] = value;
        //outString += "Item ID:" + key + " --> Qty: " + value + ", Price: " + dict[key][1] + "\n";
    }

    let data = {
      createdDate: createdDate,
      dueDate: dueDate,
      deliveryLocation: deliveryLocation,
      orderType: "purchases",
      status: "New",
      items: itemsData,
      productionID: "0"
    };
    props.onSubmit(data, true);

    dict = {};  // Clear dictionary
    setOpen(false);
  };

  const handleClose = () => {
    dict = {};  // Clear dictionary
    setOpen(false);
  };

  const handleChange = event => {
    //setVendor(event.target.value);
    // CHANGE INVENTORY ROWS BY FETCHING VENDOR'S INVENTORY FROM DB
    // Or not...
  };

  const classes = useStyles();

  return (
    <div>
      <AlertSnackbar></AlertSnackbar>
      <Button variant="contained" color="primary" style={{ float: 'right' }} onClick={handleClickOpen}>
      {props.initialButton}
      </Button>
      <Dialog open={open} onClose={handleClose} scroll='paper' aria-labelledby="form-dialog-title">
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
              defaultValue={today}
              onChange={(event) => setCreatedDate(event.target.value)}
              fullWidth
            />
            <TextField
              autoFocus
              margin="dense"
              id="dueDate"
              label="Due Date"
              type="date"
              defaultValue={tomorrow}
              onChange={(event) => setDueDate(event.target.value)}
              fullWidth
            />
            <TextField
              autoFocus
              margin="dense"
              id="deliveryLocation"
              label="Delivery Location"
              type="string"
              defaultValue="Montreal"
              onChange={(event) => setDeliveryLocation(event.target.value)}
              fullWidth
            />
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
                  {props.vendors.map(item =>
                      <option value={item.id}>{item.companyName}</option>
                  )}
                </Select>
            </FormControl>
            <FormControl className={classes.formControl}>
              <DataGrid
                rows={inventoryRows}
                columns={inventoryCols}
                pageSize={99}
                hideFooter={true}
                onRowClick={displayTotal}
              />
          </FormControl>

        </DialogContent>
        <DialogActions>
          <div style={{float: 'left', width: '100%'}}>
            <TextField
              disabled
              margin="dense"
              id="total"
              label="Total Cost"
              type="number"
              defaultValue="0"
              variant="outlined"
            />
          </div>

          <Button onClick={handleSubmit} color="primary">
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
