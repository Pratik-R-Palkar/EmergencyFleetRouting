# Module Ownership & Team Responsibilities

This document defines the architectural responsibilities, file ownership, and direct scopes for each of the 4 team members on the **Dynamic Emergency Fleet Routing** project.

---

## Team Allocation Matrix

| Team Member | Member Name | Role / Focus Area | Primary Git Branch | Primary Code Directories |
| :--- | :--- | :--- | :--- | :--- |
| **Member 1 (Leader)** | **Pratik** | Android UI, Mobile Integration, Cross-Module Wiring, PR Reviews | `android-frontend` | `android/EmergencyFleetRouting/` & project-wide integration |
| **Member 2** | **Piyush** | Dynamic Routing Engine (NetworkX, Dijkstra, A*) | `routing-engine` | `backend/routing/`, `backend/tests/test_routing*.py` |
| **Member 3** | **Gayatri** | Fleet Selection & Dispatch Optimization | `fleet-optimization` | `backend/optimization/`, `backend/tests/test_optimization*.py` |
| **Member 4** | **Samrudhi** | FastAPI Application, Database (SQLite/SQLAlchemy), REST APIs | `backend-integration` | `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/` |

---

## Detailed Responsibilities

### Member 1: Pratik (Team Leader)
- **Scope**:
  - Android Jetpack Compose user interface development.
  - Screen implementations: Dashboard, New Emergency, Live Map, Vehicles List, Assignment Details, and Incident History.
  - State management using MVVM architecture and Kotlin Coroutines/Flow.
  - Retrofit HTTP client integration matching `docs/API_CONTRACT.md`.
  - **Cross-Module Connection & Integration**: Exclusively responsible for connecting and wiring all individual components (routing engine, fleet optimization, FastAPI backend, and Android app).
  - Code reviews, pull-request approvals, and releases into `main`.
- **First Task**: Set up Android Studio, build the scaffold Jetpack Compose app, and wire the Dashboard screen to sample state.

---

### Member 2: Piyush (Routing Engine Specialist)
- **Scope**:
  - Graph modeling of road networks using `NetworkX`.
  - Implement shortest-path algorithms (Dijkstra and A*) factoring in edge weights (distance, speed limits).
  - Dynamic weight recalculation based on real-time traffic congestion factors.
  - Penalty and exclusion logic for road closures and incident hazard zones.
  - Route waypoint calculation and ETA estimation logic.
  - **Strict Boundary**: Owns only `backend/routing/`. Does not touch or edit other members' folders.
- **First Task**: Implement the graph construction function in `backend/routing/engine.py` and run Dijkstra on a sample network graph.

---

### Member 3: Gayatri (Fleet Selection & Optimization Specialist)
- **Scope**:
  - Multi-criteria decision-making algorithm for vehicle assignment.
  - Ranking available vehicles by:
    - Distance / travel time to incident (using Member 2's routing estimates).
    - Emergency vehicle type suitability (Ambulance, Fire Truck, Police).
    - Equipment availability, vehicle capacity, and fuel constraints.
  - Dynamic re-assignment logic when higher-priority emergencies emerge.
  - **Strict Boundary**: Owns only `backend/optimization/`. Does not touch or edit other members' folders.
- **First Task**: Implement the scoring heuristic in `backend/optimization/dispatcher.py` to pick the best vehicle given an emergency priority and distance list.

---

### Member 4: Samrudhi (Backend & Database Engineer)
- **Scope**:
  - FastAPI server configuration, middleware, CORS, and lifespan event management.
  - SQLite database schemas, migrations, and SQLAlchemy ORM models.
  - REST endpoint controllers in `backend/api/` matching `docs/API_CONTRACT.md`.
  - Simulation service for traffic and closure updates.
  - **Strict Boundary**: Owns `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/`, and `backend/main.py`. Does not touch routing or optimization folders.
- **First Task**: Implement SQLAlchemy ORM models in `backend/database/models.py` and connect SQLite session initialization.

---

## Boundaries & Collaboration Guidelines

1. **Strict Folder Isolation (Rule)**:
   - **No member is allowed to edit or touch another member's work folder.**
   - Piyush works strictly in `backend/routing/`.
   - Gayatri works strictly in `backend/optimization/`.
   - Samrudhi works strictly in `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/`, and `backend/main.py`.
   - **All connections and integration between them will be connected and integrated exclusively by Member 1 (Leader - Pratik).**
2. **Shared Schemas**:
   - `backend/models/schemas.py` holds shared Pydantic models. Any changes must be coordinated with Pratik (Leader) and Samrudhi (Backend) to keep Android Retrofit models and FastAPI payloads in sync.
3. **Interface Contracts**:
   - Routing engine interface: `backend/routing/engine.py`
   - Fleet optimization interface: `backend/optimization/dispatcher.py`
   - Modifying method signatures in these interfaces requires communication with Pratik (Leader) and Samrudhi (Backend Integration).
