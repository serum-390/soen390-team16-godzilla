import { Button, makeStyles} from '@material-ui/core';
import React from 'react';
import Paper from '@material-ui/core/Paper';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from '@material-ui/core';
import { useState} from "react";

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
  selectedCell: {
    backgroundColor: '#ba543f'
  }
}));

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

const Calendar = ({ changeDay }) => {
  const classes = useStyles();
  const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  const [month, setMonth] = useState(new Date().getMonth());
  const [year, setYear] = useState(new Date().getFullYear());
  const [monthYear, setMonthYear] = useState(monthNames[[month]] + " " + [year]);
  const [data, setData] = useState(ChangeCalendar([year], [month]));
  //const [selectedDay, setSelectedDay] = useState(0);

  //const [currentDay, setCurrentDay] = useState(new Date().getDay());
    
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
    let newYear = (m === 11) ? y + 1 : y;
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
          rows.push(<TableCell style={{height:'120px'} } ></TableCell>);
        }
        else if (date > daysInMonth(y, m)) {
          break;
        }
        else{
          rows.push(<TableCell style={{height:'120px'}} id={date} onClick={(e)=>{SelectDay(e, m, y);}}>{date}</TableCell>);
          //cols.push(<TableCell style={{backgroundColor:'#ba543f', height:'120px'}}>{i}</TableCell>);
          date++;
        }
      }

      // APPEND ROW TO CALENDAR BODY
        table.push(rows);
    }

    return table;
  }

  function SelectDay(e, m, y){
    //alert(e.target.id + "/" + (m+1) + "/" + y);
    changeDay(e.target.id, m, y);
    /*alert([selectedDay][1]);

    if(Number.parseInt([selectedDay], 10) !== 0){
      let prevSelected = [selectedDay][0];   
      // remove class of prev
      prevSelected.target.classList.remove("selectedCell");
      alert('b');
    }
   
    // add class to new
    e.target.classList.add("selectedCell");
    var t = [e.target, e.target.id, m, y];
    alert(t);
    setSelectedDay(t);*/
  }

  return (
    <div>
       <TableContainer component={Paper}>
          <Table className={classes.table} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell colSpan='7'>
                  <Button variant='contained' color='primary' style={{float: 'left', marginTop:'20px'}} onClick={() => { PrevMonth();}}>Previous</Button>
                  <Button variant='contained' color='primary' style={{float: 'right', marginTop:'20px'}} onClick={()=>{NextMonth();}}>Next</Button>
                  <h2 style={{float: 'center', textAlign:'center'}}>{[monthYear]}</h2>

                  
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
    </div>
   
  );
}

export default Calendar;