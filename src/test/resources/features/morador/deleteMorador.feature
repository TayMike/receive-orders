#language: pt

Funcionalidade: Delete um morador

  Cenário: Delete um morador
    Dado que existe um morador cadastrado - Delete
    Quando o morador for deletado pelo ID - Delete
    Então o morador deletado não será retornado no sistema - Delete