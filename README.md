🎀 Meu Brechó API

Moda circular, inclusão e tecnologia feita com propósito. Welcome to the girly coded side of tech! ✨

Um sistema back-end completo e modelado sob medida para a gestão de um brechó online. Muito além de um simples e-commerce genérico, esta API foi pensada para abraçar as nuances da moda circular, permitindo não apenas a venda de acervo próprio, mas também a gestão inteligente de peças em consignação.

💖 O que torna este projeto especial? (Diferenciais)

A maioria dos sistemas de e-commerce não entende como um brechó funciona. Aqui, a regra de negócio foi moldada para a vida real:

♻️ Economia Circular de Verdade: Uma usuária pode ser CLIENTE (apenas compra), CONSIGNADX (apenas fornece peças) ou AMBOS. O sistema calcula automaticamente a porcentagem de repasse das peças vendidas.

✨ 100% Inclusivo: Sabemos que moda é expressão. Nossa entidade de usuário possui mapeamento nativo de Pronomes, garantindo que a comunicação do sistema seja sempre respeitosa e acolhedora com todes.

🔍 Transparência Absoluta: Roupas de segunda mão têm história. O banco de dados foi modelado para registrar detalhadamente o Estado de Conservação e os Detalhes de Avaria de cada peça, garantindo zero surpresas quando a "sacolinha" chegar em casa.

🛍️ Lógica de Peça Única: Diferente do varejo tradicional, no brechó o estoque é sempre 1. O sistema gerencia a transição de status (de DISPONIVEL para VENDIDA) assim que a sacolinha é paga.

👩‍💻 Como isso ajuda as usuárias?

Para quem compra: Uma vitrine digital super organizada por categorias (Parte de cima, Calçados, etc.), clareza total sobre o estado da peça e um fluxo de "Sacolinha" intuitivo.

Para quem fornece (Consignação): Segurança de que a peça está rastreada no sistema, atrelada ao seu ID de fornecedora, com o valor de repasse devidamente calculado no banco de dados.

Para a dona do Brechó: Facilidade para cadastrar o acervo, gerenciar pedidos (em aberto, pagos, enviados) e não se perder com as finanças de terceiros.

🛠️ Tecnologias Utilizadas (The Tech Stack)

A arquitetura foi construída visando um código limpo (Clean Code), fácil manutenção e alta escalabilidade:

Java 17+ (Fortemente tipado e robusto)

Spring Boot 3 (Framework principal)

Spring Data JPA / Hibernate (Mapeamento Objeto-Relacional)

Lombok (Para um código enxuto, usando o padrão @Builder)

PostgreSQL / H2 Database (Persistência de dados)

Padrão MVC (Model, Repository, Service, Controller)

🚀 Como rodar o projeto localmente

Se você quiser testar essa lindeza na sua máquina, siga os passos:

Clone este repositório:

git clone [https://github.com/seu-usuario/meu-brecho-api.git](https://github.com/seu-usuario/meu-brecho-api.git)


Abra a pasta do projeto na sua IDE favorita (IntelliJ IDEA super recomendado).

Atualize as dependências do Maven/Gradle.

O projeto conta com uma classe de Seed (TestConfig.java) que alimenta o banco de dados automaticamente com algumas peças e usuárias ao iniciar, para que você não comece com uma vitrine vazia!

Execute a classe MeuBrechoApplication e acesse a API via http://localhost:8080.
