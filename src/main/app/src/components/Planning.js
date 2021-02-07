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

function GenerateCalendarRows(){
  var rows = [];
  var cols = [];
  for (var i = 1; i < 32; i++) {
      // FOR TESTING
      if(i === 6 || i === 21){
        cols.push(<TableCell style={{backgroundColor:'#ba543f', height:'120px'}}>{i}</TableCell>);
      }
      else if(i === 16){
        cols.push(<TableCell style={{backgroundColor:'#66cc79', height:'120px'}}>{i}</TableCell>);
      }
      else{
        cols.push(<TableCell style={{height:'120px'}}>{i}</TableCell>);
      }

      // Row is filled
      if(cols.length === 7){
        rows.push(cols);
        cols = [];
      }
  }

  // Not empty
  if(cols.length !== 0){
    rows.push(cols);
  }

  return rows;
}

function ScheduleTable() {
  const classes = useStyles();

  var scheduleRows = GenerateCalendarRows();
  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell colSpan='7'>
              <h2 style={{textAlign:'center'}}>JANUARY 2020</h2>
            </TableCell>
          </TableRow>
          <TableRow>
            <TableCell>Su</TableCell>
            <TableCell>Mo</TableCell>
            <TableCell>Tu</TableCell>
            <TableCell>We</TableCell>
            <TableCell>Th</TableCell>
            <TableCell>Fr</TableCell>
            <TableCell>Sa</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {scheduleRows.map((row) => (
            <TableRow key={row.name}>
              {row[0]}
              {row[1]}
              {row[2]}
              {row[3]}
              {row[4]}
              {row[5]}
              {row[6]}
            </TableRow>
          ))}

        </TableBody>
      </Table>
    </TableContainer>
  );
}

function createActivityData(title, content) {
  return { title, content };
}

const activityRows = [
  createActivityData('Date', '20/01/2020'),
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

const LoadedView = () => {
  return (
    <div style={{ height: 600, width: '100%' }}>
      <h1 style={{ textAlign: "center" }}>Planning Department</h1>
      <div style={{ height: 600, width: '55%', float: 'left' }}>
        <ScheduleTable></ScheduleTable>
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

function Planning() {

  const classes = useStyles();

  return (
    <SpinBeforeLoading minLoadingTime={700}>
      <LoadedView classes={classes} />
    </SpinBeforeLoading>
  );
}


export default Planning;