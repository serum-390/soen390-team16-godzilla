import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';



export default function AddCustomerForm() {
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
      Add New Customer
      </Button>
      <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
        <DialogTitle id="form-dialog-title">Add New Customer</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Please enter the following information below to add a new customer: 
          </DialogContentText>
          <TextField
            autoFocus
            margin="dense"
            id="customer"
            label="Customer Name"
            type="string"
        
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="Address"
            label="Address"
            type="string"

            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="contact"
            label="Contact #"
            type="string"
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Save
          </Button>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
