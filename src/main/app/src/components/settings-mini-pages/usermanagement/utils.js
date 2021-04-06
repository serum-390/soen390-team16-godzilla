/**
 * Returns true if the given query is present as a substring of the string
 *
 * @param {String} stringToSearch
 * @param {String} query
 * @returns
 */
function matchesAnySubstring(stringToSearch, query) {
  const allSubstrings = str => {
    let buf = [];
    const reducer = (a, c) => {
      let fullBuf = buf.length >= query.length;
      let ret = a.concat(fullBuf ? buf.join("") : []);
      buf = (fullBuf ? buf.slice(1) : buf).concat([c]);
      return ret;
    };

    return query.length === 0
      ? [query]
      : str.split("")
        .reduce(reducer, [])
        .concat(buf.length > 0 ? [buf.join("")] : [])
        .filter((v, i, s) => s.indexOf(v) === i)
        .map(sub => sub.toLowerCase());
  };

  return stringToSearch.length === 0
    ? query.length === 0
    : stringToSearch.length >= query.length
    && allSubstrings(stringToSearch).includes(query.toLowerCase());
}

// Basic filtering algorithm; checks for search as a prefix of username
const searchByPrefix = (search, user) => user
  .name
  .toLowerCase()
  .startsWith(search.toLowerCase());


/**
 * Sorting function
 *
 * @param {*} u1
 * @param {*} u2
 * @returns
 */
const sortUsersManagersFirst = (u1, u2) => {
  const hasManagerRole = u =>
    u.roles.reduce(
      (acc, r) => acc || r.role.toLowerCase() === 'manager',
      false);

  const lowestRole = u =>
    u.roles.length > 0
      ? u.roles.map(r => r.role).sort()[0].toLowerCase()
      : "";

  const compareHighestRoles = (user1, user2) =>
    lowestRole(user1).localeCompare(lowestRole(user2));

  if (hasManagerRole(u1)) {
    return -1;
  } else if (hasManagerRole(u2)) {
    return 1;
  } else {
    return compareHighestRoles(u1, u2);
  }
};

export default matchesAnySubstring;
export { matchesAnySubstring, searchByPrefix, sortUsersManagersFirst };
