"""Pydantic data models for Dynamic Emergency Fleet Routing."""

from datetime import datetime
from enum import Enum
from typing import List, Optional
from pydantic import BaseModel, Field, ConfigDict


class EmergencyType(str, Enum):
    FIRE = "FIRE"
    MEDICAL = "MEDICAL"
    TRAFFIC_ACCIDENT = "TRAFFIC_ACCIDENT"
    HAZMAT = "HAZMAT"
    RESCUE = "RESCUE"
    OTHER = "OTHER"


class EmergencyStatus(str, Enum):
    REPORTED = "REPORTED"
    PENDING_DISPATCH = "PENDING_DISPATCH"
    ASSIGNED = "ASSIGNED"
    IN_PROGRESS = "IN_PROGRESS"
    RESOLVED = "RESOLVED"
    CANCELLED = "CANCELLED"


class VehicleType(str, Enum):
    AMBULANCE = "AMBULANCE"
    FIRE_TRUCK = "FIRE_TRUCK"
    RESCUE_SQUAD = "RESCUE_SQUAD"
    POLICE_CRUISER = "POLICE_CRUISER"


class VehicleStatus(str, Enum):
    AVAILABLE = "AVAILABLE"
    DISPATCHED = "DISPATCHED"
    EN_ROUTE = "EN_ROUTE"
    ON_SCENE = "ON_SCENE"
    RETURNING = "RETURNING"
    MAINTENANCE = "MAINTENANCE"


class AssignmentStatus(str, Enum):
    PENDING = "PENDING"
    DISPATCHED = "DISPATCHED"
    EN_ROUTE = "EN_ROUTE"
    ARRIVED = "ARRIVED"
    COMPLETED = "COMPLETED"
    CANCELLED = "CANCELLED"


class TrafficLevel(str, Enum):
    LOW = "LOW"
    MODERATE = "MODERATE"
    HEAVY = "HEAVY"
    SEVERE = "SEVERE"


class Coordinates(BaseModel):
    latitude: float = Field(..., ge=-90.0, le=90.0, description="Latitude in decimal degrees")
    longitude: float = Field(..., ge=-180.0, le=180.0, description="Longitude in decimal degrees")
    address: Optional[str] = Field(None, description="Human-readable address or landmark")


class EmergencyBase(BaseModel):
    emergency_type: EmergencyType
    priority: int = Field(..., ge=1, le=5, description="1 is highest priority, 5 is lowest")
    description: str = Field(..., min_length=1)
    location: Coordinates
    reporter_contact: Optional[str] = None


class EmergencyCreate(EmergencyBase):
    pass


class Emergency(EmergencyBase):
    id: str
    status: EmergencyStatus = EmergencyStatus.PENDING_DISPATCH
    created_at: datetime
    updated_at: datetime

    model_config = ConfigDict(from_attributes=True)


class VehicleLocation(Coordinates):
    bearing: Optional[float] = Field(None, ge=0.0, lt=360.0)
    speed_kmh: Optional[float] = Field(0.0, ge=0.0)


class VehicleBase(BaseModel):
    call_sign: str
    vehicle_type: VehicleType
    capacity: int = Field(default=2, ge=1)
    fuel_level_percent: int = Field(default=100, ge=0, le=100)
    equipment: List[str] = Field(default_factory=list)


class VehicleCreate(VehicleBase):
    initial_location: Coordinates


class Vehicle(VehicleBase):
    id: str
    status: VehicleStatus = VehicleStatus.AVAILABLE
    current_location: VehicleLocation

    model_config = ConfigDict(from_attributes=True)


class AssignmentCreate(BaseModel):
    emergency_id: str
    vehicle_id: str
    dispatcher_notes: Optional[str] = None


class Assignment(BaseModel):
    assignment_id: str
    emergency_id: str
    vehicle_id: str
    status: AssignmentStatus = AssignmentStatus.DISPATCHED
    route_id: Optional[str] = None
    dispatched_at: datetime
    eta_minutes: Optional[float] = None
    completed_at: Optional[datetime] = None
    dispatcher_notes: Optional[str] = None

    model_config = ConfigDict(from_attributes=True)


class RouteWaypoint(Coordinates):
    step: int
    instruction: Optional[str] = None


class TrafficCondition(BaseModel):
    level: TrafficLevel = TrafficLevel.LOW
    congestion_factor: float = Field(default=1.0, ge=1.0, description="Multiplier for travel time")
    last_updated: datetime


class RoadClosure(BaseModel):
    closure_id: str
    street_name: str
    reason: str
    is_active: bool = True
    avoided: bool = True
    start_time: Optional[datetime] = None
    end_time: Optional[datetime] = None


class Route(BaseModel):
    route_id: str
    origin: Coordinates
    destination: Coordinates
    algorithm_used: str = "DIJKSTRA_DYNAMIC_WEIGHTED"
    total_distance_km: float
    estimated_time_minutes: float
    traffic_condition: TrafficCondition
    road_closures: List[RoadClosure] = Field(default_factory=list)
    waypoints: List[RouteWaypoint] = Field(default_factory=list)

    model_config = ConfigDict(from_attributes=True)
