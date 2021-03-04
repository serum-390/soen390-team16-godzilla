import { Button } from "@material-ui/core";
import { DataGrid } from "@material-ui/data-grid";
import  UserForm  from "../../Forms/UserForm";

function UserManagement() {
  return (
    <div>
      <div><h1 style={{textAlign: "center" , margin: '20px'}} >User Management</h1></div>
      <div style={{ height: 30, margin: '20px' }}>
        <UserForm
        initialButton='Add New User'  
        dialogTitle='Add New User'  
        dialogContentText='Please enter the following information below to add a new Employee: ' 
        submitButton='Save'
        />
      </div>
      <div style={{ height: 250, margin: '20px'}}> 
      <DataGrid
          columns={UserColumns}
          rows={UserRows}
        />
      </div>
    </div>        
  );
}

export default UserManagement;

const UserRows = [
  { id: 1, UserName: 'User1', UserPassword: '*****', UserRole: "Manager" },
  { id: 2, UserName: 'User2', UserPassword: '********', UserRole: "Sales Rep" }
];

const UserColumns= [
  { field: 'id', headerName: 'ID', width: 70 },
  { field: 'UserName', headerName: 'User Name', width: 150 },
  { field: 'UserPassword', headerName: 'password', width: 150 },
  { field: 'UserRole', headerName: 'Role', width: 200 },
  {
    field: 'UserEdit',
    headerName: 'Modify',
    width: 160,
    renderCell: params => (
      <div style={{ margin: 'auto',  float: 'right'}}>
      <UserForm
        initialButton='Edit'  
        dialogTitle= {'User Information: ID('+ params.getValue('id')+')'}
        dialogContentText='Please enter any information you would like to modify: ' 
        userName={params.getValue('UserName') || ''}
        userPassword={params.getValue('UserPassword') || ''}
        userRole={params.getValue('UserRole') || ''}
        submitButton='Update'
        />
      </div>
    ),
  },
  {
    field: 'UserDelete',
    headerName: 'Remove User',
    width: 160,
    renderCell: params => (
      <div style={{ margin: 'auto' }}>
          <Button variant="contained" color="secondary" style={{ float: 'right' }}>
            Delete
          </Button>
      </div>
    ),
  },
];
