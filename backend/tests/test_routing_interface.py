"""Unit tests for RoutingEngine and FleetOptimizer placeholder interfaces."""

import unittest
from backend.models.schemas import Coordinates, Emergency, EmergencyType, EmergencyStatus
from backend.routing.engine import RoutingEngine, RoutingAlgorithm
from backend.optimization.dispatcher import FleetOptimizer
from datetime import datetime


class TestRoutingAndOptimizationInterfaces(unittest.TestCase):

    def setUp(self):
        self.engine = RoutingEngine()
        self.optimizer = FleetOptimizer()

    def test_routing_engine_placeholder_returns_route(self):
        origin = Coordinates(latitude=37.7812, longitude=-122.4111)
        destination = Coordinates(latitude=37.774929, longitude=-122.419416)
        route = self.engine.calculate_route(origin, destination, algorithm=RoutingAlgorithm.DIJKSTRA)

        self.assertIsNotNone(route.route_id)
        self.assertGreater(route.total_distance_km, 0)
        self.assertGreater(route.estimated_time_minutes, 0)
        self.assertGreaterEqual(len(route.waypoints), 2)

    def test_fleet_optimizer_placeholder_selection(self):
        emg = Emergency(
            id="emg-test",
            emergency_type=EmergencyType.FIRE,
            priority=1,
            description="Fire call",
            location=Coordinates(latitude=37.77, longitude=-122.41),
            status=EmergencyStatus.PENDING_DISPATCH,
            created_at=datetime.utcnow(),
            updated_at=datetime.utcnow(),
        )
        result = self.optimizer.find_best_vehicle(emg, [])
        self.assertIsNone(result)


if __name__ == "__main__":
    unittest.main()
