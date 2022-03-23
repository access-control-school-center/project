import { Link } from 'react-router-dom'

const BannerCard = ({ title, description, buttonLabel, to }) => {
  return (
    <section className="card">
      <h3>{title}</h3>

      <p>{description}</p>

      <Link to={to}>{buttonLabel}</Link>
    </section>
  )
}

export default BannerCard