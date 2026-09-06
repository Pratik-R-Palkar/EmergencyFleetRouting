# Work Ownership and Ordered Tasks

This document defines the strict task breakdown, code ownership boundaries, and sequential build order for all four team members.

---

## 1. Member Ownership and Assigned Tasks

### Member 1 / Team Leader
- **Assigned Git Branch**: `android-frontend`
- **Owned Folders**: `android/` and frontend-related documentation.
- **Specific Tasks**:
  1. Build and refine the Jetpack Compose screens:
     - **DashboardScreen**: active incident counts, available fleet status, quick action buttons.
     - **NewEmergencyScreen**: emergency report form (type, priority, coordinates, description).
     - **VehiclesScreen**: list of fleet vehicles with status, fuel, capacity, and equipment.
     - **AssignmentDetailsScreen**: dispatched vehicle details, route ID, ETA, and status update controls.
     - **HistoryScreen**: past resolved emergencies and dispatch logs.
     - **LiveMapScreen / Route View**: map canvas placeholder with route waypoints, traffic status, and detour alert.
  2. Implement local state management and verify screen-to-screen navigation using sample mock data first.
  3. Integrate the Retrofit client with the backend REST endpoints **only after** backend endpoints are confirmed and stable.
  4. Review and merge all teammate pull requests into `main`.
- **Strict Boundary**: Do **not** implement routing or vehicle-selection algorithms inside the Android app. All computation belongs on the backend.

---

### Member 2
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

---

### Member 3
- **Assigned Git Branch**: `fleet-optimization`
- **Owned Folders**: `backend/optimization/` and optimization unit tests in `backend/tests/test_optimization*.py`.
- **Specific Tasks**:
  1. Implement deterministic, rule-based vehicle selection logic:
     - Filter candidates by `VehicleStatus.AVAILABLE`.
     - Filter by emergency type suitability (e.g., Fire -> Fire Truck, Medical -> Ambulance, Accident -> Rescue Squad/Police).
     - Evaluate incident priority level.
     - Score candidate vehicles factoring in route ETA and distance (provided by Member 2's routing interface).
  2. Return structured selection output: assigned vehicle ID, calculated ranking score, and explicit selection reason text.
  3. Implement priority pre-emption evaluation placeholder (evaluating if a priority 1 incident should redirect a lower-priority unit).
  4. Write unit tests validating ranking logic, empty vehicle pools, and fallback behaviors.

---

### Member 4
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
  5. Integrate Member 2's routing engine and Member 3's optimization dispatcher **only through their defined interfaces**.
  6. Ensure credentials, secrets, and API keys remain strictly out of the repository and Git history.
  7. Write backend API integration tests verifying all endpoints and HTTP status codes.

---

## 2. Ordered Build Order

To guarantee continuous integration without deadlocks, development must proceed in this exact sequence:

```
Step 1: Member 4 (Foundation)
├── Finalizes stable Pydantic schemas, seed data, and initial mock FastAPI endpoints.
└── Merged into main via PR.
       ↓
Step 2 & 3: Member 2 & Member 3 (Core Engines in Parallel)
├── Member 2 completes NetworkX road graph, Dijkstra/A*, traffic weights, and closures.
├── Member 3 completes rule-based vehicle selection and ranking heuristics.
└── Both submit tested modules via PRs to main.
       ↓
Step 4: Member 4 (Engine Integration)
├── Wires Member 2's routing engine and Member 3's fleet optimizer into FastAPI routers.
└── Validates end-to-end backend request/response flow via automated tests.
       ↓
Step 5: Member 1 (Frontend Integration)
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
