import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import NativeSelect from "@material-ui/core/NativeSelect";
import InputLabel from "@material-ui/core/InputLabel";

export default function ShippingForm(props) {
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
            type="date"
            onChange={(event) => setShippingDate(event.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="orderID"
            label="Order ID"
            type="number"
            onChange={(event) => setOrderID(event.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
            InputProps={{inputProps: {min: 1}}}
            fullWidth
          />
          <InputLabel htmlFor="select" shrink={true}>Shipping Method</InputLabel>
          <NativeSelect id="select" defaultValue="air" onChange={(event) => setShippingMethod(event.target.value)}>
            <option value="air">Air</option>
            <option value="ferry">Ferry</option>
            <option value="car">Car</option>
          </NativeSelect>
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
