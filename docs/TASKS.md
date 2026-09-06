# Work Ownership and Ordered Tasks

This document defines the strict task breakdown, code ownership boundaries, and sequential build order for all four team members:
- **Member 1 (Leader)**: **Pratik**
- **Member 2**: **Piyush**
- **Member 3**: **Gayatri**
- **Member 4**: **Samrudhi**

---

## 🔒 Golden Rule: Strict Folder Isolation & Centralized Integration

> [!IMPORTANT]
> **No member is allowed to edit or touch another member's work folder.**
> Every member works exclusively in their own assigned directory.
> **All connections and integrations between individual modules will be connected and wired exclusively by Member 1 (Leader - Pratik).**
> - Piyush builds the routing engine in isolation (`backend/routing/`).
> - Gayatri builds the fleet optimizer in isolation (`backend/optimization/`).
> - Samrudhi builds the core backend, database, and models in isolation (`backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/`).
> - **Pratik (Leader)** reviews, wires, and connects the routing engine, fleet optimizer, REST API, and Android mobile client together.

---

## 1. Member Ownership and Assigned Tasks

### Member 1: Pratik (Team Leader)
- **Assigned Git Branch**: `android-frontend`
- **Owned Folders**: `android/EmergencyFleetRouting/` and project-wide integration.
- **Specific Tasks**:
  1. Build and refine the Jetpack Compose screens:
     - **DashboardScreen**: active incident counts, available fleet status, quick action buttons.
     - **NewEmergencyScreen**: emergency report form (type, priority, coordinates, description).
     - **VehiclesScreen**: list of fleet vehicles with status, fuel, capacity, and equipment.
     - **AssignmentDetailsScreen**: dispatched vehicle details, route ID, ETA, and status update controls.
     - **HistoryScreen**: past resolved emergencies and dispatch logs.
     - **LiveMapScreen / Route View**: map canvas placeholder with route waypoints, traffic status, and detour alert.
  2. Implement local state management and verify screen-to-screen navigation using sample mock data first.
  3. **Connect and Integrate All Modules**:
     - Connect Member 2's (Piyush) routing engine to the backend.
     - Connect Member 3's (Gayatri) fleet optimizer to the backend.
     - Connect Member 4's (Samrudhi) FastAPI REST API to the Android Retrofit client.
  4. Review and merge all teammate pull requests into `main`.
- **Strict Boundary**: Teammates do not touch Android; Pratik manages all cross-module integration.

---

### Member 2: Piyush (Routing Specialist)
- **Assigned Git Branch**: `routing-engine`
- **Owned Folders**: `backend/routing/` and routing unit tests in `backend/tests/test_routing*.py`.
- **Specific Tasks**:
  1. Construct a small, deterministic road graph using `networkx.DiGraph`.
  2. Implement shortest-path algorithms:
     - **Dijkstra** pathfinding based on weighted edge travel times.
     - **A\*** pathfinding using coordinate-based distance heuristics.
  3. Apply dynamic edge weight adjustments using simulated traffic speed multipliers (low, medium, high, blocked).
  4. Implement road closure exclusion logic (removing or penalizing blocked edges so paths automatically detour).
  5. Return structured route results: coordinate waypoints, total distance (km), estimated time of arrival (ETA in minutes), and path availability status.
  6. Write comprehensive unit tests verifying pathfinding accuracy, traffic impact, and closure rerouting.
- **Strict Boundary**: Do not edit or touch other members' folders (`backend/optimization/`, `backend/api/`, `android/`). Your module will be connected to the system by Pratik.

---

### Member 3: Gayatri (Fleet Optimization Specialist)
- **Assigned Git Branch**: `fleet-optimization`
- **Owned Folders**: `backend/optimization/` and optimization unit tests in `backend/tests/test_optimization*.py`.
- **Specific Tasks**:
  1. Implement deterministic, rule-based vehicle selection logic:
     - Filter candidates by `VehicleStatus.AVAILABLE`.
     - Filter by emergency type suitability (e.g., Fire -> Fire Truck, Medical -> Ambulance, Accident -> Rescue Squad/Police).
     - Evaluate incident priority level.
     - Score candidate vehicles factoring in route ETA and distance (provided by the routing interface).
  2. Return structured selection output: assigned vehicle ID, calculated ranking score, and explicit selection reason text.
  3. Implement priority pre-emption evaluation placeholder (evaluating if a priority 1 incident should redirect a lower-priority unit).
  4. Write unit tests validating ranking logic, empty vehicle pools, and fallback behaviors.
- **Strict Boundary**: Do not edit or touch other members' folders (`backend/routing/`, `backend/api/`, `android/`). Your module will be connected to the system by Pratik.

---

### Member 4: Samrudhi (Backend & Database Engineer)
- **Assigned Git Branch**: `backend-integration`
- **Owned Folders**: `backend/api/`, `backend/database/`, `backend/models/`, `backend/simulation/`, and `backend/main.py`.
- **Specific Tasks**:
  1. Maintain and freeze Pydantic v2 schemas (`backend/models/schemas.py`) ensuring compatibility with the Android Retrofit models.
  2. Maintain SQLite database tables, session lifecycles, and SQLAlchemy ORM models (`backend/database/`).
  3. Populate reproducible seed data: pre-configured emergency fleet vehicles, initial stations, and sample locations.
  4. Implement and expose FastAPI REST routes:
     - `POST /api/v1/emergencies`
     - `GET /api/v1/vehicles`
     - `POST /api/v1/assignments`
     - `GET /api/v1/assignments/{assignment_id}`
     - `GET /api/v1/routes/{route_id}`
     - `GET /api/v1/simulation/traffic` and `/closures`
  5. Provide clean interfaces and dependencies so Member 1 (Pratik) can wire in the routing engine and optimizer.
  6. Ensure credentials, secrets, and API keys remain strictly out of the repository and Git history.
  7. Write backend API integration tests verifying all endpoints and HTTP status codes.
- **Strict Boundary**: Do not edit or touch `backend/routing/`, `backend/optimization/`, or `android/`.

---

## 2. Ordered Build Order

To guarantee continuous integration without deadlocks or folder cross-contamination, development must proceed in this exact sequence:

```
Step 1: Samrudhi (Member 4 - Core Backend & Database)
├── Finalizes stable Pydantic schemas, SQLite models, seed data, and initial mock FastAPI endpoints.
└── Merged into main via PR (reviewed by Pratik).
       ↓
Step 2 & 3: Piyush (Member 2) & Gayatri (Member 3) (Core Engines in Parallel)
├── Piyush completes NetworkX road graph, Dijkstra/A*, traffic weights, and closures in backend/routing/.
├── Gayatri completes rule-based vehicle selection and ranking heuristics in backend/optimization/.
└── Both test their isolated modules and submit PRs to main (reviewed by Pratik).
       ↓
Step 4: Pratik (Member 1 - Leader Integration & Wiring)
├── Connects Piyush's routing engine and Gayatri's fleet optimizer into Samrudhi's FastAPI routers.
└── Validates end-to-end backend request/response flow via automated tests.
       ↓
Step 5: Pratik (Member 1 - Frontend Integration)
├── Connects Jetpack Compose UI screens to the live FastAPI REST endpoints via Retrofit.
└── Validates end-to-end mobile user experience on the Android emulator.
       ↓
Step 6: Team Demonstration Milestone
└── The team demonstrates the complete integrated scenario:
    1. Dispatcher creates an emergency in Android.
    2. Backend selects the best vehicle with explanation.
    3. Backend computes initial route and ETA.
    4. A road closure is injected via the simulation service.
    5. Backend recalculates route avoiding the closure, and Android updates the navigation display.
```
