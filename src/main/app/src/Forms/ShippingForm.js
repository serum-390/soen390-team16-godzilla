import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export default function ShippingForm(props) {
  const item = props.onSubmit(null, false);
  const [open, setOpen] = React.useState(false);
  const [shippingDate, setShippingDate] = React.useState("");
  const [orderID, setOrderID] = React.useState("");
  const [shippingMethod, setShippingMethod] = React.useState("");

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      shippingDate: shippingDate,
      orderID: orderID,
      shippingMethod: shippingMethod,
    };
    props.onSubmit(data, true);
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
            id="shippingDate"
            label="Shipping Date"
            type="string"
            defaultValue={item.shippingDate}
            onChange={(event) => setShippingDate(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="orderID"
            label="Order ID"
            type="string"
            defaultValue={item.orderID}
            onChange={(event) => setOrderID(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="shippingMethod"
            label="Shipping Method"
            onChange={(event) => setShippingMethod(event.target.value)}
            fullWidth
            type="string"
            defaultValue={item.shippingMethod}
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
