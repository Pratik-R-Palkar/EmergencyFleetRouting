"""Route calculation and waypoint query endpoints.

Module Owner: Member 4 (FastAPI, database, REST API integration)
Collaborator: Member 2 (Routing engine)
"""

from datetime import datetime
from fastapi import APIRouter, HTTPException
from ..models.schemas import (
    Route,
    Coordinates,
    RouteWaypoint,
    TrafficCondition,
    TrafficLevel,
    RoadClosure,
)
from ..routing.engine import RoutingEngine

router = APIRouter(prefix="/routes", tags=["Routes"])
routing_engine = RoutingEngine()


@router.get("/{route_id}", response_model=Route)
def get_route(route_id: str):
    """Retrieve detailed waypoint navigation, live traffic, and road closures for a route."""
    # TODO (Member 2 & Member 4): Fetch computed route or dynamically calculate via RoutingEngine
    sample_origin = Coordinates(latitude=37.7812, longitude=-122.4111, address="Fire Station 1")
    sample_destination = Coordinates(latitude=37.774929, longitude=-122.419416, address="Market St & 10th St")

    return Route(
        route_id=route_id,
        origin=sample_origin,
        destination=sample_destination,
        algorithm_used="DIJKSTRA_DYNAMIC_WEIGHTED",
        total_distance_km=3.42,
        estimated_time_minutes=5.2,
        traffic_condition=TrafficCondition(
            level=TrafficLevel.MODERATE,
            congestion_factor=1.35,
            last_updated=datetime.utcnow(),
        ),
        road_closures=[
            RoadClosure(
                closure_id="cls-091",
                street_name="Mission St (between 6th & 7th)",
                reason="Water main repair",
                is_active=True,
                avoided=True,
            )
        ],
        waypoints=[
            RouteWaypoint(latitude=37.7812, longitude=-122.4111, step=1, instruction="Head south on 3rd St"),
            RouteWaypoint(latitude=37.7801, longitude=-122.4135, step=2, instruction="Turn right onto Folsom St"),
            RouteWaypoint(latitude=37.7770, longitude=-122.4172, step=3, instruction="Bypass Mission St via 8th St"),
            RouteWaypoint(latitude=37.774929, longitude=-122.419416, step=4, instruction="Arrive at incident site"),
        ],
    )
