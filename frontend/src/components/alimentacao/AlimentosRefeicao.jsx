
import { useEffect, useState } from 'react';
import { requisicao } from '../../services/api';

function AlimentosRefeicao({
                               refeicaoSelecionada,
                               iconesRefeicoes,
                               alimentos,
                               notificar,
                           }) {
    const [alimentosRefeicao, setAlimentosRefeicao] = useState([]);
    const [carregandoAlimentosRefeicao, setCarregandoAlimentosRefeicao] = useState(false);

    const [alimentoSelecionado, setAlimentoSelecionado] = useState('');
    const [quantidadeAlimento, setQuantidadeAlimento] = useState('');

    const [editandoAlimento, setEditandoAlimento] = useState(null);
    const [novaQuantidadeAlimento, setNovaQuantidadeAlimento] = useState('');
    const [salvandoAlimento, setSalvandoAlimento] = useState(false);

    async function carregarAlimentosRefeicao() {
        try {
            setCarregandoAlimentosRefeicao(true);

            const resultado = await requisicao(
                `/api/refeicoes/${refeicaoSelecionada.idRefeicao}/alimentos`
            );

            setAlimentosRefeicao(resultado);
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setCarregandoAlimentosRefeicao(false);
        }
    }

    useEffect(() => {
        setAlimentosRefeicao([]);
        setAlimentoSelecionado('');
        setQuantidadeAlimento('');
        setEditandoAlimento(null);

        carregarAlimentosRefeicao();
    }, [refeicaoSelecionada.idRefeicao]);

    async function adicionarAlimento(event) {
        event.preventDefault();

        try {
            notificar('');

            await requisicao(
                `/api/refeicoes/${refeicaoSelecionada.idRefeicao}/alimentos`,
                'POST',
                {
                    idAlimento: Number(alimentoSelecionado),
                    quantidade: Number(quantidadeAlimento),
                }
            );

            await carregarAlimentosRefeicao();

            setQuantidadeAlimento('');
            setAlimentoSelecionado('');

            notificar('Alimento adicionado à refeição!');
        } catch (error) {
            notificar(error.message, true);
        }
    }

    function iniciarEdicaoAlimento(item) {
        setEditandoAlimento(item.idAlimento);
        setNovaQuantidadeAlimento(String(item.quantidade));
    }

    function cancelarEdicaoAlimento() {
        setEditandoAlimento(null);
        setNovaQuantidadeAlimento('');
    }

    async function atualizarAlimento(event, idAlimento) {
        event.preventDefault();

        if (!novaQuantidadeAlimento || Number(novaQuantidadeAlimento) <= 0) {
            notificar('Informe uma quantidade válida.', true);
            return;
        }

        try {
            setSalvandoAlimento(true);
            notificar('');

            await requisicao(
                `/api/refeicoes/${refeicaoSelecionada.idRefeicao}/alimentos/${idAlimento}`,
                'PUT',
                {
                    idAlimento,
                    quantidade: Number(novaQuantidadeAlimento),
                }
            );

            await carregarAlimentosRefeicao();

            cancelarEdicaoAlimento();
            notificar('Quantidade do alimento atualizada com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setSalvandoAlimento(false);
        }
    }

    async function excluirAlimento(idAlimento) {
        const confirmar = window.confirm(
            'Tem certeza que deseja remover este alimento desta refeição? Ele continuará disponível no catálogo.'
        );

        if (!confirmar) return;

        try {
            notificar('');

            await requisicao(
                `/api/refeicoes/${refeicaoSelecionada.idRefeicao}/alimentos/${idAlimento}`,
                'DELETE'
            );

            await carregarAlimentosRefeicao();

            if (editandoAlimento === idAlimento) {
                cancelarEdicaoAlimento();
            }

            notificar('Alimento removido da refeição com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        }
    }

    return (
        <section className="secao-alimentacao secao-alimentos-refeicao">
            <div className="titulo-secao-alimentacao">
                <div>
                    <span className="etiqueta-secao">CARDÁPIO</span>

                    <h2>
                        {iconesRefeicoes[refeicaoSelecionada.tipo] || '🍴'}{' '}
                        {refeicaoSelecionada.tipo}
                    </h2>

                    <p>
                        Adicione e organize os alimentos desta refeição.
                    </p>
                </div>
            </div>

            <form
                className="formulario-alimentacao formulario-adicionar-alimento"
                onSubmit={adicionarAlimento}
            >
                <div className="campo-alimentacao">
                    <label>Escolha o alimento</label>

                    <select
                        value={alimentoSelecionado}
                        onChange={(event) =>
                            setAlimentoSelecionado(event.target.value)
                        }
                        required
                    >
                        <option value="">Selecione um alimento</option>

                        {alimentos.map((alimento) => (
                            <option
                                key={alimento.idAlimento}
                                value={alimento.idAlimento}
                            >
                                {alimento.nome}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="campo-alimentacao">
                    <label>Quantidade (gramas)</label>

                    <input
                        type="number"
                        placeholder="Ex: 100"
                        value={quantidadeAlimento}
                        onChange={(event) =>
                            setQuantidadeAlimento(event.target.value)
                        }
                        min="1"
                        step="0.01"
                        required
                    />
                </div>

                <button
                    type="submit"
                    className="botao-adicionar-refeicao"
                >
                    + Adicionar alimento
                </button>
            </form>

            <h3 className="subtitulo-lista-alimentos">
                Alimentos desta refeição
            </h3>

            {carregandoAlimentosRefeicao && (
                <p className="texto-carregamento">
                    Carregando alimentos...
                </p>
            )}

            {!carregandoAlimentosRefeicao &&
                alimentosRefeicao.length === 0 && (
                    <div className="estado-vazio-alimentacao">
                        <span>🥑</span>

                        <h3>Nenhum alimento adicionado</h3>

                        <p>
                            Use o formulário acima para montar esta refeição.
                        </p>
                    </div>
                )}

            <div className="lista-alimentos-refeicao">
                {!carregandoAlimentosRefeicao &&
                    alimentosRefeicao.map((item) => (
                        <article
                            className="item-alimento-refeicao"
                            key={`${item.idRefeicao}-${item.idAlimento}`}
                        >
                            <span className="icone-item-alimento">🥬</span>

                            <div className="conteudo-item-alimento">
                                <strong>{item.nomeAlimento}</strong>

                                {editandoAlimento === item.idAlimento ? (
                                    <form
                                        onSubmit={(event) =>
                                            atualizarAlimento(
                                                event,
                                                item.idAlimento
                                            )
                                        }
                                    >
                                        <div className="campo-alimentacao">
                                            <label>
                                                Nova quantidade (gramas)
                                            </label>

                                            <input
                                                type="number"
                                                min="1"
                                                step="0.01"
                                                value={novaQuantidadeAlimento}
                                                onChange={(event) =>
                                                    setNovaQuantidadeAlimento(
                                                        event.target.value
                                                    )
                                                }
                                                required
                                            />
                                        </div>

                                        <div className="acoes-alimento">
                                            <button
                                                type="submit"
                                                disabled={salvandoAlimento}
                                            >
                                                {salvandoAlimento
                                                    ? 'Salvando...'
                                                    : 'Salvar'}
                                            </button>

                                            <button
                                                type="button"
                                                onClick={cancelarEdicaoAlimento}
                                                disabled={salvandoAlimento}
                                            >
                                                Cancelar
                                            </button>
                                        </div>
                                    </form>
                                ) : (
                                    <>
                                        <p>
                                            Quantidade: {item.quantidade} g
                                        </p>

                                        <p>
                                            Calorias: {item.calorias} kcal
                                        </p>

                                        <div className="acoes-alimento">
                                            <button
                                                type="button"
                                                onClick={() =>
                                                    iniciarEdicaoAlimento(item)
                                                }
                                            >
                                                ✏️ Editar
                                            </button>

                                            <button
                                                type="button"
                                                onClick={() =>
                                                    excluirAlimento(item.idAlimento)
                                                }
                                            >
                                                🗑️ Remover
                                            </button>
                                        </div>
                                    </>
                                )}
                            </div>
                        </article>
                    ))}
            </div>
        </section>
    );
}

export default AlimentosRefeicao;
