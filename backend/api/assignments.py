"""Dispatch assignment endpoints.

Module Owner: Member 4 (FastAPI, database, REST API integration)
Collaborators: Member 2 (Routing) & Member 3 (Optimization)
"""

from datetime import datetime
from typing import Dict, List, Optional
import uuid
from fastapi import APIRouter, HTTPException, status
from ..models.schemas import Assignment, AssignmentCreate, AssignmentStatus

router = APIRouter(prefix="/assignments", tags=["Assignments"])

_ASSIGNMENTS_DB: Dict[str, Assignment] = {}


@router.post("", response_model=Assignment, status_code=status.HTTP_201_CREATED)
def create_assignment(payload: AssignmentCreate):
    """Dispatch an emergency vehicle to an incident."""
    assignment_id = f"asg-{uuid.uuid4().hex[:8]}"
    route_id = f"rte-{uuid.uuid4().hex[:8]}"

    # TODO (Member 4 & Member 2): Call RoutingEngine to compute route and real ETA
    assignment = Assignment(
        assignment_id=assignment_id,
        emergency_id=payload.emergency_id,
        vehicle_id=payload.vehicle_id,
        status=AssignmentStatus.DISPATCHED,
        route_id=route_id,
        dispatched_at=datetime.utcnow(),
        eta_minutes=6.5,
        dispatcher_notes=payload.dispatcher_notes,
    )
    _ASSIGNMENTS_DB[assignment_id] = assignment
    # TODO (Member 4): Persist to SQLite and update Vehicle status to DISPATCHED
    return assignment


@router.get("/{assignment_id}", response_model=Assignment)
def get_assignment(assignment_id: str):
    """Retrieve details of a specific dispatch assignment."""
    # TODO (Member 4): Fetch from SQLite database
    assignment = _ASSIGNMENTS_DB.get(assignment_id)
    if not assignment:
        # Fallback sample response for development/testing
        if assignment_id.startswith("asg-"):
            return Assignment(
                assignment_id=assignment_id,
                emergency_id="emg-9481b7a2",
                vehicle_id="veh-101",
                status=AssignmentStatus.EN_ROUTE,
                route_id="rte-551a82f3",
                dispatched_at=datetime.utcnow(),
                eta_minutes=5.2,
                dispatcher_notes="Assigned fire engine; proceeding with sirens.",
            )
        raise HTTPException(status_code=404, detail="Assignment not found")
    return assignment


@router.get("", response_model=List[Assignment])
def list_assignments():
    """List all historical and active dispatch assignments."""
    return list(_ASSIGNMENTS_DB.values())
