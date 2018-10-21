#!/usr/bin/env bash

# Compilation script for POSIX-compliant systems

src=src
dist=dist

cores=$(echo cores/*)
cores=${cores// /:}

mkdir -p $dist
javac -Xlint:all -d $dist -cp $cores $(find $src -name "*.java")
