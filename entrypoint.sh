#!/bin/sh
set -e

# Create logs directory if it doesn't exist
mkdir -p /app/logs

# Change the owner of the /app directory to the appuser
# This ensures the application has write permissions for logs
# and ownership of the files it manages.
chown -R appuser:appgroup /app/logs /app/app.jar

# Execute the main command (CMD from Dockerfile) as the 'appuser'
# 'exec' replaces the shell with the Java process, and '"$@"' passes along any arguments.
exec gosu appuser:appgroup "$@"