/*import { Button, makeStyles, TextField } from '@material-ui/core';
import React from 'react';
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

window.today = new Date();
window.currentMonth = window.today.getMonth();
window.currentYear = window.today.getFullYear();
//window.selectYear = document.getElementById("year");
//window.selectMonth = document.getElementById("month");
window.selectYear = '2021';
window.selectMonth = 'Feb';

window.months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

//window.monthAndYear = document.getElementById("monthAndYear");
window.monthAndYear = "Feb 2021";
showCalendar(window.currentMonth, window.currentYear);
window.currentYear = '';

function Next() {
  window.currentYear = (window.currentMonth === 11) ? window.currentYear + 1 : window.currentYear;
  window.currentMonth = (window.currentMonth + 1) % 12;
  showCalendar(window.currentMonth, window.currentYear);
}

function Previous() {
  window.currentYear = (window.currentMonth === 0) ? window.currentYear - 1 : window.currentYear;
  window.currentMonth = (window.currentMonth === 0) ? 11 : window.currentMonth - 1;
  showCalendar(window.currentMonth, window.currentYear);
}

function Jump() {
  window.currentYear = parseInt(window.selectYear.value);
  window.currentMonth = parseInt(window.selectMonth.value);
  showCalendar(window.currentMonth, window.currentYear);
}

function ShowCalendar(month, year){
    var firstDay = (new Date(year, month)).getDay();

    // CLEAR ALL PREVIOUS TABLE ROWS

    // FILL OUT DATA

    var date = 1;
    var table = [];
    var row = [];
    for (var i = 0; i < 6; i++) {

      // CREATE TABLE ROW
      row = [];

      // CREATE INDIVIDUAL CELLS
      for (var j = 0; j < 7; j++) {
        if (i === 0 && j < firstDay) {
          //
          row.push(<TableCell style={{height:'120px'}}></TableCell>);
        }
        else if (date > daysInMonth(month, year)) {
          break;
        }
        else{
          row.push(<TableCell style={{height:'120px'}}>{date}</TableCell>);
          date++;
        }
      }

      // APPEND ROW TO CALENDAR BODY
        table.push(row);
    }
}

function daysInMonth(iMonth, iYear) 
{ 
    return 32 - new Date(iYear, iMonth, 32).getDate();
}

function ScheduleTable() {
  const classes = useStyles();

  //var scheduleRows = GenerateCalendarRows();
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
        <TableBody id={'calendar-body'}>
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
}*/

import { Button, makeStyles} from '@material-ui/core';
import React from 'react';
import Paper from '@material-ui/core/Paper';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from '@material-ui/core';
import { useState, setState } from "react";

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


//////////////////////


const row = (x,i) =>
  <TableRow key={x.name}>
    {x[0]}
    {x[1]}
    {x[2]}
    {x[3]}
    {x[4]}
    {x[5]}
    {x[6]}
  </TableRow>;

const Calendar = props => {
  const classes = useStyles();
  const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  const [month, setMonth] = useState(new Date().getMonth());
  const [year, setYear] = useState(new Date().getFullYear());
  const [monthYear, setMonthYear] = useState(monthNames[[month]] + " " + [year]);
  const [data, setData] = useState(ChangeCalendar([year], [month]));
  
  
  function PrevMonth(){
    let m = Number.parseInt([month], 10);
    let y = Number.parseInt([year], 10);
    let newMonth = (m === 0) ? 11 : m - 1;
    let newYear = (m === 0) ? y - 1 : y;
    setMonth(newMonth);
    setYear(newYear);
    setMonthYear(monthNames[newMonth] + " " + newYear);
    setData(ChangeCalendar(newYear, newMonth));  
  }

  function NextMonth(){
    let m = Number.parseInt([month], 10);
    let y = Number.parseInt([year], 10);
    let newMonth = (m + 1) % 12;   // FOR SOME REASON, ADDS +1 TO MONTH (1 becomes 11)
    let newYear = (m == 11) ? y + 1 : y;
    setMonth(newMonth);
    setYear(newYear);
    setData(ChangeCalendar(newYear, newMonth));
    setMonthYear(monthNames[newMonth] + " " + newYear);
  }

  function daysInMonth(y, m) 
  {   
    return 32 - new Date(y, m, 32).getDate();
  }

  function ChangeCalendar(y, m){
    var firstDay = (new Date(y, m)).getDay();

    // FILL OUT DATA
    var date = 1;
    var table = [];
    var rows = [];
    for (var i = 0; i < 6; i++) {

      // CREATE TABLE ROW
      rows = [];

      // CREATE INDIVIDUAL CELLS
      for (var j = 0; j < 7; j++) {
        if (i === 0 && j < firstDay) {
          //
          rows.push(<TableCell style={{height:'120px'}}></TableCell>);
        }
        else if (date > daysInMonth(y, m)) {
          break;
        }
        else{
          rows.push(<TableCell style={{height:'120px'}}>{date}</TableCell>);
          date++;
        }
      }

      // APPEND ROW TO CALENDAR BODY
        table.push(rows);
    }

    return table;
  }

  return (
    <div>
       <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell colSpan='7'>
                  <h2 style={{textAlign:'center'}}>{[monthYear]}</h2>
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
            <TableBody id='CalendarTable'>
            {[data].map((x,i) => {
              var t = [];

              for (var j = 0; j < 6; j++) {
                //
                t.push(row(x[j],i));
              }
              //return row(x,i);
              return t;
            })}
            </TableBody>
          </Table>
        </TableContainer>
        <Button variant='contained' color='primary' style={{float: 'left', marginTop:'20px'}} onClick={() => { PrevMonth();}}>Previous</Button>
        <Button variant='contained' color='primary' style={{float: 'right', marginTop:'20px'}} onClick={()=>{NextMonth();}}>Next</Button>
    </div>
   
  );
}

export default Calendar;