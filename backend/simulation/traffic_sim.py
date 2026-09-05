"""Traffic Condition and Road Closure Simulator.

Simulates dynamic urban traffic events, congestion spikes, and sudden road blocks.
"""

from datetime import datetime
import random
from typing import List
from ..models.schemas import TrafficCondition, TrafficLevel, RoadClosure


class TrafficSimulator:
    """Generates synthetic traffic fluctuations and road closures for testing routing logic."""

    def __init__(self):
        self._closures: List[RoadClosure] = [
            RoadClosure(
                closure_id="cls-101",
                street_name="Market St & 5th Ave",
                reason="Construction & Water main replacement",
                is_active=True,
                avoided=True,
            )
        ]

    def get_current_traffic(self) -> TrafficCondition:
        """Simulates periodic traffic condition queries."""
        levels = [TrafficLevel.LOW, TrafficLevel.MODERATE, TrafficLevel.HEAVY]
        chosen = random.choice(levels)
        multipliers = {
            TrafficLevel.LOW: 1.05,
            TrafficLevel.MODERATE: 1.35,
            TrafficLevel.HEAVY: 1.85,
        }
        return TrafficCondition(
            level=chosen,
            congestion_factor=multipliers[chosen],
            last_updated=datetime.utcnow(),
        )

    def get_active_closures(self) -> List[RoadClosure]:
        """Returns list of simulated active road closures."""
        return [c for c in self._closures if c.is_active]

    def add_closure(self, street_name: str, reason: str) -> RoadClosure:
        """Injects a new simulated road closure."""
        closure = RoadClosure(
            closure_id=f"cls-{random.randint(100, 999)}",
            street_name=street_name,
            reason=reason,
            is_active=True,
            avoided=True,
        )
        self._closures.append(closure)
        return closure
