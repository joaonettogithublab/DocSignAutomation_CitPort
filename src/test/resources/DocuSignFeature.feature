# language: pt

Funcionalidade: Automação do DocuSign PowerForm

Como um Gestor de Contas da Ci&t
Eu quero executar automaticamente o preenchimento do formulário DocuSign
Para otimizar de forma massiva e automática o prrenchimento das solictações de acesso á Porto

@Docsign @CargaDeDados
Cenario: Preencher o formulário DocuSign com dados da planilha e iniciar a assinatura

  Dado      que  a URL do DocuSign PowerForm está aberta
  Quando    os dados da planilha "CargaDados_Docsign.xlsx" são lidos e iterados
  E         para cada linha de dados, o formulário é preenchido e o botão 'Begin Signing' é clicado
  Então     o pop-up de acordo eletrônico é exibido e aceito
