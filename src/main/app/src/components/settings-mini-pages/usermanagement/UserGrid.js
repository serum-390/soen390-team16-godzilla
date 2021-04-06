import { Avatar, Fade, Grid, InputBase, List, ListItem, ListItemAvatar, ListItemText, ListSubheader, makeStyles, Paper } from "@material-ui/core";
import SearchIcon from '@material-ui/icons/Search';
import React, { Fragment, useRef, useState } from "react";
import useNavBarStyles from "../../../styles/navBarSyles";
import EditUserCard from "./EditUsersCard";
import { defaultPic, useUserGridStyles } from "./styles";
import matchesAnySubstring, { sortUsersManagersFirst } from "./utils";

const useStyles = makeStyles({
  userList: {
    maxHeight: '80%',
    overflowY: 'scroll',
    msOverflowStyle: 'none',
    scrollbarWidth: 'none',
    '&::WebkitScrollbar': {
      display: 'none',
    },
  },
});

const defaultProfilePic =
  <img
    src={defaultPic}
    alt="profile pic"
    style={{ height: '70%', width: '70%', }}
  />;

const demoUsers = [
  {
    name: "Jeff",
    pic: defaultProfilePic,
    admin: true,
    roles: [
      {
        role: 'Buyer',
        description: 'Buys raw materials and parts'
      },
    ],
  },
  {
    name: "John",
    pic: defaultProfilePic,
    admin: false,
    roles: [
      {
        role: 'Buyer',
        description: 'Buys raw materials and parts'
      },
    ],
  },
  {
    name: "Gertrude",
    pic: defaultProfilePic,
    admin: true,
    roles: [
      {
        role: 'Manager',
        description: 'Leads employees'
      },
    ],
  },
  {
    name: "Gonzalez",
    pic: defaultProfilePic,
    admin: false,
    roles: [
      {
        role: 'Seller',
        description: 'Sells bicycles'
      },
    ],
  },
];

const GridItem = ({ user, flip, flipped, unflipAll, hovering, ...props }) =>
  <Grid item {...props}>
    <EditUserCard
      user={user}
      flip={flip}
      flipped={flipped}
      hovering={hovering}
      unflipAll={unflipAll}
    />
  </Grid>;

const GridItems = ({ users, flipped, flip, unflipAll, hovering, ...props }) => {
  return (
    <Fragment {...props}>
      {users
        .sort(sortUsersManagersFirst)
        .map((user, i) =>
          <GridItem
            key={i}
            user={user}
            flipped={flipped[i]}
            flip={() => flip(i)}
            unflipAll={unflipAll}
            hovering={hovering[i]}
          />
        )
      }
    </Fragment>
  );
};

const Blackout = ({ flipped, className, ...props }) =>
  <Fade in={flipped}>
    <div className={className} {...props} />
  </Fade>;

function UserGrid({ users, flipped, flip, unflipAll, hovering, ...props }) {
  const classes = useUserGridStyles();
  if (!users) { return <div>No user data available</div>; }

  return (
    <Grid
      container
      spacing={8}
      justify="flex-start"
      className={classes.grid}
      {...props}
    >
      <GridItems
        users={users}
        flip={flip}
        flipped={flipped}
        unflipAll={unflipAll}
        hovering={hovering}
      />
      <Blackout
        flipped={flipped.reduce((p, c) => p || c, false)}
        className={classes.blackout}
        onClick={unflipAll}
      />
    </Grid>
  );
}

const SearchBarInput = ({ searchBarRef, searchBarClasses, onChange, ...props }) =>
  <InputBase
    inputRef={searchBarRef}
    placeholder="Search…"
    classes={{
      root: searchBarClasses.inputRoot,
      input: searchBarClasses.inputInput,
    }}
    inputProps={{ 'aria-label': 'search' }}
    onChange={onChange}
    {...props}
  />;

const SearchBar = ({ searchBarRef, onChange, ...props }) => {
  const searchBarClasses = useNavBarStyles();
  return (
    <div className={searchBarClasses.search}>
      <div className={searchBarClasses.searchIcon}>
        <SearchIcon />
      </div>
      <SearchBarInput
        searchBarClasses={searchBarClasses}
        searchBarRef={searchBarRef}
        onChange={onChange}
        {...props}
      />
    </div>
  );
};

function UserList({
  users,
  search,
  filterMeToo,
  flip,
  hover,
  unhoverAll,
  ...props
}) {
  const classes = useStyles();

  const groupedByRoles = users.reduce((acc, curr) => {
    let sortedRoles = curr.roles.sort();
    (acc[sortedRoles[0].role] = acc[sortedRoles[0].role] || []).push(curr);
    return acc;
  }, {});

  let count = 0;
  const userToListItem = u => {
    let key = count++;
    return (
      <ListItem
        button
        key={key}
        style={{ paddingLeft: '1em', }}
        onClick={() => {
          flip(key);
          unhoverAll();
        }}
        onMouseEnter={() => hover(key)}
        onMouseLeave={unhoverAll}
      >
        <ListItemAvatar>
          <Avatar>
            {u.pic}
          </Avatar>
        </ListItemAvatar>
        <ListItemText primary={u.name} />
      </ListItem>
    );
  };

  // const filterUsers = user => matchesAnySubstring(user.name, search);
  const asListItems = Object
    .entries(groupedByRoles)
    .map(([role, usrs], i) => (
      <Fragment key={i}>
        <ListSubheader>{`${role}s`}</ListSubheader>
        {usrs.map(userToListItem)}
      </Fragment>
    ));

  return (
    <List className={classes.userList} {...props}> {asListItems} </List>
  );
}

/**
 * A "friends-list" style list of users of the ERP system.
 *
 * @param {Object[]} users
 * @callback sortRoles Sort the roles of your users using this comparison
 *                     function
 * @returns A JSX.Element of a card grid with a sidebar next to it
 */
function UserSideBar({
  users,
  flip,
  hover,
  unhoverAll,
  search,
  searchBarRef,
  searchBarOnChange,
  ...props
}) {
  const gridClasses = useUserGridStyles();

  // Default parameters
  users = users ? users : [];

  return (
    <div className={gridClasses.userListContainer} {...props}>
      <Paper variant='outlined' elevation={0}>
        <SearchBar searchBarRef={searchBarRef} onChange={searchBarOnChange} />
        <UserList
          users={users}
          search={search}
          flip={flip}
          hover={hover}
          unhoverAll={unhoverAll}
        />
      </Paper>
    </div>
  );
}

function GridAndSidebar({ users, ...props }) {
  const classes = useUserGridStyles();

  const [flipped, setFlipped] = useState(users.map(() => false));
  const flip = index => setFlipped(flipped.map((_, i) => i === index));
  const unflipAll = () => setFlipped(flipped.map(() => false));

  const [hovering, setHovering] = useState(users.map(() => false));
  const hover = index => setHovering(hovering.map((_, i) => i === index));
  const unhoverAll = () => setHovering(hovering.map(() => false));

  const [search, setSearch] = useState("");
  const searchBarRef = useRef(null);
  const searchBarOnChange = e => {
    setSearch(e.currentTarget.value);
    searchBarRef.current.focus();
  };

  const filterUsers = user => matchesAnySubstring(user.name, search);
  const sortedAndFilteredUsers = users
    .sort(sortUsersManagersFirst)
    .filter(filterUsers);

  return (
    <div className={classes.root} {...props}>
      <div>
        <UserGrid
          users={sortedAndFilteredUsers}
          flipped={flipped}
          flip={flip}
          unflipAll={unflipAll}
          hovering={hovering}
        />
        <UserSideBar
          users={sortedAndFilteredUsers}
          flip={flip}
          hover={hover}
          unhoverAll={unhoverAll}
          search={search}
          searchBarRef={searchBarRef}
          searchBarOnChange={searchBarOnChange}
        />
      </div>
    </div>
  );
}

const DemoGrid = () => <GridAndSidebar users={demoUsers} />

export default UserGrid;
export { UserGrid, DemoGrid, demoUsers };
