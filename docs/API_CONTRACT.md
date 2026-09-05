# API Contract Specification

This document defines the REST JSON API contract between the **FastAPI Backend** and the **Android Mobile Application**. All endpoints communicate using standard JSON payloads over HTTP.

**Base URL (Local Development)**: `http://10.0.2.2:8000/api/v1` (for Android Emulator) or `http://localhost:8000/api/v1` (direct/web).

---

## 1. POST /emergencies
Report a new emergency incident and initiate the routing/dispatch workflow.

### Request
`POST /api/v1/emergencies`
```json
{
  "emergency_type": "FIRE",
  "priority": 1,
  "description": "Multi-vehicle collision with fire near Highway 101 interchange",
  "location": {
    "latitude": 37.774929,
    "longitude": -122.419416,
    "address": "Market St & 10th St, San Francisco, CA"
  },
  "reporter_contact": "+1-555-0199"
}
```

### Response
`201 Created`
```json
{
  "id": "emg-9481b7a2",
  "emergency_type": "FIRE",
  "priority": 1,
  "status": "PENDING_DISPATCH",
  "description": "Multi-vehicle collision with fire near Highway 101 interchange",
  "location": {
    "latitude": 37.774929,
    "longitude": -122.419416,
    "address": "Market St & 10th St, San Francisco, CA"
  },
  "reporter_contact": "+1-555-0199",
  "created_at": "2026-09-05T18:30:00Z",
  "updated_at": "2026-09-05T18:30:00Z"
}
```

---

## 2. GET /vehicles
Retrieve the list of all fleet emergency vehicles, their real-time status, and GPS coordinates.

### Request
`GET /api/v1/vehicles?status=AVAILABLE`

### Response
`200 OK`
```json
[
  {
    "id": "veh-101",
    "call_sign": "Engine-1",
    "vehicle_type": "FIRE_TRUCK",
    "status": "AVAILABLE",
    "capacity": 4,
    "current_location": {
      "latitude": 37.7812,
      "longitude": -122.4111,
      "bearing": 180.0,
      "speed_kmh": 0.0
    },
    "fuel_level_percent": 95,
    "equipment": ["hose", "ladder", "extinguisher", "first_aid"]
  },
  {
    "id": "veh-204",
    "call_sign": "Medic-4",
    "vehicle_type": "AMBULANCE",
    "status": "AVAILABLE",
    "capacity": 2,
    "current_location": {
      "latitude": 37.7654,
      "longitude": -122.4231,
      "bearing": 45.0,
      "speed_kmh": 0.0
    },
    "fuel_level_percent": 88,
    "equipment": ["defibrillator", "stretcher", "oxygen_tank"]
  }
]
```

---

## 3. POST /assignments
Dispatch and assign an emergency vehicle to an incident.

### Request
`POST /api/v1/assignments`
```json
{
  "emergency_id": "emg-9481b7a2",
  "vehicle_id": "veh-101",
  "dispatcher_notes": "Assigned highest priority fire engine; expect traffic near Mission St."
}
```

### Response
`201 Created`
```json
{
  "assignment_id": "asg-7c103e91",
  "emergency_id": "emg-9481b7a2",
  "vehicle_id": "veh-101",
  "status": "DISPATCHED",
  "route_id": "rte-551a82f3",
  "dispatched_at": "2026-09-05T18:30:45Z",
  "eta_minutes": 6.8,
  "dispatcher_notes": "Assigned highest priority fire engine; expect traffic near Mission St."
}
```

---

## 4. GET /assignments/{assignment_id}
Retrieve full details of an active or completed assignment.

### Request
`GET /api/v1/assignments/asg-7c103e91`

### Response
`200 OK`
```json
{
  "assignment_id": "asg-7c103e91",
  "emergency": {
    "id": "emg-9481b7a2",
    "emergency_type": "FIRE",
    "priority": 1,
    "status": "ASSIGNED",
    "location": {
      "latitude": 37.774929,
      "longitude": -122.419416,
      "address": "Market St & 10th St, San Francisco, CA"
    }
  },
  "vehicle": {
    "id": "veh-101",
    "call_sign": "Engine-1",
    "vehicle_type": "FIRE_TRUCK",
    "status": "EN_ROUTE",
    "current_location": {
      "latitude": 37.7795,
      "longitude": -122.4140,
      "speed_kmh": 42.5
    }
  },
  "route_id": "rte-551a82f3",
  "status": "EN_ROUTE",
  "dispatched_at": "2026-09-05T18:30:45Z",
  "eta_minutes": 5.2,
  "completed_at": null
}
```

---

## 5. GET /routes/{route_id}
Retrieve calculated optimal navigation route, waypoints, road closures, and live traffic conditions.

### Request
`GET /api/v1/routes/rte-551a82f3`

### Response
`200 OK`
```json
{
  "route_id": "rte-551a82f3",
  "origin": {
    "latitude": 37.7812,
    "longitude": -122.4111
  },
  "destination": {
    "latitude": 37.774929,
    "longitude": -122.419416
  },
  "algorithm_used": "DIJKSTRA_DYNAMIC_WEIGHTED",
  "total_distance_km": 3.42,
  "estimated_time_minutes": 5.2,
  "traffic_condition": {
    "level": "MODERATE",
    "congestion_factor": 1.35,
    "last_updated": "2026-09-05T18:31:00Z"
  },
  "road_closures": [
    {
      "closure_id": "cls-091",
      "street_name": "Mission St (between 6th & 7th)",
      "reason": "Water main repair",
      "is_active": true,
      "avoided": true
    }
  ],
  "waypoints": [
    {"latitude": 37.7812, "longitude": -122.4111, "step": 1},
    {"latitude": 37.7801, "longitude": -122.4135, "step": 2},
    {"latitude": 37.7770, "longitude": -122.4172, "step": 3},
    {"latitude": 37.774929, "longitude": -122.419416, "step": 4}
  ]
}
```
