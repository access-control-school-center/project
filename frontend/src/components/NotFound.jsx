import BannerCard from "./BannerCard"

const NotFound = () => {
  return (
    <section className="banner">

      <BannerCard
        title="Página não encontrada"
        description="A página que você está tentando acessar não existe"
        buttonLabel="Volte para a página principal"
        to="/"
      />

    </section>
  )
}

export default NotFound