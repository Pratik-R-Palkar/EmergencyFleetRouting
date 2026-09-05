"""Fleet Selection and Optimization Engine for Emergency Vehicles.

Module Owner: Member 3 (Fleet selection and optimization)
Algorithms: Multi-criteria vehicle scoring, ETA ranking, resource matching.
"""

from typing import Dict, List, Optional, Tuple
from ..models.schemas import Emergency, Vehicle, VehicleStatus, VehicleType, EmergencyType


class OptimizationCriteria:
    """Configurable weights for multi-criteria fleet ranking."""
    WEIGHT_DISTANCE: float = 0.50
    WEIGHT_TYPE_SUITABILITY: float = 0.25
    WEIGHT_FUEL_CAPACITY: float = 0.15
    WEIGHT_EQUIPMENT_MATCH: float = 0.10


class FleetOptimizer:
    """Selects and assigns the optimal emergency vehicle for an active incident.

    Member 3 will implement:
    1. Vehicle filtering by availability and vehicle type suitability.
    2. Multi-factor scoring function: Score = w1*TravelTime + w2*Equipment + w3*Fuel.
    3. Emergency priority pre-emption (reassigning en-route low-priority vehicles to life-threatening calls).
    4. Fleet re-balancing heuristic across geographic coverage sectors.
    """

    def __init__(self, criteria: Optional[OptimizationCriteria] = None):
        self.criteria = criteria or OptimizationCriteria()

    def find_best_vehicle(
        self,
        emergency: Emergency,
        available_vehicles: List[Vehicle],
        estimated_etas: Optional[Dict[str, float]] = None,
    ) -> Optional[Tuple[Vehicle, float]]:
        """Selects the best vehicle for the specified emergency.

        TODO (Member 3):
        1. Filter available_vehicles by status == VehicleStatus.AVAILABLE.
        2. Match vehicle type to emergency type:
           - EmergencyType.FIRE -> VehicleType.FIRE_TRUCK / RESCUE_SQUAD
           - EmergencyType.MEDICAL -> VehicleType.AMBULANCE
           - EmergencyType.TRAFFIC_ACCIDENT -> AMBULANCE / POLICE_CRUISER / RESCUE_SQUAD
        3. Rank candidates using composite utility score:
           lower score = better candidate (or normalized higher utility).
        4. Return (selected_vehicle, best_score).
        """
        if not available_vehicles:
            return None

        # Scaffold placeholder: Pick first available matching vehicle or first available
        filtered = [v for v in available_vehicles if v.status == VehicleStatus.AVAILABLE]
        if not filtered:
            return None

        # Simple type matching fallback
        preferred_type = {
            EmergencyType.FIRE: VehicleType.FIRE_TRUCK,
            EmergencyType.MEDICAL: VehicleType.AMBULANCE,
            EmergencyType.TRAFFIC_ACCIDENT: VehicleType.RESCUE_SQUAD,
        }.get(emergency.emergency_type)

        for veh in filtered:
            if preferred_type and veh.vehicle_type == preferred_type:
                return veh, 0.95

        # Fallback to first available vehicle
        return filtered[0], 0.70

    def evaluate_preemption(
        self,
        high_priority_emergency: Emergency,
        active_assignments: List[Dict],
    ) -> Optional[str]:
        """Determines if a lower-priority dispatch should be pre-empted.

        TODO (Member 3):
        Evaluate if emergency.priority == 1 (e.g. cardiac arrest, active fire) warrants
        diverting a vehicle currently assigned to a low-priority (priority 4 or 5) call.
        """
        # Placeholder logic
        return None
