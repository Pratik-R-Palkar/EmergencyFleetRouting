# Locked Project Plan

**Project Name**: Dynamic Emergency Fleet Routing Android Application  
**Project Classification**: Academic Prototype & Simulation  
**Status**: Frozen & Locked  

---

## 1. Project Goal

The system is a **simulation prototype** for emergency vehicle dispatch and routing. 

The workflow is as follows:
1. A dispatcher creates an emergency incident request via the Android mobile application.
2. The FastAPI backend selects the most suitable available vehicle based on deterministic rule-based scoring.
3. The backend calculates the fastest route using a NetworkX graph with Dijkstra or A* pathfinding, factoring in simulated traffic congestion and active road closures.
4. The backend returns the vehicle assignment, estimated time of arrival (ETA), distance, and route coordinate waypoints to the Android app for display.

---

## 2. Locked Technology Stack

| Layer | Tool / Technology | Purpose |
| :--- | :--- | :--- |
| **Version Control & Workflow** | GitHub | Git repository, feature branches, pull requests, code reviews |
| **IDE / Editor** | VS Code / Android Studio | Code editing, terminal, and local Git operations |
| **Mobile Frontend** | Android Studio, Kotlin, Jetpack Compose, Retrofit | Declarative Android UI, MVVM architecture, REST client |
| **Backend Framework** | Python 3.10+, FastAPI, Pydantic v2 | High-performance asynchronous REST API and data validation |
| **Database** | SQLite + SQLAlchemy ORM | Local relational storage for incidents, vehicles, assignments |
| **Routing Engine** | NetworkX with Dijkstra and A* | Graph-based road network modeling and shortest path algorithms |
| **Fleet Optimization** | Python rule-based scoring | Deterministic multi-factor candidate vehicle selection |
| **Map Display** | OpenStreetMap with OSMDroid | Open-source map tile visualization (future map display) |
| **Traffic & Incidents** | Simulated traffic and road closures | Synthetic urban traffic multipliers and blockages (V1) |
| **Testing Harness** | Pytest, Python unittest, Android Emulator | Automated test validation and local runtime verification |

---

## 3. Explicitly Excluded from Version 1 (Non-Goals)

To prevent scope creep and keep the project manageable and robust, the following technologies and features are **strictly excluded** from Version 1:
- ❌ **No Google Maps SDK and Google Traffic Services**: No Google Maps API keys, paid Google Maps services, or Google directions/traffic endpoints.
- ❌ **No God’s Eye View**: Satellite/drone/high-altitude feeds are not to be used as a traffic, routing, or navigation source.
- ❌ **No Real-Time Production Emergency Dispatch**: The platform is strictly an academic demonstration prototype, not for real emergency services.
- ❌ **No Real Vehicle GPS Tracking**: No hardware trackers, OBD-II readers, or real-life GPS fleet telemetry.
- ❌ **No Heavy Infrastructure & Frameworks**: No Firebase, PostgreSQL, Docker containerization, WebSockets, or Google OR-Tools.
- ❌ **No Auxiliary Enterprise Features**: No user accounts/authentication, push notifications, payment gateways, or physical hardware integrations.

---

## 4. Optional Enhancement After Version 1

- **TomTom Traffic Trial API Adapter**: A backend-only `TrafficProvider` adapter may optionally integrate with a TomTom traffic trial API post-V1.
- **Rules for TomTom Adapter**:
  1. It must remain strictly optional and **disabled by default**.
  2. The API key must never be committed to Git; it must reside only in a local, uncommitted `.env` file.
  3. The local simulated traffic engine remains the default, primary demonstration path at all times.

---

## 5. Final System Flow

```
+-----------------------------+
|    Android Application      |
|  - Jetpack Compose UI       |
|  - Retrofit HTTP Client     |
+--------------+--------------+
               | HTTP REST (JSON)
               v
+--------------+--------------+
|     FastAPI REST API        |
|  - /api/v1/emergencies      |
|  - /api/v1/vehicles         |
|  - /api/v1/assignments      |
|  - /api/v1/routes           |
|  - /api/v1/simulation       |
+--------------+--------------+
               |
    +----------+--------------------+--------------------+
    |                               |                    |
    v                               v                    v
+---+-------------------+   +-------+------------+   +---+--------------------+
| SQLite Database       |   | Vehicle Selection  |   | NetworkX Routing       |
| (SQLAlchemy ORM)      |   | (Rule-Based Score) |   | (Dijkstra / A*)        |
| - Emergencies         |   | - Vehicle type     |   | - Graph road network   |
| - Vehicles            |   | - Availability     |   | - Dynamic traffic      |
| - Assignments         |   | - Incident priority|   | - Closure avoidance    |
| - Road closures       |   | - Distance / ETA   |   | - Waypoints & ETA      |
+-----------------------+   +--------------------+   +---+--------------------+
                                                         ^
                                                         |
                                             +-----------+------------+
                                             | Traffic/Closure Sim    |
                                             | - Low/Med/High/Blocked |
                                             | - Synthetic closures   |
                                             +------------------------+
```
