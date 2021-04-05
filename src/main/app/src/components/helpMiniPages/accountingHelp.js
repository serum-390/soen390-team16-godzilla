import exportImage from '../../misc/helpPageImages/exporting.png';
import accountingImage from '../../misc/helpPageImages/accounting.png';

function AccountingHelp() {
  return (
    <div>
      <h1>Accounting</h1>
      <h4>Any transactions processed via the GODZILLA enterprise resource planning system will be automatically displayed in the Accounting section, simmilar to the entry below</h4>

      <img src={accountingImage} alt="img" style={{ width: '60%', height: '60%', resizeMode: 'contain' }} />
      <h4>A list of available stats for each transactions follow below</h4>
      <ul>
        <li>Order Number</li>
        <li>Vendor Name</li>
        <li>Invoice Date</li>
        <li>Due Date</li>
        <li>Transaction status</li>
        <li>Transaction Amount</li>
      </ul>
      <h4>Display of transactions can be customized in order to hide/show additional collumns</h4>
      <h2>Exporting as CSV</h2>
      <h4>Transactions can easily be expored as an external .csv file for usage.</h4>
      <img src={exportImage} alt="img" style={{ width: '70%', height: '70%', resizeMode: 'contain' }} />
      <h4>To Export as a .csv file , click on the "Export" button, and then "Download as CSV". This will immediately begin a download of an exported CSV file to your dev</h4>

    </div>
  );
}

export default AccountingHelp;