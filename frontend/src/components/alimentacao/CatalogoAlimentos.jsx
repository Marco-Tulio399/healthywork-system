
function CatalogoAlimentos({ alimentos, carregando, formatarMoeda }) {
    return (
        <section className="secao-alimentacao secao-catalogo-alimentos">
            <div className="titulo-secao-alimentacao">
                <div>
                    <span className="etiqueta-secao">CATÁLOGO</span>
                    <h2>Alimentos disponíveis</h2>
                    <p>
                        Consulte as informações nutricionais e os preços médios.
                    </p>
                </div>

                <span className="contador-planos">
                    {alimentos.length} alimentos
                </span>
            </div>

            {carregando && (
                <p className="texto-carregamento">
                    Carregando alimentos...
                </p>
            )}

            {!carregando && alimentos.length === 0 && (
                <div className="estado-vazio-alimentacao">
                    <span>🥕</span>
                    <h3>Nenhum alimento cadastrado</h3>
                </div>
            )}

            <div className="grade-catalogo-alimentos">
                {!carregando && alimentos.map((alimento) => (
                    <article
                        className="cartao-catalogo-alimento"
                        key={alimento.idAlimento}
                    >
                        <div className="topo-catalogo-alimento">
                            <span>🥗</span>
                            <strong>{alimento.nome}</strong>
                        </div>

                        <div className="dados-nutricionais">
                            <div>
                                <span>Calorias</span>
                                <strong>{alimento.calorias} kcal</strong>
                            </div>

                            <div>
                                <span>Proteínas</span>
                                <strong>{alimento.proteinas} g</strong>
                            </div>

                            <div>
                                <span>Carboidratos</span>
                                <strong>{alimento.carboidratos} g</strong>
                            </div>

                            <div>
                                <span>Gorduras</span>
                                <strong>{alimento.gorduras} g</strong>
                            </div>
                        </div>

                        <div className="preco-alimento">
                            Preço médio:{' '}
                            <strong>
                                {formatarMoeda(alimento.precoMedio)}
                            </strong>
                        </div>
                    </article>
                ))}
            </div>
        </section>
    );
}

export default CatalogoAlimentos;
