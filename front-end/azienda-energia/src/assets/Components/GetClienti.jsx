import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function GetClienti() {
  const [clienti, setClienti] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errore, setErrore] = useState(null);

  const [nome, setNome] = useState("");
  const [minFatturato, setMinFatturato] = useState("");
  const [maxFatturato, setMaxFatturato] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setErrore(null);

        const response = await fetch("http://localhost:3001/clienti");
        if (!response.ok) throw new Error("Errore server");

        const data = await response.json();

        setClienti(data.content || data);
      } catch (err) {
        setErrore(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleFiltri = async () => {
    try {
      setLoading(true);
      setErrore(null);

      let url = "http://localhost:3001/clienti";
      const params = new URLSearchParams();

      if (nome) params.append("nome", nome);
      if (minFatturato) params.append("minFatturato", minFatturato);
      if (maxFatturato) params.append("maxFatturato", maxFatturato);

      if ([...params].length > 0) {
        url += `?${params.toString()}`;
      }

      const response = await fetch(url);
      if (!response.ok) throw new Error("Errore server");

      const data = await response.json();

      setClienti(data.content || data);
    } catch (err) {
      setErrore(err.message);
    } finally {
      setLoading(false);
    }
  };

  const resetFiltri = async () => {
    setNome("");
    setMinFatturato("");
    setMaxFatturato("");

    try {
      setLoading(true);

      const response = await fetch("http://localhost:3001/clienti");
      const data = await response.json();

      setClienti(data.content || data);
    } catch (err) {
      setErrore(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container-fluid mt-3 px-5 p-0">
      <h2 className="mb-4 text-center">Lista Clienti</h2>

      <div className="row mb-3 g-2">
        <div className="col">
          <input className="form-control" placeholder="Nome contatto" value={nome} onChange={(e) => setNome(e.target.value)} />
        </div>

        <div className="col">
          <input className="form-control" placeholder="Min fatturato" type="number" value={minFatturato} onChange={(e) => setMinFatturato(e.target.value)} />
        </div>

        <div className="col">
          <input className="form-control" placeholder="Max fatturato" type="number" value={maxFatturato} onChange={(e) => setMaxFatturato(e.target.value)} />
        </div>

        <div className="col-auto">
          <button className="btn btn-primary" onClick={handleFiltri}>
            Cerca
          </button>
        </div>

        <div className="col-auto">
          <button className="btn btn-secondary" onClick={resetFiltri}>
            Reset
          </button>
        </div>
      </div>

      <button className="btn btn-danger mb-3" onClick={() => navigate("/getFatture")}>
        Vai alle Fatture
      </button>

      {loading && (
        <div className="text-center">
          <div className="spinner-border" role="status"></div>
        </div>
      )}

      {errore && <div className="alert alert-danger text-center">{errore}</div>}

      {!loading && !errore && (
        <table className="table table-striped table-bordered text-center">
          <thead className="table-dark">
            <tr>
              <th style={{ width: "200px" }}>ID</th>
              <th style={{ width: "200px" }}>Ragione Sociale</th>
              <th style={{ width: "200px" }}>P.IVA</th>
              <th style={{ width: "200px" }}>Contatto</th>
              <th style={{ width: "200px" }}>Email</th>
              <th style={{ width: "200px" }}>Telefono</th>
              <th style={{ width: "200px" }}>Tipo Azienda</th>
            </tr>
          </thead>

          <tbody>
            {clienti.map((cliente) => (
              <tr key={cliente.id}>
                <td>{cliente.id}</td>
                <td>{cliente.ragioneSociale}</td>
                <td>{cliente.pIva}</td>
                <td>
                  {cliente.nomeContatto} {cliente.cognomeContatto}
                </td>
                <td>{cliente.emailContatto}</td>
                <td>{cliente.telefonoContatto}</td>
                <td>{cliente.tipoAzienda}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default GetClienti;
