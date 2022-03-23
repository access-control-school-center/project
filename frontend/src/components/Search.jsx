import { useEffect, useState } from "react"
import { Link } from "react-router-dom"

import useAxiosPrivate from '../hooks/useAxiosPrivate'

const Search = () => {
  const axios = useAxiosPrivate()

  const [name, setName] = useState('')
  const [nameEmpty, setNameEmpty] = useState(true)
  const [people, setPeople] = useState([

  ])
  const [emptyPeopleList, setEmptyPeopleList] = useState(true)

  const search = async () => {
    // TODO: real fetching logic

    setPeople([{
      id: 1,
      name: "José Pedro"
    },
    {
      id: 5,
      name: "Maria José"
    }])
  }

  useEffect(() => {
    setNameEmpty(name.length == 0)
  }, [name])

  useEffect(() => {
    setEmptyPeopleList(people.length == 0)
  }, [people])

  return (
    <section className="card">
      <h2>Busca</h2>

      <input
        type="text"
        className="last"
        autoComplete="off"
        placeholder="nome da pessoa"
        required
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <button
        disabled={nameEmpty}
        onClick={search}
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
                {id} - <Link to={`/user/${id}`}>{name}</Link>
              </li>
            ))}
          </ul>
        </>
      }


    </section>
  )
}

export default Search