
import { useState } from 'react';
import { requisicao } from '../../services/api';

function CriarPlano({ idUsuario, aoCriarPlano, notificar }) {
    const [orcamento, setOrcamento] = useState('');
    const [objetivo, setObjetivo] = useState('Emagrecimento');
    const [criandoPlano, setCriandoPlano] = useState(false);

    async function criarPlano(event) {
        event.preventDefault();

        if (Number(orcamento) <= 0) {
            notificar('O orçamento deve ser maior que zero.', true);
            return;
        }

        try {
            setCriandoPlano(true);
            notificar('');

            const resultado = await requisicao(
                `/api/planos-alimentares/${idUsuario}`,
                'POST',
                {
                    orcamento: Number(orcamento),
                    objetivo,
                }
            );

            aoCriarPlano(resultado);

            setOrcamento('');
            setObjetivo('Emagrecimento');

            notificar('Plano alimentar criado com sucesso!');
        } catch (error) {
            notificar(error.message, true);
        } finally {
            setCriandoPlano(false);
        }
    }

    return (
        <section className="secao-alimentacao secao-criar-plano">
            <div className="titulo-secao-alimentacao">
                <div>
                    <span className="etiqueta-secao">COMECE AQUI</span>
                    <h2>Criar novo plano</h2>
                    <p>
                        Defina seu orçamento e seu objetivo alimentar.
                    </p>
                </div>

                <span className="icone-secao">＋</span>
            </div>

            <form
                className="formulario-alimentacao"
                onSubmit={criarPlano}
            >
                <div className="campo-alimentacao">
                    <label>Orçamento semanal (R$)</label>

                    <input
                        type="number"
                        placeholder="Ex: 250,00"
                        value={orcamento}
                        onChange={(event) =>
                            setOrcamento(event.target.value)
                        }
                        min="1"
                        step="0.01"
                        required
                    />
                </div>

                <div className="campo-alimentacao">
                    <label>Objetivo</label>

                    <select
                        value={objetivo}
                        onChange={(event) =>
                            setObjetivo(event.target.value)
                        }
                    >
                        <option value="Emagrecimento">
                            Emagrecimento
                        </option>
                        <option value="Manutenção">
                            Manutenção
                        </option>
                        <option value="Ganho de massa">
                            Ganho de massa
                        </option>
                    </select>
                </div>

                <button
                    type="submit"
                    className="botao-criar-plano"
                    disabled={criandoPlano}
                >
                    {criandoPlano
                        ? 'Criando plano...'
                        : '+ Criar plano alimentar'}
                </button>
            </form>
        </section>
    );
}

export default CriarPlano;
