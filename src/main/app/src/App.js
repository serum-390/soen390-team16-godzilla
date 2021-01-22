import logo from './logo.svg';
import './App.css';
import { Component } from 'react';
import Home from './pages/Home';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      message: ""
    };
  }

  async componentDidMount() {
    const got = await fetch("/hello");
    const body = await got.text();
    this.setState({ message: body, isLoading: false })
  }

  render() {
    const { message, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>
    }

    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>FROM SPRING: {message}</p>
        </header>
      </div>
    )
  }
}

export default App;
