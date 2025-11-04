#language: pt

Funcionalidade: Get um recebimentoEncomenda

  Cenário: Get um recebimentoEncomenda
    Dado que existe um recebimentoEncomenda cadastrado - Get
    Quando um recebimentoEncomenda for buscado pelo ID - Get
    Então o recebimentoEncomenda será retornado no sistema - Get