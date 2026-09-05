# Module Ownership & Team Responsibilities

This document defines the architectural responsibilities, file ownership, and direct scopes for each of the 4 team members on the **Dynamic Emergency Fleet Routing** project.

---

## Team Allocation Matrix

| Team Member | Role / Focus Area | Primary Git Branch | Primary Code Directories |
| :--- | :--- | :--- | :--- |
| **Member 1 (Leader)** | Android UI, Mobile Integration, PR Reviews | `android-frontend` | `android/EmergencyFleetRouting/` |
| **Member 2** | Dynamic Routing Engine (NetworkX, Dijkstra, A*) | `routing-engine` | `backend/routing/`, `backend/tests/test_routing*.py` |
| **Member 3** | Fleet Selection & Dispatch Optimization | `fleet-optimization` | `backend/optimization/`, `backend/tests/test_optimization*.py` |
| **Member 4** | FastAPI Application, Database (SQLite/SQLAlchemy), REST APIs | `backend-integration` | `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/` |

---

## Detailed Responsibilities

### Member 1 (Team Leader)
- **Scope**:
  - Android Jetpack Compose user interface development.
  - Screen implementations: Dashboard, New Emergency, Live Map, Vehicles List, Assignment Details, and Incident History.
  - State management using MVVM architecture and Kotlin Coroutines/Flow.
  - Retrofit HTTP client integration matching `docs/API_CONTRACT.md`.
  - Code reviews, pull-request approvals, and releases into `main`.
- **First Task**: Set up Android Studio, build the scaffold Jetpack Compose app, and wire the Dashboard screen to sample state.

---

### Member 2 (Routing Engine Specialist)
- **Scope**:
  - Graph modeling of road networks using `NetworkX`.
  - Implement shortest-path algorithms (Dijkstra and A*) factoring in edge weights (distance, speed limits).
  - Dynamic weight recalculation based on real-time traffic congestion factors.
  - Penalty and exclusion logic for road closures and incident hazard zones.
  - Route waypoint calculation and ETA estimation logic.
- **First Task**: Implement the graph construction function in `backend/routing/engine.py` and run Dijkstra on a sample network graph.

---

### Member 3 (Fleet Selection & Optimization Specialist)
- **Scope**:
  - Multi-criteria decision-making algorithm for vehicle assignment.
  - Ranking available vehicles by:
    - Distance / travel time to incident (using Member 2's routing estimates).
    - Emergency vehicle type suitability (Ambulance, Fire Truck, Police).
    - Equipment availability, vehicle capacity, and fuel constraints.
  - Dynamic re-assignment logic when higher-priority emergencies emerge.
- **First Task**: Implement the scoring heuristic in `backend/optimization/dispatcher.py` to pick the best vehicle given an emergency priority and distance list.

---

### Member 4 (Backend & Database Engineer)
- **Scope**:
  - FastAPI server configuration, middleware, CORS, and lifespan event management.
  - SQLite database schemas, migrations, and SQLAlchemy ORM models.
  - REST endpoint controllers in `backend/api/` matching `docs/API_CONTRACT.md`.
  - Integration of Member 2 (Routing) and Member 3 (Optimization) services into the FastAPI endpoints.
  - Simulation service for traffic and closure updates.
- **First Task**: Implement SQLAlchemy ORM models in `backend/database/models.py` and connect SQLite session initialization.

---

## Boundaries & Collaboration Guidelines

1. **Shared Schemas**:
   - `backend/models/schemas.py` holds shared Pydantic models. Any changes must be coordinated between Member 1 and Member 4 to keep Android Retrofit models and FastAPI payloads in sync.
2. **Interface Contracts**:
   - Routing engine interface: `backend/routing/engine.py`
   - Fleet optimization interface: `backend/optimization/dispatcher.py`
   - Modifying method signatures in these interfaces requires communication with Member 4 (Backend Integration).
