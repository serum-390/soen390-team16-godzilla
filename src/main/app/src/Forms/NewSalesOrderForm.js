import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import OrderItemListForm from "./OrderItemListForm";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";


export default function NewSalesOrderForm(props) {
  const [open, setOpen] = React.useState(false);
  const [createdDate, setCreatedDate] = React.useState("");
  const [dueDate, setDueDate] = React.useState("");
  const [deliveryLocation, setDeliveryLocation] = React.useState( "");
  const [status, setStatus] = React.useState( "");
  const [items, setItems] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      id: "",
      createdDate: createdDate,
      dueDate: dueDate,
      deliveryLocation: deliveryLocation,
      orderType: 'sale',
      status: status,
      items: items
    };
    props.onSubmit(data);
    setOpen(false);

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
            id="createdDate"
            label="Created Date"
            type="date"
            defaultValue={(typeof props.order !== 'undefined' && props.order != null) ? props.order.createdDate : ''}
            onChange={(event) => setCreatedDate(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="dueDate"
            label="Due Date"
            type="date"
            defaultValue={(typeof props.order !== 'undefined' && props.order != null) ? props.order.dueDate : ''}
            onChange={(event) => setDueDate(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="deliveryLocation"
            label="Delivery Location"
            type="string"
            defaultValue={(typeof props.order !== 'undefined' && props.order != null) ? props.order.deliveryLocation : ''}
            onChange={(event) => setDeliveryLocation(event.target.value)}
            onKeyDown={(e) => e.stopPropagation()}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <InputLabel id="label" shrink={true}>Status</InputLabel>
          <Select labelId="label" id="select" value={(typeof props.order !== 'undefined' && props.order != null) ? props.order.status : ''}
                  onChange={(event) => setStatus(event.target.value)}>
            <MenuItem value="new">new</MenuItem>
            <MenuItem value="ready">ready</MenuItem>
            <MenuItem value="production process">production process</MenuItem>
            <MenuItem value="shipping process">shipping process</MenuItem>
            <MenuItem value="packaged">packaged</MenuItem>
            <MenuItem value="shipped">shipped</MenuItem>
            <MenuItem value="delivered">delivered</MenuItem>
          </Select>

          <div style={{margin: 'auto'}}>
            <OrderItemListForm
              initialButton='Add Items'
              dialogTitle={'Items'}
              dialogContentText={''}
              submitButton='Submit'
              onSubmit={(itemList) => setItems(itemList)}
              orderItems={(typeof props.order !== 'undefined' && props.order != null) ? props.order.items : {}}
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

