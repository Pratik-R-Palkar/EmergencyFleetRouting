# Team Git & Collaboration Workflow

This document outlines the branching model, pull request process, and collaboration rules for the **Dynamic Emergency Fleet Routing** project. All team members must adhere to these policies to ensure stability and clean integration.

---

## 1. Core Branch Policy

1. **`main` is Protected**:
   - Nobody pushes directly to `main` after initial repository setup.
   - The `main` branch must always represent deployable, working code with passing tests.
2. **Dedicated Member Branches**:
   - Each team member works exclusively within their allocated feature branch:
     - **Member 1 (Leader)**: `android-frontend`
     - **Member 2**: `routing-engine`
     - **Member 3**: `fleet-optimization`
     - **Member 4**: `backend-integration`
3. **Short-Lived Task Branches**:
   - For specific features or bugfixes, members may branch off their allocated branch (e.g., `feat/dijkstra-weights` branched from `routing-engine`).

---

## 2. Pull Request (PR) Workflow

Every completed change must be integrated into `main` via a GitHub Pull Request:

```
[Local Development] 
       ↓ 
[Feature Branch (e.g. routing-engine)] 
       ↓ 
[Push to Origin] 
       ↓ 
[Open Pull Request against main] 
       ↓ 
[Leader Review & CI Validation] 
       ↓ 
[Merged into main]
```

### Pull Request Rules
1. **Target Branch**: Always open PRs against `main`.
2. **Review Requirement**: The **Team Leader (Member 1)** must review, approve, and merge all pull requests.
3. **PR Description Checklist**:
   - Summary of what was changed and why.
   - Which module was modified.
   - Evidence of testing (e.g., test output, screenshots for Android UI).
   - Any dependencies added.
4. **Clean Sync**: Before submitting a PR, pull the latest changes from `main` into your working branch:
   ```bash
   git fetch origin
   git merge origin/main
   # Resolve any conflicts locally and verify tests pass
   git push origin <your-branch>
   ```

---

## 3. Commit Message Standards

Use clear, descriptive commit messages following the Conventional Commits style:

- `feat: <description>`: A new feature or capability (e.g., `feat: implement A* heuristic for road network`)
- `fix: <description>`: A bug fix (e.g., `fix: handle disconnected graph nodes gracefully`)
- `docs: <description>`: Documentation changes only (e.g., `docs: update API contract for vehicle status`)
- `test: <description>`: Adding or fixing tests (e.g., `test: add unit tests for fleet dispatcher`)
- `refactor: <description>`: Code changes that neither fix a bug nor add a feature

**Examples of bad commit messages**:
- `fixed stuff`
- `update`
- `asdf`
- `wip`

---

## 4. Conflict Avoidance & Code Boundaries

- Adhere strictly to [MODULE_OWNERSHIP.md](MODULE_OWNERSHIP.md).
- Avoid modifying code owned by another teammate without prior coordination.
- Shared models and API contracts in `backend/models/` and `docs/API_CONTRACT.md` require consensus before modification.
