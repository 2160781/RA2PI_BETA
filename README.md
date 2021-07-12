# RA2PI_BETA
Versão Beta da aplicação


Com a implementação das diversas spikes, foi possível delimitar as funcionalidades da aplicação final. 
Estas consistem nas seguintes:
--> Iniciar de sessão - De forma a possibilitar o funcionamento da aplicação o utilizador necessitará de efetuar o início de sessão, 
através da leitura de um QR Code. Este varia de utilizador para utilizador;
--> Display inicial - Neste a camara encontra-se sempre ligada e o utilizador pode escolher entre a leitura de um QR Code,
de forma a obter as tarefas do respetivo plano associado, ou poderá interagir com um menu de planos;
--> Leitura de QR Code - Leitura de QR Code, de forma a possibilitar a apresentação da informação do seu plano respetivo;
--> Menu Tarefas - Com a leitura do QR Code, o utilizador poderá navegar pelas diversas tarefas do respetivo plano, 
assim como modificar o estado de cada tarefa. Estas interações são feitas por voz ou ao premir os botões do comando 
dos óculos;
--> Menu plano - Apresentação dos diversos planos existentes, assim como o estado de cada um. Por outro lado, o 
utilizador poderá adicionar novos planos ou entrar no menu das tarefas de cada plano.

Destas funcionalidades, a que necessitou de ser implementada, não tendo como base o trabalho anterior, corresponde ao 
iniciar de sessão da aplicação. Inicialmente, achou-se necessária a implementação do iniciar de sessão, através de uma 
base de dados do tipo firebase, no entanto decidiu-se implementar um sistema de iniciar de sessão com o scan de um QR Code.
De forma, a possibilitar a utilização de diversas contas, foram implementados quatro casos, duas correspondem aos orientadores, 
enquanto que as outras correspondem aos estudantes que realizaram este projeto.
