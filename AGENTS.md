# Instructions for AI Assistants and Developers

This repository is governed by a locked project plan and strict team ownership rules. Every coding AI agent and human contributor **must** adhere to the instructions defined in this document before writing, refactoring, or reviewing code.

---

## 1. Mandatory Pre-Requisite Reading

Before editing any code or documentation, you **must** read and understand:
- [docs/LOCKED_PLAN.md](docs/LOCKED_PLAN.md) — The locked scope, stack, boundaries, and non-goals.
- [docs/TASKS.md](docs/TASKS.md) — The ordered implementation tasks and strict module ownership.
- [docs/API_CONTRACT.md](docs/API_CONTRACT.md) — The formal JSON request/response schema.

---

## 2. Core Governance and Git Rules

1. **Assigned Branch and Scope Only**:
   - Work **only** on your assigned Git branch (`android-frontend`, `routing-engine`, `fleet-optimization`, or `backend-integration`).
   - Edit **only** files within your owned directories as assigned in [docs/TASKS.md](docs/TASKS.md) and [docs/MODULE_OWNERSHIP.md](docs/MODULE_OWNERSHIP.md).
2. **Never Push Directly to `main`**:
   - The `main` branch is protected. All changes must go through a pull request and be reviewed and approved by the Team Leader.
3. **No Unapproved Cross-Module Modifications**:
   - Never modify another teammate's owned folder without explicit approval from the Team Leader.
4. **Frozen Architecture & Dependencies**:
   - Do not change shared API fields, dependency files (`requirements.txt`, `pyproject.toml`, `build.gradle.kts`), system architecture, or folder structure without prior team consensus and Team Leader approval.
5. **Small, Deterministic Commits**:
   - Keep implementations small, deterministic, focused, and testable.
   - Write clear Conventional Commit messages (`feat:`, `fix:`, `docs:`, `test:`, `refactor:`).
   - Submit clear, concise pull requests.

---

## 3. Strict Architectural Guardrails & Excluded Technologies

Do **NOT** introduce, suggest, or add any of the following to this project:
- **No Google Maps SDK or Google Traffic APIs** (OSMDroid / OpenStreetMap is planned for map rendering).
- **No God's Eye View** as a traffic, routing, or navigation data source.
- **No Real-Time Production Dispatch or Real GPS Hardware**: This is an academic prototype and simulation only. Real vehicles and actual emergency services are never tracked.
- **No Heavy Distributed Technologies**: No Firebase, PostgreSQL, Docker, WebSockets, or Google OR-Tools.
- **No Commercial / Out-of-Scope Features**: No user authentication / accounts, push notifications, billing/payments, or physical hardware integration.
- **No Feature Invention**: Do not invent new capabilities or expand project scope. If a prompt or request conflicts with the locked plan, stop immediately and ask the Team Leader.

---

## 4. Security & Credential Hygiene

- **Never commit secrets**: Do not commit API keys, access tokens, credentials, `.env` files, local database files (`*.db`, `*.sqlite3`), or personal data to Git history.
- Any optional external trial API keys (such as an optional TomTom traffic adapter post-V1) must live only in local, uncommitted `.env` files and must remain disabled by default.

---

## 5. Testing & Validation Requirement

- **Always run relevant tests before opening a pull request**:
  - Backend/Routing/Optimization: run `pytest` (ensure 100% pass rate).
  - Android: verify build compilation with Gradle.
- Do not claim tests pass without running them. Do not create fake mocks that bypass test validation.
