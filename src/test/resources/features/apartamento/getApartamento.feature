#language: pt

Funcionalidade: Criar um apartamento

  Cenário: Criar um apartamento
    Dado que existe um apartamento cadastrado - Get
    Quando um apartamento for buscado pelo ID - Get
    Então o apartamento será retornado no sistema - Get