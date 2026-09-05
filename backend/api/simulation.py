"""Traffic and road closure simulation endpoints.

Enables developers and testing harnesses to inject simulated traffic spikes and closures.
"""

from typing import List
from fastapi import APIRouter
from pydantic import BaseModel
from ..models.schemas import TrafficCondition, RoadClosure
from ..simulation.traffic_sim import TrafficSimulator

router = APIRouter(prefix="/simulation", tags=["Simulation"])
simulator = TrafficSimulator()


class ClosureCreateRequest(BaseModel):
    street_name: str
    reason: str


@router.get("/traffic", response_model=TrafficCondition)
def get_simulated_traffic():
    """Returns current simulated city-wide traffic condition."""
    return simulator.get_current_traffic()


@router.get("/closures", response_model=List[RoadClosure])
def get_simulated_closures():
    """Returns all simulated active road closures."""
    return simulator.get_active_closures()


@router.post("/closures", response_model=RoadClosure)
def inject_road_closure(payload: ClosureCreateRequest):
    """Simulate a new road closure event for path recalculation testing."""
    return simulator.add_closure(payload.street_name, payload.reason)
