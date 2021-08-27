#!/bin/zsh
cat <<EOF | docker exec --interactive postgresql bash
psql -U postgres
CREATE DATABASE soa1;
\l
EOF