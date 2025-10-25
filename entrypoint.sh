#!/bin/sh
set -e

# Create logs directory if it doesn't exist
mkdir -p /app/logs

# Change the owner of the /app directory to the appuser
# This ensures the application has the correct permissions
chown -R appuser:appgroup /app

# Execute the main command (CMD from Dockerfile) as the 'appuser'
# 'exec' replaces the shell with the Java process, and '"$@"' passes along any arguments.
exec gosu appuser:appgroup "$@"