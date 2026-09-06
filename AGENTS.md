# Instructions for AI Assistants and Developers

This repository is governed by a locked project plan and strict team ownership rules. Every coding AI agent and human contributor **must** adhere to the instructions defined in this document before writing, refactoring, or reviewing code.

---

## 1. Mandatory Pre-Requisite Reading

Before editing any code or documentation, you **must** read and understand:
- [docs/LOCKED_PLAN.md](docs/LOCKED_PLAN.md) — The locked scope, stack, boundaries, and non-goals.
- [docs/TASKS.md](docs/TASKS.md) — The ordered implementation tasks and strict module ownership.
- [docs/API_CONTRACT.md](docs/API_CONTRACT.md) — The formal JSON request/response schema.

---

## 2. Team Roster & Module Ownership

| Member | Name | Primary Branch | Owned Work Directory |
| :--- | :--- | :--- | :--- |
| **Member 1 (Leader)** | **Pratik** | `android-frontend` | `android/EmergencyFleetRouting/` & project-wide integration |
| **Member 2** | **Piyush** | `routing-engine` | `backend/routing/`, `backend/tests/test_routing*.py` |
| **Member 3** | **Gayatri** | `fleet-optimization` | `backend/optimization/`, `backend/tests/test_optimization*.py` |
| **Member 4** | **Samrudhi** | `backend-integration`| `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/` |

---

## 3. Core Governance and Git Rules

1. **Strict Folder Isolation & Leader Integration Rule**:
   - **Do NOT edit or touch another member's work folder.** Every member and coding AI must work strictly inside their assigned directory.
   - **All connections between modules must be connected and integrated exclusively by Member 1 (Leader - Pratik).** Individual members build isolated, testable modules conforming to interfaces, while Pratik handles the cross-module connections and integration.
2. **Assigned Branch Only**:
   - Work **only** on your assigned Git branch (`android-frontend`, `routing-engine`, `fleet-optimization`, or `backend-integration`).
3. **Never Push Directly to `main`**:
   - The `main` branch is protected. All changes must go through a pull request and be reviewed and approved by the Team Leader (Pratik).
4. **Frozen Architecture & Dependencies**:
   - Do not change shared API fields, dependency files (`requirements.txt`, `pyproject.toml`, `build.gradle.kts`), system architecture, or folder structure without prior team consensus and Team Leader approval.
5. **Small, Deterministic Commits**:
   - Keep implementations small, deterministic, focused, and testable.
   - Write clear Conventional Commit messages (`feat:`, `fix:`, `docs:`, `test:`, `refactor:`).
   - Submit clear, concise pull requests.

---

## 4. Strict Architectural Guardrails & Excluded Technologies

Do **NOT** introduce, suggest, or add any of the following to this project:
- **No Google Maps SDK or Google Traffic APIs** (OSMDroid / OpenStreetMap is planned for map rendering).
- **No God's Eye View** as a traffic, routing, or navigation data source.
- **No Real-Time Production Dispatch or Real GPS Hardware**: This is an academic prototype and simulation only. Real vehicles and actual emergency services are never tracked.
- **No Heavy Distributed Technologies**: No Firebase, PostgreSQL, Docker, WebSockets, or Google OR-Tools.
- **No Commercial / Out-of-Scope Features**: No user authentication / accounts, push notifications, billing/payments, or physical hardware integration.
- **No Feature Invention**: Do not invent new capabilities or expand project scope. If a prompt or request conflicts with the locked plan, stop immediately and ask the Team Leader.

---

## 5. Security & Credential Hygiene

- **Never commit secrets**: Do not commit API keys, access tokens, credentials, `.env` files, local database files (`*.db`, `*.sqlite3`), or personal data to Git history.
- Any optional external trial API keys (such as an optional TomTom traffic adapter post-V1) must live only in local, uncommitted `.env` files and must remain disabled by default.

---

## 6. Testing & Validation Requirement

- **Always run relevant tests before opening a pull request**:
  - Backend/Routing/Optimization: run `pytest` (ensure 100% pass rate).
  - Android: verify build compilation with Gradle.
- Do not claim tests pass without running them. Do not create fake mocks that bypass test validation.
