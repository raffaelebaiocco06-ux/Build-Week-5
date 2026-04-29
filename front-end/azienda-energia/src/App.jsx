import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./assets/Components/Home";
import Get from "./assets/Components/Get";
import Post from "./assets/Components/Post";
import Registration from "./assets/Components/Registration";
import Login from "./assets/Components/Login";
import Delete from "./assets/Components/delete"
import GetClienti from "./assets/Components/GetClienti"

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/get" element={<GetClienti />} />
          <Route path="/post" element={<Post />} />
          <Route path="/registration" element={<Registration />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
