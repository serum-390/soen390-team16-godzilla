import React, {useRef} from 'react';
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
        
        // Skip if invalid quantity amount
        if(value <= 0)
            continue;
        
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

export default function PurchaseOrderForm(props) {
  const [open, setOpen] = React.useState(false);
  const [vendor, setVendor] = React.useState(1);

  const totalCostText = useRef("Total Cost: $0");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleSubmit = () => {
    // THIS IS WHERE WE CALL DB TO CREATE NEW ENTRY
    var outString = "Vendor ID: " + [vendor] + "\n\n";

    for(var key in dict) {
        var value = dict[key][0];
        
        // Skip if invalid quantity amount
        if(value <= 0)
            continue;
        
        outString += "Item ID:" + key + " --> Qty: " + value + ", Price: " + dict[key][1] + "\n";
    }
      
    alert(outString + "Total Cost: " + totalCost);

    // THIS IS WHERE DATABASE FUNCTION WILL TAKE PLACE

    dict = {};  // Clear dictionary
    setOpen(false);
  };

  const handleClose = () => {
    dict = {};  // Clear dictionary
    setOpen(false);
  };

  const handleChange = (event) => {  
    //alert(event.target.value);
    setVendor(event.target.value);

    // CHANGE INVENTORY ROWS BY FETCHING VENDOR'S INVENTORY FROM DB
  };

  const handleTotalCost = () => {
    totalCostText.current.innerHTML = "Total Cost: $" + totalCost;
  };

    function InventoryTable () {
        const classes = useStyles();
        return (
            <FormControl className={classes.formControl}>
                <DataGrid rows={inventoryRows} columns={inventoryCols} pageSize={10} onRowClick={handleTotalCost}/>   
            </FormControl>
        );
    }

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
            <div style={{float: 'left', width: '100%'}}>
                <h3 ref={totalCostText}>Total Cost: $0</h3>
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
