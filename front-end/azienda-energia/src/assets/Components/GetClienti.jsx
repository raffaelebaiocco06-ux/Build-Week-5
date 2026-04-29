import { useEffect, useState } from "react"

function GetClienti() {
  const [clienti, setClienti] = useState([])
  const [loading, setLoading] = useState(true)
  const [errore, setErrore] = useState(null)

  useEffect(() => {
    const fetchClienti = async () => {
      try {
        const response = await fetch("http://localhost:3001/clienti")
        if (!response.ok) throw new Error("Errore server")
        const data = await response.json()
        setClienti(data.content)
        console.log(data)
      } catch (err) {
        setErrore(err.message)
      } finally {
        setLoading(false)
      }
    }

    fetchClienti()
  }, [])

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center">Lista Clienti</h2>

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
              <th>ID</th>
              <th>Nome</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {clienti.map((cliente) => (
              <tr key={cliente.id}>
                <td>{cliente.id}</td>
                <td>{cliente.nome}</td>
                <td>{cliente.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}

export default GetClienti
