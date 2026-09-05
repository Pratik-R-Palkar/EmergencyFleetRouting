"""Vehicle fleet endpoints.

Module Owner: Member 4 (FastAPI, database, REST API integration)
"""

from typing import List, Optional
from fastapi import APIRouter, Query
from ..models.schemas import Vehicle, VehicleCreate, VehicleStatus, VehicleType, VehicleLocation

router = APIRouter(prefix="/vehicles", tags=["Vehicles"])

# Seed data for initial testing and frontend development
_VEHICLES_DB: List[Vehicle] = [
    Vehicle(
        id="veh-101",
        call_sign="Engine-1",
        vehicle_type=VehicleType.FIRE_TRUCK,
        status=VehicleStatus.AVAILABLE,
        capacity=4,
        fuel_level_percent=95,
        equipment=["hose", "ladder", "extinguisher", "first_aid"],
        current_location=VehicleLocation(
            latitude=37.7812,
            longitude=-122.4111,
            bearing=180.0,
            speed_kmh=0.0,
            address="Fire Station 1, SF",
        ),
    ),
    Vehicle(
        id="veh-204",
        call_sign="Medic-4",
        vehicle_type=VehicleType.AMBULANCE,
        status=VehicleStatus.AVAILABLE,
        capacity=2,
        fuel_level_percent=88,
        equipment=["defibrillator", "stretcher", "oxygen_tank"],
        current_location=VehicleLocation(
            latitude=37.7654,
            longitude=-122.4231,
            bearing=45.0,
            speed_kmh=0.0,
            address="General Hospital Depot",
        ),
    ),
    Vehicle(
        id="veh-302",
        call_sign="Patrol-3",
        vehicle_type=VehicleType.POLICE_CRUISER,
        status=VehicleStatus.AVAILABLE,
        capacity=2,
        fuel_level_percent=78,
        equipment=["siren", "traffic_cones", "tactical_kit"],
        current_location=VehicleLocation(
            latitude=37.7701,
            longitude=-122.4150,
            bearing=270.0,
            speed_kmh=20.0,
            address="Central Precinct",
        ),
    ),
]


@router.get("", response_model=List[Vehicle])
def get_vehicles(status: Optional[VehicleStatus] = Query(None, description="Filter by vehicle status")):
    """Retrieve all vehicles in the emergency fleet, optionally filtered by status."""
    # TODO (Member 4): Query from SQLite database via SQLAlchemy
    if status:
        return [v for v in _VEHICLES_DB if v.status == status]
    return _VEHICLES_DB
