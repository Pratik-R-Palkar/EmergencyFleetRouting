"""SQLAlchemy ORM models for Emergency Fleet Routing (Development Database).

Module Owner: Member 4 (FastAPI, database, REST API integration)
"""

from datetime import datetime
from sqlalchemy import Column, String, Integer, Float, Boolean, DateTime, ForeignKey, Text
from sqlalchemy.orm import relationship
from .session import Base


class EmergencyRecord(Base):
    __tablename__ = "emergencies"

    id = Column(String(64), primary_key=True, index=True)
    emergency_type = Column(String(32), nullable=False, index=True)
    priority = Column(Integer, nullable=False, default=3)
    status = Column(String(32), nullable=False, default="PENDING_DISPATCH")
    description = Column(Text, nullable=False)
    latitude = Column(Float, nullable=False)
    longitude = Column(Float, nullable=False)
    address = Column(String(255), nullable=True)
    reporter_contact = Column(String(64), nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    # TODO (Member 4): Establish relationship with AssignmentRecord
    # assignments = relationship("AssignmentRecord", back_populates="emergency")


class VehicleRecord(Base):
    __tablename__ = "vehicles"

    id = Column(String(64), primary_key=True, index=True)
    call_sign = Column(String(64), nullable=False, unique=True)
    vehicle_type = Column(String(32), nullable=False, index=True)
    status = Column(String(32), nullable=False, default="AVAILABLE")
    capacity = Column(Integer, default=2)
    fuel_level_percent = Column(Integer, default=100)
    latitude = Column(Float, nullable=False)
    longitude = Column(Float, nullable=False)
    bearing = Column(Float, default=0.0)
    speed_kmh = Column(Float, default=0.0)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)

    # TODO (Member 4): Establish relationship with AssignmentRecord


class AssignmentRecord(Base):
    __tablename__ = "assignments"

    assignment_id = Column(String(64), primary_key=True, index=True)
    emergency_id = Column(String(64), ForeignKey("emergencies.id"), nullable=False)
    vehicle_id = Column(String(64), ForeignKey("vehicles.id"), nullable=False)
    status = Column(String(32), nullable=False, default="DISPATCHED")
    route_id = Column(String(64), nullable=True)
    dispatched_at = Column(DateTime, default=datetime.utcnow)
    eta_minutes = Column(Float, nullable=True)
    completed_at = Column(DateTime, nullable=True)
    dispatcher_notes = Column(Text, nullable=True)


class RoadClosureRecord(Base):
    __tablename__ = "road_closures"

    closure_id = Column(String(64), primary_key=True, index=True)
    street_name = Column(String(128), nullable=False)
    reason = Column(String(255), nullable=False)
    is_active = Column(Boolean, default=True)
    created_at = Column(DateTime, default=datetime.utcnow)
