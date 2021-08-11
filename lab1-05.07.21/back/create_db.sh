#!/bin/zsh
cat <<EOF | docker exec --interactive postgresql sh
psql -U postgres
CREATE DATABASE soa1;
\l
EOF