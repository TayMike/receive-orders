#language: pt

Funcionalidade: Search um recebimentoEncomenda

  Cenário: Search um recebimentoEncomenda
    Dado que existem vários recebimentoEncomenda cadastrados - Search
    Quando os recebimentoEncomenda forem buscados - Search
    Então os recebimentoEncomenda serão retornados no sistema - Search