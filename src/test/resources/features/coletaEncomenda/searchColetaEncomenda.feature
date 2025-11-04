#language: pt

Funcionalidade: Search um coletaEncomenda

  Cenário: Search um coletaEncomenda
    Dado que existem vários coletaEncomenda cadastrados - Search
    Quando os coletaEncomenda forem buscados - Search
    Então os coletaEncomenda serão retornados no sistema - Search