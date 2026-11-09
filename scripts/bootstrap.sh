#!/usr/bin/env bash
set -euo pipefail
if ! reference=$(git config --path --get agentic.workflowsPath); then
  echo "workflows: set the machine-local git config agentic.workflowsPath to the shared checkout." >&2
  exit 1
fi
if [ ! -f "$reference/scripts/workflows.py" ]; then
  echo "workflows: the registered shared checkout is not available." >&2
  exit 1
fi
exec python3 -B "$reference/scripts/workflows.py" "$@" --project "$(git rev-parse --show-toplevel)"
