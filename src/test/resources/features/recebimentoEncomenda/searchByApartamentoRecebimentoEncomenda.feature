#language: pt

Funcionalidade: Search um recebimentoEncomenda

  Cenário: Search um recebimentoEncomenda
    Dado que existem vários recebimentoEncomenda cadastrados - SearchByApartamentoRecebimento
    Quando os recebimentoEncomenda forem buscados - SearchByApartamentoRecebimento
    Então os recebimentoEncomenda serão retornados no sistema - SearchByApartamentoRecebimento