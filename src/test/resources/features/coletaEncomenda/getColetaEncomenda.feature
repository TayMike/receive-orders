#language: pt

Funcionalidade: Get um coletaEncomenda

  Cenário: Get um coletaEncomenda
    Dado que existe um coletaEncomendaId cadastrado - Get
    Quando um coletaEncomendaId for buscado pelo ID - Get
    Então o coletaEncomendaId será retornado no sistema - Get