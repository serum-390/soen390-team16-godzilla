import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { TableBody, Table, TableCell, TableRow} from '@material-ui/core';
import { DataGrid } from '@material-ui/data-grid';
import { makeStyles } from '@material-ui/core/styles';
import FormControl from '@material-ui/core/FormControl';

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
    headerName: 'Individual Price',
    type: 'number',
    width: 90,
  },
  {
    field: 'quantity',
    headerName: 'Quantity',
    type: 'number',
    width: 90,
  },
  {
    field: 'totPrice',
    headerName: 'Total Price',
    type: 'number',
    width: 90,
    valueGetter: (params) =>
      `${params.getValue('price') * params.getValue('quantity')}`
  },
];

function InventoryTable (props) {
  const classes = useStyles();

  let inventoryRows = [];
  let count = 0;
  let total = 0;

  console.log(props.inventory);

  for(let i = 0; i < props.inventory.length; i++){
    let id = props.inventory[i]["id"];
    console.log(i + " " + id);
    if(props.items[id] !== undefined){
      inventoryRows[count++] = {id: id, itemName: props.inventory[i]["itemName"], price: props.inventory[i]["buyPrice"], quantity: props.items[id]};
      total += props.inventory[i]["buyPrice"] * props.items[id];
      console.log(total);
    }
  }

  console.log(inventoryRows);

  props.changeTotalAmount(total);

  return (
    <FormControl className={classes.formControl}>
      <DataGrid rows={inventoryRows} columns={inventoryCols} pageSize={10}/>
    </FormControl>
  );
}

export default function PurchaseOrderDetailsForm(props) {
  const classes = useStyles();
  const [total, setTotal] = React.useState(0);
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const changeTotalAmount = (val) => {
    setTotal(val);
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
            <Table>
              <TableBody>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Created Date</TableCell>
                  <TableCell>{props.createdDate}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Due Date</TableCell>
                  <TableCell>{props.dueDate}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Delivery Location</TableCell>
                  <TableCell>{props.deliveryLocation}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Status</TableCell>
                  <TableCell>{props.status}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Total Cost</TableCell>
                  <TableCell>${total}</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </FormControl>
          <InventoryTable inventory={props.inventory} items={props.items} changeTotalAmount={changeTotalAmount}/>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
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
