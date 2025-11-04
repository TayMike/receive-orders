#language: pt

Funcionalidade: Criar um ColetaEncomenda

  Cenário: Criar um ColetaEncomenda
    Dado que foi criado um coletaEncomenda cadastrado - Criar
    E que foi construído um apartamento com recebimentoEncomenda para coletaEncomenda - Criar
    E que foi construído coletaEncomenda - Criar
    Quando o coletaEncomenda for cadastrado - Criar
    Então o coletaEncomenda será salvo no sistema - Criar
    E o coletaEncomenda deve estar no formato esperado - Criar