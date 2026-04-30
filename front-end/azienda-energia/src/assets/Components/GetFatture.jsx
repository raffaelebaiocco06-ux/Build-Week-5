import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

function GetFatture() {
  const [fatture, setFatture] = useState([])
  const [loading, setLoading] = useState(true)
  const [errore, setErrore] = useState(null)
  const [stati, setStati] = useState([])
  const [anno, setAnno] = useState("")
  const [stato, setStato] = useState("")
  const [min, setMin] = useState("")

  const navigate = useNavigate()

  const getFatture = async (endpoint) => {
    try {
      setLoading(true)
      setErrore(null)

      const response = await fetch(endpoint)
      if (!response.ok) throw new Error("Errore server")

      const data = await response.json()

      setFatture(data.content || data)
    } catch (err) {
      setErrore(err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleFiltri = () => {
    setLoading(true)
    setErrore(null)

    let url = "http://localhost:3001/fatture/search"
    const params = new URLSearchParams()

    if (anno) params.append("anno", anno)
    if (stato) params.append("stato", stato)
    if (min) params.append("min", min)

    if ([...params].length > 0) {
      url += `?${params.toString()}`
    }

    getFatture(url)
  }

  useEffect(() => {
    getFatture("http://localhost:3001/fatture/search")

    const fetchStati = async () => {
      try {
        const response = await fetch("http://localhost:3001/stati-fattura")
        const data = await response.json()
        setStati(data)
      } catch (err) {
        console.error("Errore stati:", err)
      }
    }

    fetchStati()
  }, [])

  const resetFiltri = async () => {
    setAnno("")
    setMin("")
    setStato("")

    try {
      setLoading(true)

      const response = await fetch("http://localhost:3001/fatture/search")
      const data = await response.json()

      setFatture(data.content || data)
    } catch (err) {
      setErrore(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container-fluid mt-3 px-5 p-0">
      <h2 className="mb-4 text-center">Lista Fatture</h2>

      <div className="row mb-3 g-2">
        <div className="col">
          <input
            className="form-control"
            placeholder="Anno"
            value={anno}
            onChange={(e) => setAnno(e.target.value)}
          />
        </div>

        <div className="col">
          <input
            className="form-control"
            placeholder="Min importo"
            type="number"
            value={min}
            onChange={(e) => setMin(e.target.value)}
          />
        </div>
        {console.log(stati)}
        <select onChange={(e) => setStato(e.target.value)}>
          <option>Tutti</option>
          {stati.map((s) => (
            <option key={s.id} value={s.nome}>
              {s.nome}
            </option>
          ))}
        </select>

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

      <button
        className="btn btn-danger mb-3"
        onClick={() => navigate("/getClienti")}
      >
        Vai ai clienti
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
              <th style={{ width: "200px" }}>Importo</th>
              <th style={{ width: "200px" }}>Data</th>
              <th style={{ width: "200px" }}>Numero</th>
              <th style={{ width: "200px" }}>Ragione Sociale</th>
            </tr>
          </thead>

          <tbody>
            {fatture.map((fatture) => (
              <tr key={fatture.id}>
                <td>{fatture.id}</td>
                <td>{fatture.importo}</td>
                <td>{fatture.data}</td>
                <td>{fatture.numero}</td>
                <td>{fatture.cliente.ragioneSociale}</td>
              </tr>
            ))}
            {console.log(fatture)}
          </tbody>
        </table>
      )}
    </div>
  )
}

export default GetFatture
