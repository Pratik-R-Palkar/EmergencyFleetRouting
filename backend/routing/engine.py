"""Dynamic Routing Engine for Emergency Fleet Vehicles using NetworkX.

Module Owner: Member 2 (Routing engine)
Algorithm Support: Dijkstra and A* with dynamic congestion and road closure avoidance.
"""

from enum import Enum
from typing import Dict, List, Optional, Tuple
import math

try:
    import networkx as nx
except ImportError:
    nx = None  # Fallback if networkx is not yet installed in current Python environment

from ..models.schemas import Coordinates, Route, RouteWaypoint, TrafficCondition, TrafficLevel, RoadClosure


class RoutingAlgorithm(str, Enum):
    DIJKSTRA = "DIJKSTRA"
    A_STAR = "A_STAR"


class RoutingEngine:
    """Calculates optimal paths on road networks considering dynamic traffic and closures.
    
    Member 2 will implement:
    1. Road network graph ingestion (nodes = intersections, edges = road segments).
    2. Dynamic edge weight calculation: weight = base_length * traffic_multiplier.
    3. Blocked edge exclusion for active road closures.
    4. Dijkstra and A* path computation returning coordinates and ETAs.
    """

    def __init__(self):
        if nx is not None:
            self.graph = nx.DiGraph()
        else:
            self.graph = None
        self._load_default_network()

    def _load_default_network(self):
        """Initializes default graph nodes and edges for development/testing."""
        if self.graph is None:
            return

        # TODO (Member 2): Load comprehensive road graph (e.g. from OpenStreetMap or GeoJSON)
        # Sample test network representation
        sample_nodes = {
            "node_1": (37.7812, -122.4111),
            "node_2": (37.7801, -122.4135),
            "node_3": (37.7770, -122.4172),
            "node_4": (37.774929, -122.419416),
        }
        for node_id, coords in sample_nodes.items():
            self.graph.add_node(node_id, lat=coords[0], lon=coords[1])

        # Add directed edges with distance in meters and base speed limit km/h
        self.graph.add_edge("node_1", "node_2", distance=350, speed_limit=45, weight=350)
        self.graph.add_edge("node_2", "node_3", distance=500, speed_limit=40, weight=500)
        self.graph.add_edge("node_3", "node_4", distance=400, speed_limit=50, weight=400)
        self.graph.add_edge("node_1", "node_4", distance=1500, speed_limit=60, weight=1500)

    def calculate_route(
        self,
        origin: Coordinates,
        destination: Coordinates,
        algorithm: RoutingAlgorithm = RoutingAlgorithm.DIJKSTRA,
        traffic_multiplier: float = 1.0,
        active_closures: Optional[List[RoadClosure]] = None,
    ) -> Route:
        """Computes the optimal route between origin and destination coordinates.
        
        TODO (Member 2):
        1. Find nearest graph nodes to origin and destination GPS points.
        2. Apply dynamic traffic weights: edge_weight = (distance / speed) * traffic_multiplier.
        3. Temporarily remove or apply infinite weight to edges matching active_closures.
        4. Execute nx.dijkstra_path or nx.astar_path with heuristic.
        5. Reconstruct waypoints, calculate cumulative distance (km) and estimated ETA (min).
        """
        # Scaffold placeholder calculation: straight-line distance approximation
        dx = destination.latitude - origin.latitude
        dy = destination.longitude - origin.longitude
        straight_dist_km = math.sqrt(dx * dx + dy * dy) * 111.0  # Approx 111 km per deg
        total_distance_km = round(max(straight_dist_km * 1.3, 0.5), 2)
        
        # Average emergency vehicle speed assumed at 40 km/h with traffic factor
        effective_speed = max(40.0 / max(traffic_multiplier, 1.0), 10.0)
        estimated_time_minutes = round((total_distance_km / effective_speed) * 60, 1)

        waypoints = [
            RouteWaypoint(
                latitude=origin.latitude,
                longitude=origin.longitude,
                step=1,
                instruction="Depart origin toward incident location",
            ),
            RouteWaypoint(
                latitude=(origin.latitude + destination.latitude) / 2,
                longitude=(origin.longitude + destination.longitude) / 2,
                step=2,
                instruction="Proceed along primary corridor",
            ),
            RouteWaypoint(
                latitude=destination.latitude,
                longitude=destination.longitude,
                step=3,
                instruction="Arrive at incident destination",
            ),
        ]

        from datetime import datetime
        return Route(
            route_id=f"rte-{abs(hash((origin.latitude, destination.latitude))) % 1000000:06d}",
            origin=origin,
            destination=destination,
            algorithm_used=algorithm.value,
            total_distance_km=total_distance_km,
            estimated_time_minutes=estimated_time_minutes,
            traffic_condition=TrafficCondition(
                level=TrafficLevel.MODERATE if traffic_multiplier > 1.2 else TrafficLevel.LOW,
                congestion_factor=traffic_multiplier,
                last_updated=datetime.utcnow(),
            ),
            road_closures=active_closures or [],
            waypoints=waypoints,
        )

    def update_road_closure(self, street_name: str, is_closed: bool):
        """Dynamic hook to update edge availability when a road closure is reported."""
        # TODO (Member 2): Update graph edge attributes when street is closed/re-opened
        pass
