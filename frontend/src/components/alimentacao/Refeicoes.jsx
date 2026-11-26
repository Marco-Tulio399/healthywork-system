
import { useEffect, useState } from 'react';
import { requisicao } from '../../services/api';

function Refeicoes({
                       planoSelecionado,
                       selecionarRefeicao,
                       refeicaoSelecionada,
                       iconesRefeicoes,
                       fecharPlano,
                       notificar,
                   }) {
    const [refeicoes, setRefeicoes] = useState([]);
    const [carregandoRefeicoes, setCarregandoRefeicoes] = useState(false);

    const [tipoRefeicao, setTipoRefeicao] = useState('Café da manhã');
    const [horarioRefeicao, setHorarioRefeicao] = useState('07:00');

    const [editandoRefeicao, setEditandoRefeicao] = useState(null);
    const [novoTipoRefeicao, setNovoTipoRefeicao] = useState('');
    const [novoHorarioRefeicao, setNovoHorarioRefeicao] = useState('');
    const [salvandoRefeicao, setSalvandoRefeicao] = useState(false);

    async function carregarRefeicoes() {
        try {
            setCarregandoRefeicoes(true);

            const resultado = await requisicao(
                `/api/refeicoes/${planoSelecionado.idPlano}`
            );

            setRefeicoes(resultado);
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setCarregandoRefeicoes(false);
        }
    }

    useEffect(() => {
        carregarRefeicoes();
    }, [planoSelecionado.idPlano]);

    async function criarRefeicao(event) {
        event.preventDefault();

        try {
            notificar('');

            await requisicao(
                `/api/refeicoes/${planoSelecionado.idPlano}`,
                'POST',
                {
                    tipo: tipoRefeicao,
                    horario: horarioRefeicao,
                }
            );

            await carregarRefeicoes();
            notificar('Refeição criada com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        }
    }

    function iniciarEdicaoRefeicao(refeicao) {
        setEditandoRefeicao(refeicao.idRefeicao);
        setNovoTipoRefeicao(refeicao.tipo);
        setNovoHorarioRefeicao(refeicao.horario?.slice(0, 5) || '');
        notificar('');
    }

    function cancelarEdicaoRefeicao() {
        setEditandoRefeicao(null);
        setNovoTipoRefeicao('');
        setNovoHorarioRefeicao('');
    }

    async function atualizarRefeicao(event) {
        event.preventDefault();

        try {
            setSalvandoRefeicao(true);
            notificar('');

            const resultado = await requisicao(
                `/api/refeicoes/${editandoRefeicao}`,
                'PUT',
                {
                    tipo: novoTipoRefeicao,
                    horario: novoHorarioRefeicao,
                }
            );

            setRefeicoes((atuais) =>
                atuais.map((refeicao) =>
                    refeicao.idRefeicao === resultado.idRefeicao
                        ? resultado
                        : refeicao
                )
            );

            if (refeicaoSelecionada?.idRefeicao === resultado.idRefeicao) {
                selecionarRefeicao(resultado);
            }

            cancelarEdicaoRefeicao();
            notificar('Refeição atualizada com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setSalvandoRefeicao(false);
        }
    }

    async function excluirRefeicao(idRefeicao) {
        const confirmar = window.confirm(
            'Tem certeza que deseja excluir esta refeição? Os alimentos associados a ela também serão removidos.'
        );

        if (!confirmar) return;

        try {
            notificar('');

            await requisicao(
                `/api/refeicoes/${idRefeicao}`,
                'DELETE'
            );

            setRefeicoes((atuais) =>
                atuais.filter((refeicao) => refeicao.idRefeicao !== idRefeicao)
            );

            if (refeicaoSelecionada?.idRefeicao === idRefeicao) {
                selecionarRefeicao(null);
            }

            if (editandoRefeicao === idRefeicao) {
                cancelarEdicaoRefeicao();
            }

            notificar('Refeição excluída com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        }
    }

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
