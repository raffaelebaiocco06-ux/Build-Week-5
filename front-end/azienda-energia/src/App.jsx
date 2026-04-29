import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./assets/Components/Home";

import GetClienti from "./assets/Components/GetClienti";
import Registration from "./assets/Components/Registration";
import Login from "./assets/Components/Login";
import GetFatture from "./assets/Components/GetFatture";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/getClienti" element={<GetClienti />} />
          <Route path="/getFatture" element={<GetFatture />} />

          <Route path="/registration" element={<Registration />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
