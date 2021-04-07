import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';



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
            defaultValue={sales.dueDate}
            onChange={(event) => setDueDate(event.target.value)}
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
            defaultValue={sales.deliveryLocation}
            onChange={(event) => setDeliveryLocation(event.target.value)}
            InputLabelProps={{
              shrink: true,
            }}
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
            InputLabelProps={{
              shrink: true,
            }}
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
            InputLabelProps={{
              shrink: true,
            }}
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