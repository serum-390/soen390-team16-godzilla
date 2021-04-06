import userIconImage from "../../misc/helpPageImages/userIcon.png";
import userIcon2Image from "../../misc/helpPageImages/userIcon2.png";

function UserManagementHelp() {
  return (
    <div>
      <h1>User Management</h1>
      <h4>The User Management Control Panel can be found by navigating to Settings {">"} User Management</h4>
      <h4>Existing users are displayed as an icons which display their name, role and admin status:</h4>
      <img src={userIconImage} alt="img" style={{ width: '23%', height: '23%', resizeMode: 'contain' }} />
      <h2>Role Management</h2>
      <img src={userIcon2Image} alt="img" style={{ width: '23%', height: '23%', resizeMode: 'contain' }} />
      <h4>When a user icon is selected, their roles and security levels can be modified. New roles can be added to a users by selecting the green "+" button, while admin status for a user can simply be toggled with the admin toggle button.</h4>
    </div>
  );
}

export default UserManagementHelp;