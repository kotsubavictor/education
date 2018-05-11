#!/bin/bash
sensors -u 2>/dev/null | grep -E 'temp[0-9]+_input' | grep -oE '[0-9]+\.[0-9]+'