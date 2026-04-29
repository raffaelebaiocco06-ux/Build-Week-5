import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./assets/Components/Home";

import GetClienti from "./assets/Components/GetClienti";
import Registration from "./assets/Components/Registration";
import Login from "./assets/Components/Login";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/get" element={<GetClienti />} />

          <Route path="/registration" element={<Registration />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
