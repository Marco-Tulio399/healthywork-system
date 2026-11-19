
const API_URL = 'http://localhost:8080';

export async function requisicao(endpoint, metodo = 'GET', dados = null) {
    const configuracao = {
        method: metodo,
        headers: {
            'Content-Type': 'application/json',
        },
    };

    if (dados !== null) {
        configuracao.body = JSON.stringify(dados);
    }

    const resposta = await fetch(`${API_URL}${endpoint}`, {
        ...configuracao,
        cache: 'no-store',
    });
    const texto = await resposta.text();

    const resultado = texto ? JSON.parse(texto) : null;

    if (!resposta.ok) {
        throw new Error(
            resultado?.message || resultado?.mensagem || 'Erro na comunicação com o servidor.'
        );
    }

    return resultado;
}
