#!/bin/sh
# entrypoint.sh

# Garante que o diretório de logs exista.
mkdir -p /app/logs

# Ajusta o dono da pasta de logs para o usuário do app.
# Isso funciona no volume montado, corrigindo a permissão em tempo de execução.
chown -R appuser:appgroup /app/logs

# Executa o comando principal (CMD do Dockerfile) usando o usuário 'appuser'.
# O 'exec' faz com que o processo do Java substitua o shell, e o "$@"
# passa quaisquer argumentos que o comando possa ter.
exec gosu appuser "$@"
