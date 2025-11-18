language: pt

Funcionalidade: Automação do DocuSign PowerForm

Como um Analista de Testes Sênior
Eu quero automatizar o preenchimento do formulário DocuSign
Para garantir que os dados da planilha sejam carregados corretamente para todos os destinatários.

@Docsign @CargaDeDados
Cenario: Preencher o formulário DocuSign com dados da planilha e iniciar a assinatura

Dado que a URL do DocuSign PowerForm está aberta
Quando os dados da planilha "CargaDados_Docsign.xlsx" são lidos e iterados
E para cada linha de dados, o formulário é preenchido e o botão 'Begin Signing' é clicado
Entao o pop-up de acordo eletrônico é exibido e aceito
