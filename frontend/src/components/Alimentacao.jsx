
import { useEffect, useState } from 'react';
import { requisicao } from '../services/api';
import CatalogoAlimentos from './alimentacao/CatalogoAlimentos';
import PlanosAlimentares from './alimentacao/PlanosAlimentares';
import Refeicoes from './alimentacao/Refeicoes';
import '../styles/alimentacao.css';

function Alimentacao({ idUsuario, voltar }) {
    const [alimentos, setAlimentos] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [mensagem, setMensagem] = useState('');
    const [erro, setErro] = useState(false);

    const [orcamento, setOrcamento] = useState('');
    const [objetivo, setObjetivo] = useState('Emagrecimento');
    const [criandoPlano, setCriandoPlano] = useState(false);
    const [planos, setPlanos] = useState([]);

    const [planoSelecionado, setPlanoSelecionado] = useState(null);
    const [refeicoes, setRefeicoes] = useState([]);
    const [carregandoRefeicoes, setCarregandoRefeicoes] = useState(false);

    const [editandoPlano, setEditandoPlano] = useState(null);
    const [novoOrcamento, setNovoOrcamento] = useState('');
    const [novoObjetivo, setNovoObjetivo] = useState('');
    const [salvandoPlano, setSalvandoPlano] = useState(false);

    const [tipoRefeicao, setTipoRefeicao] = useState('Café da manhã');
    const [horarioRefeicao, setHorarioRefeicao] = useState('07:00');

    const [editandoRefeicao, setEditandoRefeicao] = useState(null);
    const [novoTipoRefeicao, setNovoTipoRefeicao] = useState('');
    const [novoHorarioRefeicao, setNovoHorarioRefeicao] = useState('');
    const [salvandoRefeicao, setSalvandoRefeicao] = useState(false);

    const [refeicaoSelecionada, setRefeicaoSelecionada] = useState(null);
    const [alimentosRefeicao, setAlimentosRefeicao] = useState([]);
    const [carregandoAlimentosRefeicao, setCarregandoAlimentosRefeicao] = useState(false);

    const [alimentoSelecionado, setAlimentoSelecionado] = useState('');
    const [quantidadeAlimento, setQuantidadeAlimento] = useState('');

    async function criarPlano(event) {
        event.preventDefault();

        try {
            setCriandoPlano(true);
            setMensagem('');
            setErro(false);

            const resultado = await requisicao(
                `/api/planos-alimentares/${idUsuario}`,
                'POST',
                {
                    orcamento: Number(orcamento),
                    objetivo,
                }
            );

            setPlanos((atuais) => [...atuais, resultado]);
            setMensagem('Plano alimentar criado com sucesso!');
            setOrcamento('');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        } finally {
            setCriandoPlano(false);
        }
    }

    function iniciarEdicaoPlano(plano) {
        setEditandoPlano(plano.idPlano);
        setNovoOrcamento(String(plano.orcamento));
        setNovoObjetivo(plano.objetivo);
        setMensagem('');
        setErro(false);
    }

    function cancelarEdicaoPlano() {
        setEditandoPlano(null);
        setNovoOrcamento('');
        setNovoObjetivo('');
    }

    async function atualizarPlano(event) {
        event.preventDefault();

        try {
            setSalvandoPlano(true);
            setMensagem('');
            setErro(false);

            const resultado = await requisicao(
                `/api/planos-alimentares/${editandoPlano}`,
                'PUT',
                {
                    orcamento: Number(novoOrcamento),
                    objetivo: novoObjetivo,
                }
            );

            setPlanos((atuais) =>
                atuais.map((plano) =>
                    plano.idPlano === resultado.idPlano ? resultado : plano
                )
            );

            if (planoSelecionado?.idPlano === resultado.idPlano) {
                setPlanoSelecionado(resultado);
            }

            cancelarEdicaoPlano();
            setMensagem('Plano alimentar atualizado com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
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
            setMensagem('');
            setErro(false);

            await requisicao(
                `/api/planos-alimentares/${idPlano}`,
                'DELETE'
            );

            setPlanos((atuais) =>
                atuais.filter((plano) => plano.idPlano !== idPlano)
            );

            if (planoSelecionado?.idPlano === idPlano) {
                setPlanoSelecionado(null);
                setRefeicoes([]);
                setRefeicaoSelecionada(null);
                setAlimentosRefeicao([]);
            }

            if (editandoPlano === idPlano) {
                cancelarEdicaoPlano();
            }

            setMensagem('Plano alimentar excluído com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        }
    }

    async function carregarRefeicoes(idPlano) {
        try {
            setCarregandoRefeicoes(true);

            const resultado = await requisicao(
                `/api/refeicoes/${idPlano}`
            );

            setRefeicoes(resultado);
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        } finally {
            setCarregandoRefeicoes(false);
        }
    }

    async function criarRefeicao(event) {
        event.preventDefault();

        try {
            setMensagem('');
            setErro(false);

            await requisicao(
                `/api/refeicoes/${planoSelecionado.idPlano}`,
                'POST',
                {
                    tipo: tipoRefeicao,
                    horario: horarioRefeicao,
                }
            );

            await carregarRefeicoes(planoSelecionado.idPlano);
            setMensagem('Refeição criada com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        }
    }

    function iniciarEdicaoRefeicao(refeicao) {
        setEditandoRefeicao(refeicao.idRefeicao);
        setNovoTipoRefeicao(refeicao.tipo);
        setNovoHorarioRefeicao(refeicao.horario?.slice(0, 5) || '');
        setMensagem('');
        setErro(false);
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
            setMensagem('');
            setErro(false);

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
                setRefeicaoSelecionada(resultado);
            }

            cancelarEdicaoRefeicao();
            setMensagem('Refeição atualizada com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
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
            setMensagem('');
            setErro(false);

            await requisicao(
                `/api/refeicoes/${idRefeicao}`,
                'DELETE'
            );

            setRefeicoes((atuais) =>
                atuais.filter((refeicao) => refeicao.idRefeicao !== idRefeicao)
            );

            if (refeicaoSelecionada?.idRefeicao === idRefeicao) {
                setRefeicaoSelecionada(null);
                setAlimentosRefeicao([]);
            }

            if (editandoRefeicao === idRefeicao) {
                cancelarEdicaoRefeicao();
            }

            setMensagem('Refeição excluída com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        }
    }

    async function carregarAlimentosRefeicao(idRefeicao) {
        try {
            setCarregandoAlimentosRefeicao(true);

            const resultado = await requisicao(
                `/api/refeicoes/${idRefeicao}/alimentos`
            );

            setAlimentosRefeicao(resultado);
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        } finally {
            setCarregandoAlimentosRefeicao(false);
        }
    }

    async function adicionarAlimento(event) {
        event.preventDefault();

        try {
            setMensagem('');
            setErro(false);

            await requisicao(
                `/api/refeicoes/${refeicaoSelecionada.idRefeicao}/alimentos`,
                'POST',
                {
                    idAlimento: Number(alimentoSelecionado),
                    quantidade: Number(quantidadeAlimento),
                }
            );

            await carregarAlimentosRefeicao(
                refeicaoSelecionada.idRefeicao
            );

            setMensagem('Alimento adicionado à refeição!');
            setQuantidadeAlimento('');
            setAlimentoSelecionado('');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        }
    }

    useEffect(() => {
        async function carregarDados() {
            try {
                const [listaAlimentos, listaPlanos] = await Promise.all([
                    requisicao('/api/alimentos'),
                    requisicao(`/api/planos-alimentares/${idUsuario}`),
                ]);

                setAlimentos(listaAlimentos);
                setPlanos(listaPlanos);
            } catch (error) {
                setMensagem(error.message);
                setErro(true);
            } finally {
                setCarregando(false);
            }
        }

        carregarDados();
    }, [idUsuario]);

    function selecionarPlano(item) {
        setPlanoSelecionado(item);
        setRefeicaoSelecionada(null);
        setAlimentosRefeicao([]);
        setRefeicoes([]);
        setEditandoRefeicao(null);
        carregarRefeicoes(item.idPlano);
    }

    function selecionarRefeicao(refeicao) {
        setRefeicaoSelecionada(refeicao);
        setAlimentoSelecionado('');
        setQuantidadeAlimento('');
        setAlimentosRefeicao([]);
        carregarAlimentosRefeicao(refeicao.idRefeicao);
    }

    const formatarMoeda = (valor) =>
        Number(valor).toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL',
        });

    const iconesRefeicoes = {
        'Café da manhã': '☀️',
        'Almoço': '🍽️',
        'Lanche da tarde': '🍎',
        'Jantar': '🌙',
        'Ceia': '🥛',
    };

    return (
        <main className="pagina-alimentacao">

            <header className="cabecalho-alimentacao">
                <button className="botao-voltar-alimentacao" onClick={voltar}>
                    ← Voltar ao painel
                </button>

                <div className="identidade-alimentacao">
                    <span className="icone-alimentacao">🍎</span>
                    <span>HEALTHYWORK</span>
                </div>
            </header>

            <section className="destaque-alimentacao">
                <div>
                    <span className="subtitulo-alimentacao">
                        PLANEJAMENTO ALIMENTAR
                    </span>

                    <h1>Sua alimentação, organizada.</h1>

                    <p>
                        Planeje suas refeições, acompanhe seus alimentos
                        e mantenha seu orçamento sob controle.
                    </p>
                </div>

                <div className="decoracao-alimentacao">🥗</div>
            </section>

            {mensagem && (
                <div className={`mensagem-alimentacao ${erro ? 'erro' : 'sucesso'}`}>
                    {mensagem}
                </div>
            )}

            <div className="conteudo-alimentacao">

                <PlanosAlimentares
                    planos={planos}
                    planoSelecionado={planoSelecionado}
                    editandoPlano={editandoPlano}
                    novoOrcamento={novoOrcamento}
                    setNovoOrcamento={setNovoOrcamento}
                    novoObjetivo={novoObjetivo}
                    setNovoObjetivo={setNovoObjetivo}
                    salvandoPlano={salvandoPlano}
                    atualizarPlano={atualizarPlano}
                    cancelarEdicaoPlano={cancelarEdicaoPlano}
                    iniciarEdicaoPlano={iniciarEdicaoPlano}
                    selecionarPlano={selecionarPlano}
                    excluirPlano={excluirPlano}
                    formatarMoeda={formatarMoeda}
                />

                <section className="secao-alimentacao secao-criar-plano">
                    <div className="titulo-secao-alimentacao">
                        <div>
                            <span className="etiqueta-secao">COMECE AQUI</span>
                            <h2>Criar novo plano</h2>
                            <p>Defina seu orçamento e seu objetivo alimentar.</p>
                        </div>

                        <span className="icone-secao">＋</span>
                    </div>

                    <form className="formulario-alimentacao" onSubmit={criarPlano}>
                        <div className="campo-alimentacao">
                            <label>Orçamento semanal (R$)</label>
                            <input
                                type="number"
                                placeholder="Ex: 250,00"
                                value={orcamento}
                                onChange={(event) => setOrcamento(event.target.value)}
                                min="1"
                                step="0.01"
                                required
                            />
                        </div>

                        <div className="campo-alimentacao">
                            <label>Objetivo</label>
                            <select
                                value={objetivo}
                                onChange={(event) => setObjetivo(event.target.value)}
                            >
                                <option value="Emagrecimento">Emagrecimento</option>
                                <option value="Manutenção">Manutenção</option>
                                <option value="Ganho de massa">Ganho de massa</option>
                            </select>
                        </div>

                        <button
                            type="submit"
                            className="botao-criar-plano"
                            disabled={criandoPlano}
                        >
                            {criandoPlano ? 'Criando plano...' : '+ Criar plano alimentar'}
                        </button>
                    </form>
                </section>

                {planoSelecionado && (
                    <Refeicoes
                        planoSelecionado={planoSelecionado}
                        refeicoes={refeicoes}
                        carregandoRefeicoes={carregandoRefeicoes}
                        tipoRefeicao={tipoRefeicao}
                        setTipoRefeicao={setTipoRefeicao}
                        horarioRefeicao={horarioRefeicao}
                        setHorarioRefeicao={setHorarioRefeicao}
                        criarRefeicao={criarRefeicao}
                        editandoRefeicao={editandoRefeicao}
                        novoTipoRefeicao={novoTipoRefeicao}
                        setNovoTipoRefeicao={setNovoTipoRefeicao}
                        novoHorarioRefeicao={novoHorarioRefeicao}
                        setNovoHorarioRefeicao={setNovoHorarioRefeicao}
                        salvandoRefeicao={salvandoRefeicao}
                        atualizarRefeicao={atualizarRefeicao}
                        cancelarEdicaoRefeicao={cancelarEdicaoRefeicao}
                        iniciarEdicaoRefeicao={iniciarEdicaoRefeicao}
                        excluirRefeicao={excluirRefeicao}
                        selecionarRefeicao={selecionarRefeicao}
                        refeicaoSelecionada={refeicaoSelecionada}
                        iconesRefeicoes={iconesRefeicoes}
                        fecharPlano={() => {
                            setPlanoSelecionado(null);
                            setRefeicaoSelecionada(null);
                            setRefeicoes([]);
                            setAlimentosRefeicao([]);
                            setEditandoRefeicao(null);
                        }}
                    />
                )}

                {refeicaoSelecionada && (
                    <section className="secao-alimentacao secao-alimentos-refeicao">
                        <div className="titulo-secao-alimentacao">
                            <div>
                                <span className="etiqueta-secao">CARDÁPIO</span>
                                <h2>
                                    {iconesRefeicoes[refeicaoSelecionada.tipo] || '🍴'}{' '}
                                    {refeicaoSelecionada.tipo}
                                </h2>
                                <p>Adicione os alimentos que farão parte desta refeição.</p>
                            </div>
                        </div>

                        <form className="formulario-alimentacao formulario-adicionar-alimento" onSubmit={adicionarAlimento}>
                            <div className="campo-alimentacao">
                                <label>Escolha o alimento</label>
                                <select
                                    value={alimentoSelecionado}
                                    onChange={(event) => setAlimentoSelecionado(event.target.value)}
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
                                    onChange={(event) => setQuantidadeAlimento(event.target.value)}
                                    min="1"
                                    required
                                />
                            </div>

                            <button type="submit" className="botao-adicionar-refeicao">
                                + Adicionar alimento
                            </button>
                        </form>

                        <h3 className="subtitulo-lista-alimentos">Alimentos desta refeição</h3>

                        {carregandoAlimentosRefeicao && (
                            <p className="texto-carregamento">Carregando alimentos...</p>
                        )}

                        {!carregandoAlimentosRefeicao && alimentosRefeicao.length === 0 && (
                            <div className="estado-vazio-alimentacao">
                                <span>🥑</span>
                                <h3>Nenhum alimento adicionado</h3>
                                <p>Use o formulário acima para montar esta refeição.</p>
                            </div>
                        )}

                        <div className="lista-alimentos-refeicao">
                            {!carregandoAlimentosRefeicao && alimentosRefeicao.map((item, index) => (
                                <article className="item-alimento-refeicao" key={item.idAlimento ?? index}>
                                    <span className="icone-item-alimento">🥬</span>
                                    <div>
                                        <strong>{item.nome}</strong>
                                        <p>Quantidade: {item.quantidade} g</p>
                                    </div>
                                </article>
                            ))}
                        </div>
                    </section>
                )}

                <CatalogoAlimentos
                    alimentos={alimentos}
                    carregando={carregando}
                    formatarMoeda={formatarMoeda}
                />
            </div>

            <footer className="rodape-alimentacao">
                <span>HEALTHYWORK</span>
                <p>Pequenas escolhas, grandes mudanças.</p>
            </footer>
        </main>
    );
}

export default Alimentacao;
