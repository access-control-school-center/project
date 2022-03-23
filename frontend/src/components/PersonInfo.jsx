import usePerson from '../hooks/usePerson'

const parseDate = ({ day, month, year }) => {
  return `${day}/${month}/${year}`
}

const USER = "UserOrCompanion",
  EMPLOYEE = "Employee",
  VOLUNTEER = "Volunteer"

const translateRole = (role) => {
  switch (role) {
    case USER:
      return "Usuário(a) / Acompanhante"

    case EMPLOYEE:
      return "Funcionário(a)"

    default:
      return "Voluntário(a)"
  }
}

const roleStyle = (role) => {
  if (role === USER) return "user"

  return role.toLowerCase()
}

const PersonInfo = () => {
  const { person } = usePerson()

  const { name, role, id, document: doc, services, lastShotDate, responsible, credential } = person

  return (<>
    <h2>{name}</h2>

    <span className={"pill mb-6 " + roleStyle(role)}>{translateRole(role)}</span>

    <hr className="my-4" />

    <ul>
      <p>- Dados básicos</p>

      <li>
        <span className="font-bold">ID-CEIP:</span> {id}
      </li>

      <li>
        <span className="font-bold">{doc.type}:</span> {doc.value}
      </li>

      <li>
        <span className="font-bold">Data da última dose:</span> {parseDate(lastShotDate)}
      </li>

      <hr className="my-4" />

      <p>- Dados complementares</p>

      {
        role === USER && (
          <li>
            <span className="font-bold">Serviços ou Laboratórios:</span> {services.join(', ')}
          </li>
        )
      }

      {
        role === EMPLOYEE && (
          <li>
            <span className="font-bold">Nº USP:</span> {credential.nusp}
          </li>
        )
      }

      {
        role === VOLUNTEER && (
          <>
            <li>
              <span className="font-bold">Responsável:</span> {responsible.name}
            </li>
            <li>
              <span className="font-bold">Serviço:</span> {responsible.service}
            </li>
          </>
        )
      }

    </ul>
  </>)
}

export default PersonInfo