#!/usr/bin/env bash

# Launch script for POSIX-compliant systems

cores=$(echo cores/*)
cores=${cores// /:}
cp=.:dist:$cores

java -ea -Xmx2048m -Dwzpath=wz -Dnashorn.args=--language=es6 -cp $cp net.server.Server
