import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Get() {
  const [dati, setDati] = useState([]);
  const Url = ``; // da mettere il link
  useEffect(() => {
    fetch(Url)
      .then((res) => res.json())
      .then((data) => {
        setDati(data);
      })
      .catch((err) => console.log("Errore:", err));
  }, []);

  return (
    <div className="container-fluid min-vh-100 bg-light py-5">
      <div className="container">
        <h1 className="text-center mb-4">Pagina GET</h1>

        <div className="text-center mb-4">
          <Link to="/" className="btn btn-light border">
            Torna alla Home
          </Link>
        </div>

        <div className="row g-3">
          {dati.slice(0, 20).map((d) => (
            <div className="col-md-6" key={d.id}>
              <div className="card h-100 shadow-sm border-0">
                <div className="card-body">
                  <h5 className="card-title">{d.title}</h5>
                  <p className="card-text">{d.body}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Get;
