#language: pt

Funcionalidade: Search um morador

  Cenário: Search um morador
    Dado que existem vários moradores cadastrados - SearchByApartamentoMorador
    Quando os moradores forem buscados - SearchByApartamentoMorador
    Então os moradores serão retornados no sistema - SearchByApartamentoMorador