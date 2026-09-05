from fastapi import APIRouter
from .emergencies import router as emergencies_router
from .vehicles import router as vehicles_router
from .assignments import router as assignments_router
from .routes import router as routes_router
from .simulation import router as simulation_router

api_router = APIRouter(prefix="/api/v1")
api_router.include_router(emergencies_router)
api_router.include_router(vehicles_router)
api_router.include_router(assignments_router)
api_router.include_router(routes_router)
api_router.include_router(simulation_router)

__all__ = ["api_router"]
