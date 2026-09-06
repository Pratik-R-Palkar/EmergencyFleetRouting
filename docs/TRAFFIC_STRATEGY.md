# Traffic Strategy and Simulation Specification

This document details the traffic modeling, road closure simulation, UI visibility roles, and safety disclaimers for the **Dynamic Emergency Fleet Routing** project.

---

## 1. Version 1 Traffic Simulation Model

To maintain complete determinism, zero external cloud dependencies, and zero cost, Version 1 relies entirely on a **synthetic traffic and road closure simulation engine**.

### 1.1 Discrete Traffic Congestion Levels
The road network edges in the routing graph use four discrete traffic condition levels:

| Level | Congestion Factor (Multiplier) | Impact on Effective Speed | Description |
| :--- | :--- | :--- | :--- |
| **LOW** | 1.0x | 100% of speed limit | Free-flowing traffic conditions; optimal travel speeds. |
| **MEDIUM** | 1.35x | ~74% of speed limit | Moderate urban congestion, intersections, light rush hour. |
| **HIGH** | 1.85x | ~54% of speed limit | Heavy congestion, significant bottlenecks, severe delays. |
| **BLOCKED** | Infinite / Impassable | 0% (Edge excluded) | Road section closed to all vehicular traffic. |

### 1.2 Dynamic Road Closures
- Road closures represent immediate obstacles such as construction, fallen trees, flooding, or police cordons.
- When an edge is flagged as closed, the routing engine assigns it an infinite traversal weight (or removes the edge temporarily from the active graph), forcing Dijkstra or A* to calculate an alternative detour around the affected segment.
- Closures can be triggered dynamically via the backend simulation router (`POST /api/v1/simulation/closures`) to test live rerouting.

---

## 2. Role-Based Map Views

The application separates concerns between the dispatcher's system-wide control view and the driver's task-focused navigation view:

### 2.1 Dispatcher Map View (Global Command)
The dispatcher requires comprehensive situational awareness across the entire coverage sector:
- All fleet vehicles and their current statuses (Available, Dispatched, En Route, On Scene).
- All reported emergency incidents and their severity levels (Priority 1 through 5).
- Currently active calculated routes for all dispatched vehicles.
- Full visual overlays of current traffic congestion levels across road segments.
- Visual markers for active road closures.

### 2.2 Driver Map View (Tactical Navigation)
The driver requires minimal distraction and focused tactical instructions:
- Displays **only** the driver's assigned emergency incident and destination.
- Displays the active turn-by-turn route and current estimated time of arrival (ETA).
- Displays immediate reroute alerts when a new road closure or traffic hazard alters their active path.
- Omits unassigned incidents and unrelated fleet vehicles to reduce visual clutter.

---

## 3. Data Sources & Research Boundaries

### 3.1 Public Video Cameras
- **Status**: **Not a required or planned feature.**
- **Rationale**: Public traffic camera feeds vary dramatically in geographic availability, require complex municipal permissions, have unreliable uptimes, and introduce substantial privacy and bandwidth concerns. They are excluded from this project.

### 3.2 "God's Eye View"
- **Status**: **Strictly visual research / inspiration only.**
- **Rationale**: Concepts referencing high-altitude or omniscient "God's Eye View" monitoring may be referenced in academic literature reviews or UI design inspiration, but they are **never** to be implemented or relied upon as a safety-critical data source for navigation or routing algorithms.

---

## 4. Academic Disclaimer

> [!CAUTION]
> **Academic Prototype Notice**: This software application and its associated routing algorithms, simulation engines, and user interfaces are strictly designed and developed as an **academic prototype and research project**.
> 
> **It is NOT certified, tested, or intended for use in real-world emergency dispatch, live 911 response, active vehicular navigation, or life-critical scenarios.** Real emergency operations require certified, hardened telecommunications infrastructure, hardware-backed GPS tracking, and regulatory compliance.
