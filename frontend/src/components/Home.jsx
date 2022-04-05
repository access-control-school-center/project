import BannerCard from './BannerCard'

const Home = () => {
  return (
    <section className="banner">
      <BannerCard
        title="Cadastro"
        description="Crie um novo cadastro no sistema uma nova pessoa, seja um Usuário, Acompanhante, Funcionário ou Voluntário"
        buttonLabel="Ir para cadastro"
        to="/cadastro"
      />

      <BannerCard
        title="Busca"
        description="Encontre uma pessoa cadastrada, tanto para imprimir sua etiqueta, quanto para resolver dados pendentes."
        buttonLabel="Ir para busca"
        to="/busca"
      />
    </section>
  )
}

export default Home