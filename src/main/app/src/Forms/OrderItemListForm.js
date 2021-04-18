import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import {DataGrid} from "@material-ui/data-grid";
import CustomToolbar from "../components/tables/CustomToolbar";

export default function OrderItemListForm(props) {
  const order = props.onSubmit(null, false);
  const [open, setOpen] = React.useState(false);
  const [id, setId] = React.useState("");
  const [quantity, setQuantity] = React.useState("");
  let items = order.items;

  const handleClickOpen = () => {
    setOpen(true);
  };


  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = order;
    data.items = items;
    console.log("YOOHO");
    console.log(items);
    props.onSubmit(data, true);
    setOpen(false);
  };

  const addItem = () => {
    items["" + id] = quantity;
  };

  const deleteItem = (itemID) => {
    delete items["" + itemID];
  };


  const itemColumn = [
    {field: 'itemID', headerName: 'ID', width: 70},
    {field: 'itemQuantity', headerName: 'Quantity', width: 120},
    {
      field: 'delete',
      headerName: 'Delete',
      width: 120,
      renderCell: params => (
        <div style={{margin: 'auto'}}>
          <Button variant="contained" onClick={params.value} color="primary" style={{float: 'right'}}>
            Delete
          </Button>
        </div>
      ),
    },
  ];

  const FilledItemView = ({orderItems}) => {
    let itemsList = [];
    let i = 0;
    Object.entries(orderItems).forEach(([key, value]) => (
      itemsList.push({
        id: i++,
        itemID: key,
        itemQuantity: value,
        delete: () => deleteItem(key)
      })));

    return (
      <DataGrid
        style={{height: 600, width: 300, display: 'table-row'}}
        rows={itemsList}
        columns={itemColumn}
        pageSize={4}
        components={{Toolbar: CustomToolbar}}
      />
    );
  };

  return (
    <div>
      <Button variant="contained" color="primary" style={{float: 'right'}} onClick={handleClickOpen}>
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
            id="id"
            label="Item ID"
            type="number"
            onChange={(event) => setId(event.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
          />
          <TextField
            autoFocus
            margin="dense"
            id="quantity"
            label="Quantity"
            type="number"
            onChange={(event) => setQuantity(event.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
            InputProps={{inputProps: {min: 1}}}
          />

          <Button variant="contained" color="primary" onClick={addItem}> Add Item </Button>
          <div style={{height: 400, width: '100%'}}>
            <FilledItemView
              orderItems={order.items}
            />
          </div>

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
