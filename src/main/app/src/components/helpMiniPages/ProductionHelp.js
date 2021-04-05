import exportImage from '../../misc/helpPageImages/exporting.png';

function ProductionHelp() {
  return (
    <div>
      <h1>This is the ProductionHelp page</h1>
      <h2>Exporting as CSV</h2>
      <h4>Both Materials and Products can easily be expored as an external .csv file for usage.</h4>
      <img src={exportImage} alt="img" style={{ width: '100%', height: '100%', resizeMode: 'contain' }} />
      <h4>To Export as a .csv file , click on the "Export" button, and then "Download as CSV". This will immediately begin a download of an exported CSV file to your device</h4>
    </div>
  );
}

export default ProductionHelp;