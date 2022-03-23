import { useEffect, useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"

import useAxiosPrivate from '../hooks/useAxiosPrivate'
import usePerson from '../hooks/usePerson'

const CPF = "CPF",
  ID_CEIP = "ID-CEIP",
  NOME = "Nome",
  N_USP = "Nº USP",
  RG = "RG"

const Search = () => {
  const axios = useAxiosPrivate()
  const navigate = useNavigate()
  const location = useLocation()
  const { setPerson } = usePerson()

  const possibleFilters = [
    CPF, ID_CEIP, NOME, N_USP, RG
  ]

  const [selectedFilter, setSelectedFilter] = useState('ID-CEIP')
  const [given, setGiven] = useState('')
  const [people, setPeople] = useState([])
  const [emptyPeopleList, setEmptyPeopleList] = useState(true)
  const [disabled, setDisabled] = useState(true)

  const buildParams = () => {
    switch (selectedFilter) {
      case ID_CEIP:
        return { "id": given }

      case NOME:
        return { "name": given }

      case N_USP:
        return { "credential.nusp": given }

      case RG:
        return {
          "document.type": "RG",
          "document.value": given
        }

      default:
        return {
          "document.type": "CPF",
          "document.value": given
        }
    }
  }

  const search = async () => {
    if (disabled) return

    setDisabled(true)

    try {
      const response = await axios.get("/users", {
        params: buildParams()
      })

      setPeople(response.data)
    } catch (err) {
      console.log(err)
      setPeople([])
    } finally {
      setDisabled(false)
    }
  }

  useEffect(() => {
    setEmptyPeopleList(people.length === 0)
  }, [people])

  useEffect(() => {
    setDisabled(given.length === 0)
  }, [given])

  const handleNavigation = (id) => {
    return () => {
      const i = people.findIndex((person) => person.id === id)
      setPerson(people[i])

      navigate("/user", { from: location })
    }
  }

  return (
    <section className="card">
      <h2>Busca</h2>

      <select
        value={selectedFilter}
        onChange={(e) => setSelectedFilter(e.target.value)}
        required
      >
        {possibleFilters.map((filter) => (
          <option key={filter} value={filter}>{filter}</option>
        ))}
      </select>

      <input
        type="text"
        className="last"
        autoComplete="off"
        placeholder={`${selectedFilter} da pessoa`}
        value={given}
        onChange={(e) => setGiven(e.target.value)}
        required
      />

      <button
        onClick={search}
        disabled={disabled}
        className={disabled ? "disabled" : ""}
      >
        Buscar
      </button>

      {
        emptyPeopleList ||
        <>
          <hr />

          <h3>Resultado da busca</h3>

          <ul>
            {people.map(({ id, name }) => (
              <li key={id}>
                [{id}] <span className="underline text-blue-600" onClick={handleNavigation(id)}>{name}</span>
              </li>
            ))}
          </ul>
        </>
      }


    </section>
  )
}

export default Search