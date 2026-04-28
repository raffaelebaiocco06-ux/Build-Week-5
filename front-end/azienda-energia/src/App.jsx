import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./assets/Components/Home";
import Get from "./assets/Components/Get";
import Post from "./assets/Components/Post";
import Delete from "./assets/Components/delete";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/get" element={<Get />} />
          <Route path="/post" element={<Post />} />
          <Route path="/delete" element={<Delete />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
