
import { useState } from 'react';
import { requisicao } from '../services/api';

function AvaliacaoFisica({ idUsuario, voltar }) {
    const [peso, setPeso] = useState('');
    const [altura, setAltura] = useState('');
    const [avaliacao, setAvaliacao] = useState(null);
    const [mensagem, setMensagem] = useState('');
    const [erro, setErro] = useState(false);
    const [carregando, setCarregando] = useState(false);

    async function salvarAvaliacao(event) {
        event.preventDefault();

        setCarregando(true);
        setMensagem('');
        setErro(false);

        try {
            const resultado = await requisicao(
                `/api/avaliacoes/${idUsuario}`,
                'POST',
                {
                    peso: Number(peso),
                    altura: Number(altura),
                }
            );

            setAvaliacao(resultado);
            setMensagem('Avaliação física registrada com sucesso!');
        } catch (error) {
            setMensagem(error.message);
            setErro(true);
        } finally {
            setCarregando(false);
        }
    }

    return (
        <main className="pagina-funcionalidade">
            <button className="botao-voltar" onClick={voltar}>
                ← Voltar ao painel
            </button>

            <section className="cartao-funcionalidade">
                <h2>Avaliação física</h2>
                <p>Informe seus dados para calcular seu IMC.</p>

                <form onSubmit={salvarAvaliacao}>
                    <label>Peso (kg)</label>
                    <input
                        type="number"
                        step="0.1"
                        min="1"
                        placeholder="Ex.: 80"
                        value={peso}
                        onChange={(event) => setPeso(event.target.value)}
                        required
                    />

                    <label>Altura (metros)</label>
                    <input
                        type="number"
                        step="0.01"
                        min="0.5"
                        max="2.5"
                        placeholder="Ex.: 1.80"
                        value={altura}
                        onChange={(event) => setAltura(event.target.value)}
                        required
                    />

                    <button
                        className="botao-principal"
                        type="submit"
                        disabled={carregando}
                    >
                        {carregando ? 'Calculando...' : 'Calcular IMC'}
                    </button>
                </form>

                {mensagem && (
                    <p className={erro ? 'mensagem erro' : 'mensagem sucesso'}>
                        {mensagem}
                    </p>
                )}

                {avaliacao && (
                    <div className="resultado-imc">
                        <h3>Seu resultado</h3>
                        <p>
                            IMC: <strong>{avaliacao.imc}</strong>
                        </p>
                        <p>
                            Classificação: <strong>{avaliacao.classificacao}</strong>
                        </p>
                    </div>
                )}
            </section>
        </main>
    );
}

export default AvaliacaoFisica;
