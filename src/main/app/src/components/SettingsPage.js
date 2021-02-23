import { Fade, List, ListItem, ListItemIcon, ListItemText } from '@material-ui/core';
import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import clsx from 'clsx';
import Tooltip from '@material-ui/core/Tooltip';

// const DelayedTooltip = ({ title, placement, ...props }) => {
//     return (
//         <Tooltip
//             {...props}
//             title={title}
//             placement={placement}
//             arrow
//             enterDelay={650}
//             TransitionComponent={Fade}
//             TransitionProps={{ timeout: 250 }}
//             disableFocusListener
//             disableTouchListener
//         >
//             {props.children}
//         </Tooltip>
//     );
// };

// const DrawerItem = ({ className, Icon, link, text, selected, onClick }) => {
//     const classes = useNavBarStyles();
//     return (
//       <Link to={link} className={className}>
//         <DelayedTooltip title={text} placement='right'>
//           <ListItem
//             button
//             selected={selected}
//             onClick={onClick}
//             className={clsx({ [classes.selected]: selected })}
//           >
//             <ListItemIcon>
//               <Icon />
//             </ListItemIcon>
//             <ListItemText primary={text} />
//           </ListItem>
//         </DelayedTooltip>
//       </Link>
//     );
//   };


const ContentSwitch = () => {
    return (
        <Switch>
            {/* <Route exact path="/" component={Home} /> */}
        </Switch>
    );
};


function SettingsPage() {
    return (
        <div>
            <Router on>
                <h1>This is the Settings page</h1>
                <List>
                    <ListItem>
                        <ListItem button>
                            <ListItemText primary="User Management"></ListItemText>
                        </ListItem>
                    </ListItem>
                    <ListItem>
                        <ListItem button>
                            <ListItemText primary="Application Settings"></ListItemText>
                        </ListItem>
                    </ListItem>
                    <ListItem>
                        <ListItem button>
                            <ListItemText primary="Account Settings"></ListItemText>
                        </ListItem>
                    </ListItem>
                </List>
                <ContentSwitch />
            </Router>
        </div>

    );
}

export default SettingsPage;