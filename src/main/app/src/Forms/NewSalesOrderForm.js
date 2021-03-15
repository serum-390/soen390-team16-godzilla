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
  {id: 1, customerName: 'Customer1'},
  {id: 2, customerName: 'Customer2'},
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

const displayTotal = () => { 
  document.getElementById('total').value= totalCost; 
}

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
          onChange={(e) => {ChangeRowQuantity(e, params.getValue('id'), params.getValue('price'))}}
          defaultValue="0"
          InputLabelProps={{
          shrink: true,
          }}
      />
    )
  },
]

const inventoryRows = [
  {id: 1, ProductName: 'Product1', price: "400"},
  {id: 2, ProductName: 'Product2', price: "100"},
  {id: 3, ProductName: 'Product3', price: "300"},
  {id: 4, ProductName: 'Product4', price: "250"},
  {id: 5, ProductName: 'Product5', price: "200"},

]

function InventoryTable () {
  const classes = useStyles();
  return (
      <FormControl className={classes.formControl}>
          <DataGrid rows={inventoryRows} columns={inventoryCols}   pageSize={99}  />   
      </FormControl>
  ); 
}

export default function NewSalesOrderForm(props) {
  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {setOpen(true);};
  const handleClose = () => {setOpen(false);};

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
          <FormControl variant="outlined"  style={{marginLeft: 15}}>
                <InputLabel htmlFor="outlined-age-native-simple">Customers</InputLabel>
                <Select
                native
                label="Customers"
                inputProps={{
                    name: 'customer',
                    id: 'outlined-customer-native-simple',
                }}
                >
                {customerRows.map(item =>
                    <option value={item.id}>{item.customerName}</option>
                )}
                </Select>
          </FormControl>  
          <InventoryTable/>
          <Button  variant="contained" color="primary" onClick={displayTotal} style={{float: 'right',marginRight: 15}} >
                  Calculate Total
          </Button>
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
          <Button onClick={handleClose} color="primary">
          Create 
          </Button>
          <Button onClick={handleClose} color="secondary">
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}