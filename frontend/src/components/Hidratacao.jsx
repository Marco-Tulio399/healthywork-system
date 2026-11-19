
import { useEffect, useState } from 'react';
import { requisicao } from '../services/api';

function Hidratacao({ idUsuario, voltar }) {
    const [registro, setRegistro] = useState(null);
    const [carregando, setCarregando] = useState(true);
    const [mensagem, setMensagem] = useState('');
    const [erro, setErro] = useState(false);

    async function carregarHidratacao() {
        try {
            const resultado = await requisicao(
                `/api/hidratacao/${idUsuario}`
            );

            setRegistro(resultado);
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        } finally {
            setCarregando(false);
        }
    }


    async function adicionarAgua() {
        setMensagem('');
        setErro(false);

        try {
            const resultado = await requisicao(
                `/api/hidratacao/${idUsuario}/adicionar`,
                'POST',
                { quantidadeMl: 100 }
            );

            console.log('Resposta do servidor:', resultado);

            setRegistro((registroAtual) => ({
                ...registroAtual,
                consumidoMl: registroAtual.consumidoMl + 100,
                restanteMl: Math.max(0, registroAtual.restanteMl - 100),
            }));

            setMensagem('100 ml de água registrados!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        }
    }


    useEffect(() => {
        carregarHidratacao();
    }, [idUsuario]);

    if (carregando) {
        return <p>Carregando informações de hidratação...</p>;
    }

    if (!registro) {
        return (
            <main className="pagina-funcionalidade">
                <button className="botao-voltar" onClick={voltar}>
                    ← Voltar ao painel
                </button>
                <p className="mensagem erro">{mensagem || 'Não foi possível carregar os dados.'}</p>
            </main>
        );
    }

    const progresso = Math.min(
        (registro.consumidoMl / registro.metaMl) * 100,
        100
    );

    return (
        <main className="pagina-funcionalidade">
            <button className="botao-voltar" onClick={voltar}>
                ← Voltar ao painel
            </button>

            <section className="cartao-funcionalidade">
                <h2>Hidratação diária 💧</h2>
                <p>Acompanhe seu consumo de água durante o dia.</p>

                <div className="resumo-hidratacao">
                    <h3>{registro.consumidoMl} ml</h3>
                    <p>Consumidos hoje</p>

                    <p>
                        Meta diária: <strong>{registro.metaMl} ml</strong>
                    </p>

                    <p>
                        Restante: <strong>{registro.restanteMl} ml</strong>
                    </p>
                </div>

                <div className="barra-progresso">
                    <div
                        className="progresso"
                        style={{ width: `${progresso}%` }}
                    />
                </div>

                <p>{progresso.toFixed(0)}% da meta alcançada</p>

                <button
                    className="botao-principal"
                    onClick={adicionarAgua}
                >
                    + Adicionar 100 ml
                </button>

                {mensagem && (
                    <p className={erro ? 'mensagem erro' : 'mensagem sucesso'}>
                        {mensagem}
                    </p>
                )}
            </section>
        </main>
    );
}

export default Hidratacao;
