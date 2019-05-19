# Exercício - RSS 

Esta tarefa envolve os conceitos de UI widgets, tarefas assíncronas, RecyclerView, Custom Adapters, Intents, Permissions. 
Faça um clone ou fork deste projeto, siga os passos na ordem sugerida e marque mais abaixo, na sua resposta, quais os passos completados. 
Para entregar o exercício, responda o [formulário de entrega](#) até 20/05/2019, às 23h59.

  1. Faça o porting do código disponível em `MainActivityAntigo.java` para Kotlin, colocando o código correspondente na classe `MainActivity.kt`.
  2. Faça o carregamento do arquivo XML usando uma tarefa assíncrona, seja por meio de `AsyncTask`, seja por meio de `Anko` e `doAsync`. 
  3. Se ainda estiver dando erro, adicione a permissão para acessar internet.  
  4. Altere a aplicação de forma que passe a processar o arquivo XML usando a função `parse` da classe `ParserRSS`. Uma vez processado o XML por meio do parser, é retornado um objeto do tipo `List<ItemRSS>`. 
  5. Use este objeto para popular um `RecyclerView` por meio de um `Adapter` --- o widget deve manter o mesmo id do TextView (`conteudoRSS`).
  6. Crie um adapter personalizado para mostrar título e data para cada item do feed, usando o layout em `res/layout/itemlista.xml` como base. Este layout não deve ser alterado.
  7. Faça com que, ao clicar em um título, o usuário seja direcionado para o navegador. Opcionalmente, pode abrir em uma nova activity com `WebView`.
  8. Modifique a aplicação para que passe a carregar o endereço do feed a partir de uma `SharedPreferences` com a chave `rssfeed`. Inclua a possibilidade de alterar a `SharedPreference` (`rssfeed`) incluindo um botão na `ActionBar` da aplicação. Ao clicar no botão, uma `Activity` deve ser exibida com base em uma `PreferenceScreen` gerada automaticamente por meio de um `Fragment`, como visto em sala. Defina um arquivo em `res/xml/preferencias.xml` para estruturar a tela.

---

# Orientações

  - Comente o código que você desenvolver, explicando o que cada parte faz.
  - Entregue o exercício *mesmo que não tenha completado todos os itens* listados. Marque abaixo apenas o que completou.

----

# Status

| Passo | Completou? |
| ------ | ------ |
| 1 | **sim** |
| 2 | **sim** |
| 3 | **sim** |
| 4 | **sim** |
| 5 | **sim** |
| 6 | **sim** |
| 7 | **sim** |
| 8 | **sim** |