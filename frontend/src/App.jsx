
import { useState } from 'react';
import './App.css';
import { requisicao } from './services/api';
import AvaliacaoFisica from './components/AvaliacaoFisica';
import Hidratacao from './components/Hidratacao';
import Alimentacao from './components/Alimentacao';


function App() {
  const [modoCadastro, setModoCadastro] = useState(false);
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [mensagem, setMensagem] = useState('');
  const [erro, setErro] = useState(false);
  const [carregando, setCarregando] = useState(false);
  const [usuarioLogado, setUsuarioLogado] = useState(null);
  const [telaAtual, setTelaAtual] = useState('painel');

  async function handleSubmit(event) {
    event.preventDefault();

    setMensagem('');
    setErro(false);
    setCarregando(true);

    try {
      if (modoCadastro) {
        await requisicao('/api/usuarios/cadastro', 'POST', {
          nome,
          email,
          senha,
        });

        setMensagem('Cadastro realizado com sucesso! Faça seu login.');
        setModoCadastro(false);
        setNome('');
        setSenha('');
      } else {
        const usuario = await requisicao('/api/usuarios/login', 'POST', {
          email,
          senha,
        });

        setUsuarioLogado(usuario);
        setSenha('');
      }
    } catch (error) {
      setMensagem(error.message);
      setErro(true);
    } finally {
      setCarregando(false);
    }
  }

  function sair() {
    setUsuarioLogado(null);
    setEmail('');
    setSenha('');
    setMensagem('');
  }
  if (usuarioLogado && telaAtual === 'hidratacao') {
    return (
        <Hidratacao
            idUsuario={usuarioLogado.idUsuario}
            voltar={() => setTelaAtual('painel')}
        />
    );
  }
  if (usuarioLogado && telaAtual === 'avaliacao') {
    return (
        <AvaliacaoFisica
            idUsuario={usuarioLogado.idUsuario}            voltar={() => setTelaAtual('painel')}
        />
    );
  }
  if (usuarioLogado && telaAtual === 'alimentacao') {
    return (
        <Alimentacao
            idUsuario={usuarioLogado.idUsuario}
            voltar={() => setTelaAtual('painel')}
        />
    );
  }
  if (usuarioLogado) {
    return (
        <main className="painel">
          <header className="topo-painel">
            <div>
              <h1>HealthyWork</h1>
              <p>Seu bem-estar começa aqui.</p>
            </div>

            <button className="botao-sair" onClick={sair}>
              Sair
            </button>
          </header>

          <section className="boas-vindas">
            <h2>Olá, {usuarioLogado.nome}!</h2>
            <p>Vamos cuidar da sua saúde hoje?</p>
          </section>

          <section className="cartoes-painel"><article
              className="cartao-painel"
              onClick={() => setTelaAtual('avaliacao')}
          >
            <h3>Avaliação física</h3>
            <p>Acompanhe seu peso, altura e IMC.</p>
          </article>

            <article
                className="cartao-painel"
                onClick={() => setTelaAtual('hidratacao')}
            >
              <h3>Hidratação</h3>
              <p>Acompanhe seu consumo diário de água.</p>
            </article>

            <article
                className="cartao-painel"
                onClick={() => setTelaAtual('alimentacao')}
            >
              <h3>Alimentação</h3>
              <p>Organize suas refeições e seu orçamento.</p>
            </article>

            <article className="cartao-painel">
              <h3>Exercícios</h3>
              <p>Organize suas atividades físicas.</p>
            </article>
          </section>
        </main>
    );
  }

  return (
      <div className="container">
        <section className="lado-esquerdo">
          <h1>HealthyWork</h1>
          <p>Cuide da sua saúde, mesmo com uma rotina corrida.</p>
        </section>

        <section className="lado-direito">
          <form className="formulario" onSubmit={handleSubmit}>
            <h2>{modoCadastro ? 'Criar conta' : 'Bem-vindo de volta!'}</h2>
            <p>
              {modoCadastro
                  ? 'Preencha seus dados para começar.'
                  : 'Entre na sua conta para continuar.'}
            </p>

            {modoCadastro && (
                <input
                    type="text"
                    placeholder="Nome completo"
                    value={nome}
                    onChange={(event) => setNome(event.target.value)}
                    required
                />
            )}

            <input
                type="email"
                placeholder="E-mail"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
            />

            <input
                type="password"
                placeholder="Senha"
                value={senha}
                onChange={(event) => setSenha(event.target.value)}
                required
            />

            {mensagem && (
                <p className={erro ? 'mensagem erro' : 'mensagem sucesso'}>
                  {mensagem}
                </p>
            )}

            <button className="botao-principal" type="submit" disabled={carregando}>
              {carregando
                  ? 'Aguarde...'
                  : modoCadastro
                      ? 'Cadastrar'
                      : 'Entrar'}
            </button>

            <p className="alternar-modo">
              {modoCadastro ? 'Já possui uma conta?' : 'Ainda não possui uma conta?'}
              <button
                  type="button"
                  onClick={() => {
                    setModoCadastro(!modoCadastro);
                    setMensagem('');
                    setErro(false);
                  }}
              >
                {modoCadastro ? ' Fazer login' : ' Criar conta'}
              </button>
            </p>
          </form>
        </section>
      </div>
  );
}

export default App;
