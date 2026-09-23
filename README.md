# Produtos SQLite - atividade prática

Projeto Android em Java para cadastro de produtos com persistência local via SQLite e SQLiteOpenHelper.

## Testes pedidos no roteiro

### Válidos
- Caneta Azul — 2.50
- Caderno Universitário — 15.00
- Borracha — 2.50
- Régua de 30 cm — 3.00
- Marca-texto — 4.50

### Inválidos
- AB — 5.00
- Lápis — -3.00

## Resultado esperado
Os cinco produtos válidos devem aparecer na lista.
Os dois produtos inválidos não devem ser salvos.
Ao fechar e abrir novamente o aplicativo na mesma sessão do emulador, os produtos válidos permanecem armazenados no SQLite.
