import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import PropTypes from 'prop-types';
import MaskedInput from 'react-text-mask';
import Input from '@material-ui/core/Input';

const useStyles = makeStyles((theme) => ({
  button: {
    display: 'block',
  },
  formControl: {
    margin: theme.spacing(2),
    minWidth: 120,
  },
}));

function ProvinceOptions() {
  const classes = useStyles();
  const [province, setprovince] = React.useState('');
  const [open, setOpen] = React.useState(false);

  const handleChange = (event) => {
    setprovince(event.target.value);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };

  return (
    <div>
      <FormControl className={classes.formControl}>
        <InputLabel id="demo-controlled-open-select-label">Province</InputLabel>
        <Select
          labelId="demo-controlled-open-select-label"
          id="demo-controlled-open-select"
          open={open}
          onClose={handleClose}
          onOpen={handleOpen}
          value={province}
          onChange={handleChange}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          <MenuItem value={1}>AB</MenuItem>
          <MenuItem value={2}>BC</MenuItem>
          <MenuItem value={3}>MB</MenuItem>
          <MenuItem value={4}>NB</MenuItem>
          <MenuItem value={5}>NL</MenuItem>
          <MenuItem value={6}>NT</MenuItem>
          <MenuItem value={7}>NS</MenuItem>
          <MenuItem value={8}>NU</MenuItem>
          <MenuItem value={9}>ON</MenuItem>
          <MenuItem value={10}>PE</MenuItem>
          <MenuItem value={11}>QC</MenuItem>
          <MenuItem value={12}>SK</MenuItem>
          <MenuItem value={13}>YT</MenuItem>
        </Select>
      </FormControl>
    </div>
  );
}

function TextMaskCustom(props) {
  const { inputRef, ...other } = props;

  return (
    <MaskedInput
      {...other}
      ref={(ref) => {
        inputRef(ref ? ref.inputElement : null);
      }}
      mask={['(', /[1-9]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]}
      placeholderChar={'\u2000'}
      showMask
    />
  );
}

TextMaskCustom.propTypes = {
  inputRef: PropTypes.func.isRequired,
};



export default function PurchaseOrderForm(props) {
  const [open, setOpen] = React.useState(false);
  const vendor = props.onSubmit(null, false);
  const [companyName, setCompanyName] = React.useState("");
  const [contactName, setContactName] = React.useState("");
  const [address, setAddress] = React.useState("");
  const [contact, setContact] = React.useState("");

  function PhoneNumberInput({defaultNumber}) {
    const [values, setValues] = React.useState({
      textmask: '(1  )    -    ',
    });
  
    const handleChange = (event) => {
      setValues({
        ...values,
        [event.target.name]: event.target.value,
      });

      setContact(event.target.value);
    };
  
    return (
      <div>
        <FormControl>
          <InputLabel htmlFor="formatted-text-mask-input">Phone Number</InputLabel>
          <Input
            value={values.textmask}
            onChange={handleChange}
            name="textmask"
            defaultValue={defaultNumber}
            id="formatted-text-mask-input"
            inputComponent={TextMaskCustom}
          />
        </FormControl>
      </div>
    );
  }

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    let data = {
      id: "",
      companyName: companyName,
      contactName: contactName,
      address: address,
      contact: contact,
      contactType: "vendor"
    };
    props.onSubmit(data, true);
    setOpen(false);
    // want to update the table after clicking this
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
            id="company"
            label="Company Name"
            type="string"
            defaultValue={vendor.companyName}
            onChange={(event) => setCompanyName(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="customer"
            label="Vendor Name"
            type="string"
            defaultValue={vendor.contactName}
            onChange={(event) => setContactName(event.target.value)}
            fullWidth
          />
          <TextField
            autoFocus
            margin="dense"
            id="address"
            label="Address"
            type="string"
            defaultValue={vendor.address}
            onChange={(event) => setAddress(event.target.value)}
            fullWidth
          />
          <Grid container spacing={2}>
            <Grid item md={3}>
              <TextField
                autoFocus
                margin="normal"
                id="city"
                label="City"
                type="text" 
              />
            </Grid>
            <Grid item md={3}>
              <TextField
                autoFocus
                margin="normal"
                id="postalCode"
                label="Postal Code"
                inputProps={{ maxLength: 6 }}
                type="text" 
              />
            </Grid>
            <Grid item md={3}>
            <ProvinceOptions/>
            </Grid>
          </Grid>
          <PhoneNumberInput defaultValue={vendor.contact}/>
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
