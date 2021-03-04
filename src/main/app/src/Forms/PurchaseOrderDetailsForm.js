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

const inventoryRows = [
  {id: 1, itemName: 'Item1', price: "20", quantity: "1"},
  {id: 2, itemName: 'Item2', price: "20", quantity: "3"},
  {id: 3, itemName: 'Item3', price: "30", quantity: "4"}
]

function InventoryTable () {
  const classes = useStyles();
  return (
    <FormControl className={classes.formControl}>
      <DataGrid rows={inventoryRows} columns={inventoryCols} pageSize={10}/>
    </FormControl>
  );
}

export default function PurchaseOrderDetailsForm(props) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
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
            <Table>
              <TableBody>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">{props.TypeName} Name</TableCell>
                  <TableCell>{props.vendorName}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Timestamp</TableCell>
                  <TableCell>{props.orderTimestamp}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Total Cost</TableCell>
                  <TableCell>{props.orderCost}</TableCell>
                </TableRow>
                <TableRow>
                  <TableCell className={classes.table} aria-label="simple table">Status</TableCell>
                  <TableCell>{props.orderStatus}</TableCell>
                </TableRow>
              </TableBody>
            </Table>
          </FormControl>
          <InventoryTable/>
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
