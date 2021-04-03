import { Button, makeStyles, TextField } from '@material-ui/core';
import React from 'react';
import { SpinBeforeLoading } from './inventory/Inventory';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Calendar from './planning/Calendar';
import Snackbar from '@material-ui/core/Snackbar';
import Fade from '@material-ui/core/Fade';
import { useState, useRef } from "react";

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    padding: theme.spacing(2),
  },
  sort: {
    margin: theme.spacing(1),
    textTransform: 'none',
  },
  table: {
    minWidth: 650,
  },
  visible: {
    visibility: 'visible'
  },
  invisible: {
    visibility: 'hidden'
  }
}));

function createActivityData(title, content) {
  return { title, content };
}

let showTable = false;

const LoadedView = (classes) => {
  const [date, setDate] = useState();
  const [activityName, setActivityName] = useState();
  const [activityDesc, setActivityDesc] = useState();
  const [frequency, setFrequency] = useState();
  const [organiserName, setOrganiserName] = useState();

  const activityTableRef = useRef();
  const [openAlert, setOpenAlert] = React.useState(false);
  const [alertText, setAlertText] = React.useState();

  const AlertSnackbar = () => {
    return (
      <div>
        <Snackbar
          open={openAlert}
          onClose={closeAlert}
          TransitionComponent={Fade}
          message={alertText}
          autoHideDuration={6000}
        />
      </div>
    );
  };

  function showAlert(text){
    setAlertText(text);
    setOpenAlert(true);
  }

  function closeAlert(){
    setOpenAlert(false);
  }

  const changeDay = (d, m, y, aName, aDesc, freq, oName) => {
    m = (Number.parseInt(m, 10) +1);
    setDate(m + "/" + d + "/" + y);
    setActivityName(aName);
    setActivityDesc(aDesc);
    setFrequency(freq);
    setOrganiserName(oName);

    if(!showTable)
      showTable = true;
  }

  const activityRows = [
    createActivityData('Date', [date]),
    createActivityData('Activity Name', [activityName]),
    createActivityData('Activity Description', [activityDesc]),
    createActivityData('Frequency', [frequency]),
    createActivityData('Organiser Name', [organiserName])
  ];

  function SavePlanning() {
    showAlert("Now saving planning...\n"
          + [date] + "\n"
          + [activityName] + "\n"
          + [activityDesc] + "\n"
          + [frequency] + "\n"
          + [organiserName] + "\n"
    );
  }

  function ActivityTable() {
    classes = useStyles();

    // You might be wondering, hey Sean, what the fuck are you doing here?
    // Well it's simple really
    // React JS is a fucking bitch and refuses to let me keep element classes from disappearing because it's always refreshing
    // Fuck you too React JS
    if(showTable){
      return (
        <TableContainer ref={activityTableRef} component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableCell colSpan='2'>
                <h2 style={{textAlign:'center'}}>Add to Schedule</h2>
              </TableCell>
            </TableHead>
            <TableBody>
              {activityRows.map((row) => (
                <TableRow key={row.name}>
                  <TableCell>{row.title}</TableCell>
                  <TableCell>{row.content}</TableCell>
                </TableRow>
              ))}
              <TableRow>
                <TableCell colSpan='2'>
                  <TextField id="filled-basic" label="Notes" multiline rows={6} style={{ width: '100%'}}/>
                  <Button variant='contained' color='primary' style={{float: 'right', marginTop:'20px'}} onClick={() => { SavePlanning();}}>Save</Button>
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
      );
    }
    else{
      return (
        <TableContainer ref={activityTableRef} component={Paper} className={classes.invisible}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableCell colSpan='2'>
                <h2 style={{textAlign:'center'}}>Add to Schedule</h2>
              </TableCell>
            </TableHead>
            <TableBody>
              {activityRows.map((row) => (
                <TableRow key={row.name}>
                  <TableCell>{row.title}</TableCell>
                  <TableCell>{row.content}</TableCell>
                </TableRow>
              ))}
              <TableRow>
                <TableCell colSpan='2'>
                  <TextField id="filled-basic" label="Notes" multiline rows={6} style={{ width: '100%'}}/>
                  <Button variant='contained' color='primary' style={{float: 'right', marginTop:'20px'}} onClick={() => { SavePlanning();}}>Save</Button>
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
      );
    }
  }

  return (
    <div style={{ height: 600, width: '100%' }}>
      <AlertSnackbar></AlertSnackbar>
      <h1 style={{ textAlign: "center" }}>Planning Department</h1>
      <div style={{ height: 600, width: '55%', float: 'left' }}>
        {<Calendar changeDay={changeDay}></Calendar>}
      </div>
      <div style={{ height: 600, width: '40%', float: 'right' }}>

        <ActivityTable></ActivityTable>
      </div>
    </div>
  );
}

const Planning = props => {

  const classes = useStyles();

  return (
    <SpinBeforeLoading minLoadingTime={700}>
      <LoadedView classes={classes} />
    </SpinBeforeLoading>
  );
}

export default Planning;