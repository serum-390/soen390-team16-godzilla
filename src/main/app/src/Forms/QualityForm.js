import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { Paper } from '@material-ui/core';
import Slider from '@material-ui/core/Slider';

export default function QualityForm(props) {


  const marks = [
    {
      value: 0,
      label: '0',
    },
    {
      value: 1,
      label: '1',
    },
    {
      value: 2,
      label: '2',
    },
    {
      value: 3,
      label: '3',
    },
  ];

  function valuetext(value) {
    return `${value} `;
  }

  function valueLabelFormat(value) {
    return marks.findIndex((mark) => mark.value === value);
  }

  const [open, setOpen] = React.useState(false);
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
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
          <h4> Current Item Quality</h4>
          <Paper style={{ minHeight: '50px', maxHeight: '170px', padding: '10px' }}>
            Quality of the item has not been set
          </Paper>
          <div style={{ height: 20 }} />
          <h4> Edit Item Quality</h4>
          <Slider
            defaultValue={0}
            valueLabelFormat={valueLabelFormat}
            getAriaValueText={valuetext}
            aria-labelledby="discrete-slider-restrict"
            step={1}
            min={0}
            max={3}
            valueLabelDisplay="auto"
            marks={marks}
            onKeyDown={(e) => e.stopPropagation()}
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
