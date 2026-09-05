"""Emergency management endpoints.

Module Owner: Member 4 (FastAPI, database, REST API integration)
"""

from datetime import datetime
from typing import List
import uuid
from fastapi import APIRouter, Depends, HTTPException, status
from ..models.schemas import Emergency, EmergencyCreate, EmergencyStatus

router = APIRouter(prefix="/emergencies", tags=["Emergencies"])

# In-memory storage placeholder for development before DB wiring
_EMERGENCIES_DB: List[Emergency] = []


@router.post("", response_model=Emergency, status_code=status.HTTP_201_CREATED)
def create_emergency(payload: EmergencyCreate):
    """Report a new emergency incident."""
    emergency = Emergency(
        id=f"emg-{uuid.uuid4().hex[:8]}",
        emergency_type=payload.emergency_type,
        priority=payload.priority,
        description=payload.description,
        location=payload.location,
        reporter_contact=payload.reporter_contact,
        status=EmergencyStatus.PENDING_DISPATCH,
        created_at=datetime.utcnow(),
        updated_at=datetime.utcnow(),
    )
    _EMERGENCIES_DB.append(emergency)
    # TODO (Member 4): Persist to SQLite DB using SQLAlchemy session
    # TODO (Member 3 & Member 4): Trigger FleetOptimizer to auto-suggest vehicle
    return emergency


@router.get("", response_model=List[Emergency])
def list_emergencies():
    """Retrieve all reported emergency incidents."""
    # TODO (Member 4): Query from SQLite database
    return _EMERGENCIES_DB
