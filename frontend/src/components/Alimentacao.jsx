
import { useEffect, useState } from 'react';
import { requisicao } from '../services/api';
import CatalogoAlimentos from './alimentacao/CatalogoAlimentos';
import PlanosAlimentares from './alimentacao/PlanosAlimentares';
import Refeicoes from './alimentacao/Refeicoes';
import AlimentosRefeicao from './alimentacao/AlimentosRefeicao';
import CriarPlano from './alimentacao/CriarPlano';
import '../styles/alimentacao.css';

function Alimentacao({ idUsuario, voltar }) {
    const [alimentos, setAlimentos] = useState([]);
    const [carregando, setCarregando] = useState(true);
    const [mensagem, setMensagem] = useState('');
    const [erro, setErro] = useState(false);
    const [abaAtiva, setAbaAtiva] = useState('planos');

    const [planos, setPlanos] = useState([]);
    const [planoSelecionado, setPlanoSelecionado] = useState(null);
    const [refeicaoSelecionada, setRefeicaoSelecionada] = useState(null);

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
    }

    function selecionarRefeicao(refeicao) {
        setRefeicaoSelecionada(refeicao);
    }

    function adicionarPlanoNaLista(resultado) {
        setPlanos((atuais) => [...atuais, resultado]);
    }

    function atualizarPlanoNaLista(resultado) {
        setPlanos((atuais) =>
            atuais.map((plano) =>
                plano.idPlano === resultado.idPlano ? resultado : plano
            )
        );

        if (planoSelecionado?.idPlano === resultado.idPlano) {
            setPlanoSelecionado(resultado);
        }
    }

    function excluirPlanoDaLista(idPlano) {
        setPlanos((atuais) =>
            atuais.filter((plano) => plano.idPlano !== idPlano)
        );

        if (planoSelecionado?.idPlano === idPlano) {
            setPlanoSelecionado(null);
            setRefeicaoSelecionada(null);
        }
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

    function notificar(mensagemNova, houveErro = false) {
        setMensagem(mensagemNova);
        setErro(houveErro);
    }

    function fecharPlano() {
        setPlanoSelecionado(null);
        setRefeicaoSelecionada(null);
        setAbaAtiva('planos');
    }

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

            <nav className="abas-alimentacao">
                <button
                    type="button"
                    className={abaAtiva === 'planos' ? 'aba-alimentacao ativa' : 'aba-alimentacao'}
                    onClick={() => setAbaAtiva('planos')}
                >
                    📋 Meus planos
                </button>

                <button
                    type="button"
                    className={abaAtiva === 'refeicoes' ? 'aba-alimentacao ativa' : 'aba-alimentacao'}
                    onClick={() => setAbaAtiva('refeicoes')}
                    disabled={!planoSelecionado}
                >
                    🍽️ Refeições
                </button>

                <button
                    type="button"
                    className={abaAtiva === 'alimentos' ? 'aba-alimentacao ativa' : 'aba-alimentacao'}
                    onClick={() => setAbaAtiva('alimentos')}
                >
                    🥗 Catálogo de alimentos
                </button>
            </nav>

            <div className="conteudo-alimentacao">
                {abaAtiva === 'planos' && (
                    <>
                        <PlanosAlimentares
                            planos={planos}
                            planoSelecionado={planoSelecionado}
                            selecionarPlano={selecionarPlano}
                            formatarMoeda={formatarMoeda}
                            notificar={notificar}
                            aoAtualizarPlano={atualizarPlanoNaLista}
                            aoExcluirPlano={excluirPlanoDaLista}
                        />

                        <CriarPlano
                            idUsuario={idUsuario}
                            aoCriarPlano={adicionarPlanoNaLista}
                            notificar={notificar}
                        />
                    </>
                )}

                {abaAtiva === 'refeicoes' && planoSelecionado && (
                    <>
                        <Refeicoes
                            planoSelecionado={planoSelecionado}
                            selecionarRefeicao={selecionarRefeicao}
                            refeicaoSelecionada={refeicaoSelecionada}
                            iconesRefeicoes={iconesRefeicoes}
                            fecharPlano={fecharPlano}
                            notificar={notificar}
                        />

                        {refeicaoSelecionada && (
                            <AlimentosRefeicao
                                refeicaoSelecionada={refeicaoSelecionada}
                                iconesRefeicoes={iconesRefeicoes}
                                alimentos={alimentos}
                                notificar={notificar}
                            />
                        )}
                    </>
                )}

                {abaAtiva === 'alimentos' && (
                    <CatalogoAlimentos
                        alimentos={alimentos}
                        carregando={carregando}
                        formatarMoeda={formatarMoeda}
                    />
                )}
            </div>

            <footer className="rodape-alimentacao">
                <span>HEALTHYWORK</span>
                <p>Pequenas escolhas, grandes mudanças.</p>
            </footer>
        </main>
    );
}

export default Alimentacao;
