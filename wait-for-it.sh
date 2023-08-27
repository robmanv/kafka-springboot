#!/bin/sh
# wait-for-it.sh
# Uma ferramenta simples para esperar até que um host e porta estejam disponíveis.

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

until nc -z "$host" "$port"; do
  echo "Aguardando $host:$port..."
  sleep 1
done

echo "$host:$port está disponível"
exec $cmd
