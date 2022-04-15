import { useEffect, useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"

import { RG, CPF } from '../utils/documents'
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import usePerson from '../hooks/usePerson'

const ID_CEIP = "ID-CEIP",
  NOME = "Nome",
  N_USP = "Nº USP"

function interpretRole(person) {
  const keys = Object.keys(person)

  if (keys.includes("services"))
    return "UserOrCompanion"

  return "Employee"
}

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
  const [disabled, setDisabled] = useState(true)

  const [submitted, setSubmitted] = useState(false)
  const [emptyPeopleList, setEmptyPeopleList] = useState(true)
  const [selectedFilterCopy, setSelectedFilterCopy] = useState('ID-CEIP')
  const [givenCopy, setGivenCopy] = useState('')

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
          "document.type": RG,
          "document.value": given
        }

      default:
        return {
          "document.type": CPF,
          "document.value": given
        }
    }
  }

  const search = async () => {
    if (disabled) return

    setDisabled(true)

    try {
      setSubmitted(true)
      setSelectedFilterCopy(selectedFilter)
      setGivenCopy(given)
      const response = await axios.get("/people", {
        params: buildParams()
      })

      setPeople(response.data.people)
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
      setPerson({
        ...people[i],
        role: interpretRole(people[i])
      })
      navigate("/perfil", { from: location })
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
        submitted && !emptyPeopleList &&
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

      {
        submitted && emptyPeopleList &&
        <>
          <hr />

          <h3>Resultado da busca</h3>

          <p>A busca por "<span className="italic">{selectedFilterCopy} = {givenCopy}</span>" não obteve resultados</p>
        </>
      }


    </section>
  )
}

export default Search