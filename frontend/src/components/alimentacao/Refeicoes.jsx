
function Refeicoes({
                       planoSelecionado,
                       refeicoes,
                       carregandoRefeicoes,
                       tipoRefeicao,
                       setTipoRefeicao,
                       horarioRefeicao,
                       setHorarioRefeicao,
                       criarRefeicao,
                       editandoRefeicao,
                       novoTipoRefeicao,
                       setNovoTipoRefeicao,
                       novoHorarioRefeicao,
                       setNovoHorarioRefeicao,
                       salvandoRefeicao,
                       atualizarRefeicao,
                       cancelarEdicaoRefeicao,
                       iniciarEdicaoRefeicao,
                       excluirRefeicao,
                       selecionarRefeicao,
                       refeicaoSelecionada,
                       iconesRefeicoes,
                       fecharPlano,
                   }) {
    return (
        <section className="secao-alimentacao secao-refeicoes">
            <div className="titulo-secao-alimentacao">
                <div>
                    <span className="etiqueta-secao">ORGANIZAÇÃO</span>
                    <h2>Refeições do seu plano</h2>
                    <p>
                        Plano #{planoSelecionado.idPlano} ·{' '}
                        {planoSelecionado.objetivo}
                    </p>
                </div>

                <button
                    className="botao-limpar-selecao"
                    type="button"
                    onClick={fecharPlano}
                >
                    Fechar
                </button>
            </div>

            <form className="formulario-refeicao" onSubmit={criarRefeicao}>
                <h3>Adicionar uma refeição</h3>

                <div className="campos-refeicao">
                    <div className="campo-alimentacao">
                        <label>Tipo de refeição</label>
                        <select
                            value={tipoRefeicao}
                            onChange={(event) => setTipoRefeicao(event.target.value)}
                        >
                            <option value="Café da manhã">Café da manhã</option>
                            <option value="Almoço">Almoço</option>
                            <option value="Lanche da tarde">Lanche da tarde</option>
                            <option value="Jantar">Jantar</option>
                            <option value="Ceia">Ceia</option>
                        </select>
                    </div>

                    <div className="campo-alimentacao">
                        <label>Horário</label>
                        <input
                            type="time"
                            value={horarioRefeicao}
                            onChange={(event) => setHorarioRefeicao(event.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" className="botao-adicionar-refeicao">
                        + Adicionar refeição
                    </button>
                </div>
            </form>

            {carregandoRefeicoes && (
                <p className="texto-carregamento">Carregando refeições...</p>
            )}

            {!carregandoRefeicoes && refeicoes.length === 0 && (
                <div className="estado-vazio-alimentacao">
                    <span>🍽️</span>
                    <h3>Seu plano ainda não tem refeições</h3>
                    <p>
                        Adicione uma refeição para começar a montar seu cardápio.
                    </p>
                </div>
            )}

            <div className="lista-refeicoes-alimentacao">
                {!carregandoRefeicoes && refeicoes.map((refeicao) => (
                    <article
                        className={`cartao-refeicao-alimentacao ${
                            refeicaoSelecionada?.idRefeicao === refeicao.idRefeicao
                                ? 'refeicao-ativa'
                                : ''
                        }`}
                        key={refeicao.idRefeicao}
                    >
                        {editandoRefeicao === refeicao.idRefeicao ? (
                            <form
                                className="formulario-editar-refeicao"
                                onSubmit={atualizarRefeicao}
                            >
                                <h3>Editar refeição</h3>

                                <div className="campo-alimentacao">
                                    <label>Tipo de refeição</label>
                                    <select
                                        value={novoTipoRefeicao}
                                        onChange={(event) =>
                                            setNovoTipoRefeicao(event.target.value)
                                        }
                                    >
                                        <option value="Café da manhã">Café da manhã</option>
                                        <option value="Almoço">Almoço</option>
                                        <option value="Lanche da tarde">Lanche da tarde</option>
                                        <option value="Jantar">Jantar</option>
                                        <option value="Ceia">Ceia</option>
                                    </select>
                                </div>

                                <div className="campo-alimentacao">
                                    <label>Horário</label>
                                    <input
                                        type="time"
                                        value={novoHorarioRefeicao}
                                        onChange={(event) =>
                                            setNovoHorarioRefeicao(event.target.value)
                                        }
                                        required
                                    />
                                </div>

                                <div className="acoes-plano">
                                    <button
                                        type="submit"
                                        className="botao-editar-plano"
                                        disabled={salvandoRefeicao}
                                    >
                                        {salvandoRefeicao ? 'Salvando...' : 'Salvar'}
                                    </button>

                                    <button
                                        type="button"
                                        className="botao-excluir-plano"
                                        onClick={cancelarEdicaoRefeicao}
                                    >
                                        Cancelar
                                    </button>
                                </div>
                            </form>
                        ) : (
                            <>
                                <div className="icone-refeicao">
                                    {iconesRefeicoes[refeicao.tipo] || '🍴'}
                                </div>

                                <div className="dados-refeicao">
                                    <h3>{refeicao.tipo}</h3>
                                    <p>
                                        Horário: {refeicao.horario?.slice(0, 5)}
                                    </p>
                                </div>

                                <div className="acoes-refeicao">
                                    <button
                                        type="button"
                                        className="botao-selecionar-refeicao"
                                        onClick={() => selecionarRefeicao(refeicao)}
                                    >
                                        {refeicaoSelecionada?.idRefeicao === refeicao.idRefeicao
                                            ? 'Selecionada ✓'
                                            : 'Ver alimentos →'}
                                    </button>

                                    <button
                                        type="button"
                                        className="botao-editar-plano"
                                        onClick={() => iniciarEdicaoRefeicao(refeicao)}
                                    >
                                        ✏️ Editar
                                    </button>

                                    <button
                                        type="button"
                                        className="botao-excluir-plano"
                                        onClick={() => excluirRefeicao(refeicao.idRefeicao)}
                                    >
                                        🗑️ Excluir
                                    </button>
                                </div>
                            </>
                        )}
                    </article>
                ))}
            </div>
        </section>
    );
}

export default Refeicoes;
