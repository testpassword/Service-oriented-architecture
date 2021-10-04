USERNAME=$1
PORT=$2
ssh -p 2222 s"$USER"@se.ifmo.ru -L "$PORT":localhost:"$PORT"