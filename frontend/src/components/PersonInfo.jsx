import { REPRESENTATION, REV_ROLES_REPRESENTATION } from '../utils/roles'
import usePerson from '../hooks/usePerson'
import If from './If'

const translateRole = (role) => {
  return REV_ROLES_REPRESENTATION[role]
}

const roleStyle = (role) => {
  if (role === REPRESENTATION.USER) return "user"

  return role.toLowerCase()
}

const PersonInfo = () => {
  const { person } = usePerson()

  const { name, role, id, documentType, documentValue, services, shotDate, responsible, credential } = person

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
        <span className="font-bold">{documentType}:</span> {documentValue}
      </li>

      <li>
        <span className="font-bold">Data da última dose:</span> {shotDate}
      </li>

      <hr className="my-4" />

      <p>- Dados complementares</p>

      <If condition={role === REPRESENTATION.USER}>
        <li>
          <span className="font-bold">Serviços ou Laboratórios:</span> {services?.join(', ')}
        </li>

      </If>

      <If condition={role === REPRESENTATION.EMPLOYEE}>
        <li>
          <span className="font-bold">Nº USP:</span> {credential?.nusp}
        </li>
      </If>

      <If condition={role === REPRESENTATION.VOLUNTEER}>
        <li>
          <span className="font-bold">Responsável:</span> {responsible?.name}
        </li>
        <li>
          <span className="font-bold">Serviço:</span> {responsible?.service}
        </li>
      </If>

    </ul>
  </>)
}

export default PersonInfo