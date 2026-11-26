
import { useState } from 'react';
import { requisicao } from '../../services/api';

function PlanosAlimentares({
                               planos,
                               planoSelecionado,
                               selecionarPlano,
                               formatarMoeda,
                               notificar,
                               aoAtualizarPlano,
                               aoExcluirPlano,
                           }) {
    const [editandoPlano, setEditandoPlano] = useState(null);
    const [novoOrcamento, setNovoOrcamento] = useState('');
    const [novoObjetivo, setNovoObjetivo] = useState('');
    const [salvandoPlano, setSalvandoPlano] = useState(false);

    function iniciarEdicaoPlano(plano) {
        setEditandoPlano(plano.idPlano);
        setNovoOrcamento(String(plano.orcamento));
        setNovoObjetivo(plano.objetivo);
        notificar('');
    }

    function cancelarEdicaoPlano() {
        setEditandoPlano(null);
        setNovoOrcamento('');
        setNovoObjetivo('');
    }

    async function atualizarPlano(event) {
        event.preventDefault();

        if (Number(novoOrcamento) <= 0) {
            notificar('O orçamento deve ser maior que zero.', true);
            return;
        }

        try {
            setSalvandoPlano(true);
            notificar('');

            const resultado = await requisicao(
                `/api/planos-alimentares/${editandoPlano}`,
                'PUT',
                {
                    orcamento: Number(novoOrcamento),
                    objetivo: novoObjetivo,
                }
            );

            aoAtualizarPlano(resultado);
            cancelarEdicaoPlano();
            notificar('Plano alimentar atualizado com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setSalvandoPlano(false);
        }
    }

    async function excluirPlano(idPlano) {
        const confirmar = window.confirm(
            'Tem certeza que deseja excluir este plano alimentar? Todas as refeições dele também serão excluídas.'
        );

        if (!confirmar) return;

        try {
            notificar('');

            await requisicao(
                `/api/planos-alimentares/${idPlano}`,
                'DELETE'
            );

            aoExcluirPlano(idPlano);

            if (editandoPlano === idPlano) {
                cancelarEdicaoPlano();
            }

            notificar('Plano alimentar excluído com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        }
    }

    return (
        <section className="secao-alimentacao">
            <div className="titulo-secao-alimentacao">
                <div>
                    <span className="etiqueta-secao">SEUS PLANOS</span>
                    <h2>Meus planos alimentares</h2>
                    <p>Escolha um plano para organizar suas refeições.</p>
                </div>

                <span className="contador-planos">
                    {planos.length} {planos.length === 1 ? 'plano' : 'planos'}
                </span>
            </div>

            {planos.length === 0 ? (
                <div className="estado-vazio-alimentacao">
                    <span>📋</span>
                    <h3>Nenhum plano por enquanto</h3>
                    <p>Crie seu primeiro plano alimentar para começar.</p>
                </div>
            ) : (
                <div className="lista-planos-alimentacao">
                    {planos.map((item) => (
                        <article
                            className={`cartao-plano-alimentacao ${
                                planoSelecionado?.idPlano === item.idPlano
                                    ? 'plano-ativo'
                                    : ''
                            }`}
                            key={item.idPlano}
                        >
                            <div className="topo-cartao-plano">
                                <span className="icone-cartao-plano">🥗</span>
                                <span className="numero-plano">
                                    PLANO #{item.idPlano}
                                </span>
                            </div>

                            <h3>Plano alimentar</h3>

                            {editandoPlano === item.idPlano ? (
                                <form
                                    className="formulario-alimentacao"
                                    onSubmit={atualizarPlano}
                                >
                                    <div className="campo-alimentacao">
                                        <label>Orçamento semanal (R$)</label>
                                        <input
                                            type="number"
                                            min="1"
                                            step="0.01"
                                            value={novoOrcamento}
                                            onChange={(event) =>
                                                setNovoOrcamento(event.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <div className="campo-alimentacao">
                                        <label>Objetivo</label>
                                        <select
                                            value={novoObjetivo}
                                            onChange={(event) =>
                                                setNovoObjetivo(event.target.value)
                                            }
                                        >
                                            <option value="Emagrecimento">Emagrecimento</option>
                                            <option value="Manutenção">Manutenção</option>
                                            <option value="Manutenção de peso">Manutenção de peso</option>
                                            <option value="Ganho de massa">Ganho de massa</option>
                                        </select>
                                    </div>

                                    <button
                                        type="submit"
                                        className="botao-criar-plano"
                                        disabled={salvandoPlano}
                                    >
                                        {salvandoPlano ? 'Salvando...' : 'Salvar alterações'}
                                    </button>

                                    <button
                                        type="button"
                                        className="botao-limpar-selecao"
                                        onClick={cancelarEdicaoPlano}
                                    >
                                        Cancelar
                                    </button>
                                </form>
                            ) : (
                                <>
                                    <div className="informacoes-plano">
                                        <div>
                                            <span>Orçamento semanal</span>
                                            <strong>{formatarMoeda(item.orcamento)}</strong>
                                        </div>

                                        <div>
                                            <span>Objetivo</span>
                                            <strong>{item.objetivo}</strong>
                                        </div>
                                    </div>

                                    <button
                                        type="button"
                                        className="botao-organizar-plano"
                                        onClick={() => selecionarPlano(item)}
                                    >
                                        {planoSelecionado?.idPlano === item.idPlano
                                            ? 'Plano selecionado ✓'
                                            : 'Organizar refeições →'}
                                    </button>

                                    <div className="acoes-plano">
                                        <button
                                            type="button"
                                            className="botao-editar-plano"
                                            onClick={() => iniciarEdicaoPlano(item)}
                                        >
                                            ✏️ Editar
                                        </button>

                                        <button
                                            type="button"
                                            className="botao-excluir-plano"
                                            onClick={() => excluirPlano(item.idPlano)}
                                        >
                                            🗑️ Excluir
                                        </button>
                                    </div>
                                </>
                            )}
                        </article>
                    ))}
                </div>
            )}
        </section>
    );
}

export default PlanosAlimentares;
