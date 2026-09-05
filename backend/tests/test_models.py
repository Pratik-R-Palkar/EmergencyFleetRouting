"""Unit tests for Pydantic data schemas."""

import unittest
from datetime import datetime
from backend.models.schemas import (
    Coordinates,
    EmergencyCreate,
    EmergencyType,
    Vehicle,
    VehicleLocation,
    VehicleType,
    VehicleStatus,
    Assignment,
    AssignmentStatus,
    TrafficCondition,
    TrafficLevel,
    RoadClosure,
    Route,
    RouteWaypoint,
)


class TestSchemas(unittest.TestCase):

    def test_emergency_schema(self):
        emergency = EmergencyCreate(
            emergency_type=EmergencyType.FIRE,
            priority=1,
            description="Structure fire",
            location=Coordinates(latitude=37.7749, longitude=-122.4194),
            reporter_contact="+1-555-0100",
        )
        self.assertEqual(emergency.emergency_type, EmergencyType.FIRE)
        self.assertEqual(emergency.priority, 1)
        self.assertAlmostEqual(emergency.location.latitude, 37.7749)

    def test_vehicle_schema(self):
        vehicle = Vehicle(
            id="veh-test-1",
            call_sign="Rescue-9",
            vehicle_type=VehicleType.RESCUE_SQUAD,
            status=VehicleStatus.AVAILABLE,
            capacity=3,
            fuel_level_percent=90,
            equipment=["jaws_of_life", "winch"],
            current_location=VehicleLocation(
                latitude=37.7800,
                longitude=-122.4200,
                bearing=90.0,
                speed_kmh=0.0,
            ),
        )
        self.assertEqual(vehicle.call_sign, "Rescue-9")
        self.assertEqual(vehicle.status, VehicleStatus.AVAILABLE)

    def test_assignment_schema(self):
        asg = Assignment(
            assignment_id="asg-test-1",
            emergency_id="emg-test-1",
            vehicle_id="veh-test-1",
            status=AssignmentStatus.DISPATCHED,
            dispatched_at=datetime.utcnow(),
            eta_minutes=4.5,
        )
        self.assertEqual(asg.status, AssignmentStatus.DISPATCHED)
        self.assertEqual(asg.eta_minutes, 4.5)

    def test_route_schema(self):
        route = Route(
            route_id="rte-test-1",
            origin=Coordinates(latitude=37.78, longitude=-122.41),
            destination=Coordinates(latitude=37.77, longitude=-122.42),
            algorithm_used="DIJKSTRA",
            total_distance_km=2.5,
            estimated_time_minutes=4.0,
            traffic_condition=TrafficCondition(
                level=TrafficLevel.LOW,
                congestion_factor=1.0,
                last_updated=datetime.utcnow(),
            ),
            road_closures=[],
            waypoints=[
                RouteWaypoint(latitude=37.78, longitude=-122.41, step=1),
                RouteWaypoint(latitude=37.77, longitude=-122.42, step=2),
            ],
        )
        self.assertEqual(len(route.waypoints), 2)
        self.assertEqual(route.total_distance_km, 2.5)


if __name__ == "__main__":
    unittest.main()
