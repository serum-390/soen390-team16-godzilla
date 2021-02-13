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
import { useState } from "react";

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
  }
}));

function createActivityData(title, content) {
  return { title, content };
}

const LoadedView = () => {
  const [date, setDate] = useState();

  const changeDay = (d, m, y) => {
    m = (Number.parseInt(m, 10) +1);
    //let day = new Date(m + "/" + d + "/" + y);
    setDate(m + "/" + d + "/" + y); 
    //alert(day);
  }

  const activityRows = [
    createActivityData('Date', [date]),
    createActivityData('Activity Name', 'Placeholder Day'),
    createActivityData('Activity Description', 'A day for temporary placeholders'),
    createActivityData('Frequency', 'Monthly'),
    createActivityData('Organiser Name', 'Jane Doe')
  ];

  function ActivityTable() {
    const classes = useStyles();
  
    return (
      <TableContainer component={Paper}>
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

  return (
    <div style={{ height: 600, width: '100%' }}>
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

function SavePlanning() {
  alert("Now saving planning...");
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