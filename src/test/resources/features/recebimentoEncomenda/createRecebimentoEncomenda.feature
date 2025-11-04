#language: pt

Funcionalidade: Criar um RecibementoEncomenda

  Cenário: Criar um RecibementoEncomenda
    Dado que foi criado um recebimentoEncomenda cadastrado - Criar
    E que foi construído um apartamento com recebimentoEncomenda - Criar
    Quando o recebimentoEncomenda for cadastrado - Criar
    Então o recebimentoEncomenda será salvo no sistema - Criar
    E o recebimentoEncomenda deve estar no formato esperado - Criar