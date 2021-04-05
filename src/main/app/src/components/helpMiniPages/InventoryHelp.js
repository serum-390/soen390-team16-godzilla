import exportImage from '../../misc/helpPageImages/exporting.png';

function InventoryHelp() {
  return (
    <div>
      <h1>Inventory</h1>
      <h4>The Inventory Page displays a list of all available items for all business operations, which will also store any completed products from the production line. This list consists of :</h4>
      <ul>
        <li>Completed products which are ready to be sold</li>
        <li>Raw materials used for production</li>
        <li>Components (such as electronics) used for productions</li>
      </ul>
      <h2>Adding an item to the inventory</h2>
      <h4>To add a new item to the inventory , simply click on the "insert" button on the top left corner of the table. This will prompt a pop up form to record details about the new item. Once the form has been completed, press "insert" to add the item to the inventory.</h4>
      <h2>Editing an item in the inventor</h2>
      <h4>To edit an existing item in the inventory, scroll to the desired item in the table, and click on the "edit" button.</h4>
      <h2>Deleting an Item</h2>
      <h4>To remove an existing item in the inventory, scroll to the desired item in the table, and click on the "delete" button. Warning :All deletions are irreversible.</h4>
      <h2>Exporting as CSV</h2>
      <h4>The inventory list can easily be expored as an external .csv file for usage.</h4>
      <img src={exportImage} alt="img" style={{ width: '70%', height: '70%', resizeMode: 'contain' }} />
      <h4>To Export as a .csv file , click on the "Export" button, and then "Download as CSV". This will immediately begin a download of an exported CSV file to your device.</h4>
    </div>
  );
}

export default InventoryHelp;