# Dynamic Emergency Fleet Routing Android Application

An intelligent, real-time dispatch and dynamic routing platform designed for emergency response fleets (Ambulances, Fire Services, and Police). The platform combines an Android Jetpack Compose mobile client with a high-performance Python FastAPI backend, graph-based routing algorithms (NetworkX Dijkstra/A*), and multi-criteria fleet optimization.

---

## 📌 Project Overview

During critical emergencies, every second counts. Traditional routing systems often fail to account for emergency vehicle dynamics, sudden road closures, dynamic traffic congestion, and multi-incident dispatch prioritization. 

This project solves this challenge through:
1. **Dynamic Shortest-Path Routing**: Graph-based pathfinding factoring in real-time traffic speeds and dynamic road blockage avoidance.
2. **Optimal Fleet Dispatch**: Multi-criteria matching of emergency incidents to the closest, most suitable emergency vehicle.
3. **Dispatcher & Driver Mobile UI**: Modern Android application providing real-time tracking, live turn-by-turn waypoints, and incident status management.

---

## 🚀 Key Features

- **Real-Time Incident Reporting**: Fast dispatch creation with GPS coordinates, priority levels, and incident classifications.
- **Dynamic Routing Engine**: NetworkX-powered Dijkstra and A* pathfinding with live traffic congestion weighting and closure avoidance.
- **Intelligent Fleet Optimization**: Multi-factor vehicle selection considering distance, vehicle type, fuel level, and equipment compatibility.
- **Interactive Jetpack Compose UI**: Clean MVVM architecture with Dashboard, Incident Creator, Live Map, Fleet Management, Assignment Details, and Incident History.
- **RESTful Architecture**: Strongly-typed JSON contracts between Kotlin Retrofit client and Python FastAPI server.

---

## 🏗️ Architecture Summary

```
+-------------------------------------------------------------------+
|                        Android Client                             |
|  - Jetpack Compose Screens (Dashboard, Map, Dispatch, History)    |
|  - MVVM Architecture + Kotlin Coroutines / StateFlow              |
|  - Retrofit REST Client                                           |
+---------------------------------+---------------------------------+
                                  | HTTP / JSON
                                  v
+---------------------------------+---------------------------------+
|                        FastAPI Backend                            |
|  - REST Endpoints (/emergencies, /vehicles, /assignments, /routes)|
|  - Pydantic v2 Request/Response Validation                        |
|  - SQLite + SQLAlchemy ORM Database Persistence                   |
+-------------------+-------------------------------+---------------+
                    |                               |
                    v                               v
+-------------------+-------------+   +-------------+---------------+
|     Dynamic Routing Engine      |   |   Fleet Optimization Engine |
|  - NetworkX Graph Topology      |   |  - Multi-Criteria Heuristic |
|  - Dijkstra / A* Pathfinding    |   |  - Priority Pre-emption     |
|  - Dynamic Weight Modifiers     |   |  - ETA & Resource Scoring   |
+---------------------------------+   +-----------------------------+
```

---

## 📁 Repository Structure

```
EmergencyFleetRouting/
├── android/
│   └── EmergencyFleetRouting/       # Android Jetpack Compose project
│       ├── app/
│       │   ├── src/main/java/com/emergency/routing/
│       │   │   ├── data/api/        # Retrofit API interface & models
│       │   │   ├── navigation/      # Jetpack Compose Navigation graph
│       │   │   ├── ui/screens/      # Dashboard, Map, Incident, Vehicle screens
│       │   │   └── ui/theme/        # Compose theme, colors, typography
│       │   └── build.gradle.kts
│       ├── build.gradle.kts
│       └── settings.gradle.kts
├── backend/
│   ├── api/                         # FastAPI router modules
│   ├── database/                    # SQLAlchemy engine & ORM models
│   ├── models/                      # Pydantic data schemas
│   ├── routing/                     # NetworkX routing engine & algorithms
│   ├── optimization/                # Fleet selection & assignment engine
│   ├── simulation/                  # Traffic & road closure simulation
│   ├── tests/                       # Backend unit & integration tests
│   ├── main.py                      # FastAPI application entry point
│   ├── requirements.txt             # Python dependencies
│   └── pyproject.toml
├── docs/
│   ├── API_CONTRACT.md              # REST JSON API specification
│   ├── MODULE_OWNERSHIP.md          # 4-member roles and responsibilities
│   └── TEAM_WORKFLOW.md             # Git branch & pull request guidelines
├── tests/                           # Root end-to-end and shared tests
├── .gitignore
├── LICENSE
└── README.md
```

---

## 👥 Team Roles & Responsibilities

| Role | Member | Primary Branch | Focus Area |
| :--- | :--- | :--- | :--- |
| **Team Leader** | Member 1 | `android-frontend` | Android Jetpack Compose UI, Retrofit API integration, PR reviews |
| **Routing Specialist** | Member 2 | `routing-engine` | NetworkX road graphs, Dijkstra/A* routing, traffic weights |
| **Optimization Specialist** | Member 3 | `fleet-optimization` | Fleet dispatch heuristics, vehicle ranking, capacity constraints |
| **Backend & DB Engineer** | Member 4 | `backend-integration`| FastAPI application, SQLite database, REST controllers |

For detailed breakdown, refer to [docs/MODULE_OWNERSHIP.md](docs/MODULE_OWNERSHIP.md).

---

## 🛠️ Basic Setup Instructions

### Prerequisites
- **Git**
- **Python 3.10+** (Python 3.12 recommended)
- **JDK 17+** and **Android Studio Ladybug or newer**

### 1. Clone the Repository
```bash
git clone https://github.com/Pratik-R-Palkar/EmergencyFleetRouting.git
cd EmergencyFleetRouting
```

### 2. Backend Setup
```bash
cd backend
python -m venv .venv

# On Windows:
.venv\Scripts\activate
# On Linux/macOS:
# source .venv/bin/activate

pip install -r requirements.txt
uvicorn main:app --reload --port 8000
```
- Interactive Swagger API docs: `http://localhost:8000/docs`
- Health check: `http://localhost:8000/health`

### 3. Android Setup
1. Open Android Studio.
2. Select **Open** and select the `android/EmergencyFleetRouting` directory.
3. Allow Gradle to sync dependencies.
4. Run the app on an Android Emulator or physical device (API 26+).

---

## 🌿 Branch and Pull-Request Workflow

1. **No direct pushes to `main`**.
2. Switch to your allocated branch before writing code:
   ```bash
   git checkout <your-branch-name>
   ```
3. Test locally and commit with conventional messages:
   ```bash
   git add .
   git commit -m "feat: implement initial dijkstra routing graph"
   git push origin <your-branch-name>
   ```
4. Open a Pull Request against `main`.
5. The Team Leader reviews and merges the PR.

See [docs/TEAM_WORKFLOW.md](docs/TEAM_WORKFLOW.md) for complete details.
