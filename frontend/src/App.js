import {Route, Routes} from 'react-router-dom';
import './App.css';
import ForgotPassword from './component/ForgotPassword';
import SignIn from './component/SignIn';
import SignUp from './component/SignUp';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
      </Routes>
    </>
      
  );
}

export default App;
