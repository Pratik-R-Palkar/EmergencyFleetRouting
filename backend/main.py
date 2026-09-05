"""Main FastAPI entry point for Dynamic Emergency Fleet Routing.

Module Owner: Member 4 (FastAPI, database, REST API integration)
"""

from contextlib import asynccontextmanager
from datetime import datetime
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from backend.api import api_router
from backend.database.session import Base, engine


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application startup and shutdown hooks."""
    # Initialize SQLite database tables on startup
    Base.metadata.create_all(bind=engine)
    yield
    # Clean shutdown tasks (if any)


app = FastAPI(
    title="Dynamic Emergency Fleet Routing API",
    description="Backend dispatch, routing, and fleet optimization services for emergency services.",
    version="1.0.0",
    lifespan=lifespan,
)

# Enable CORS for local development and Android emulator connections
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Mount REST API endpoints
app.include_router(api_router)


@app.get("/health", tags=["Health"])
def health_check():
    """Health check endpoint for monitoring, CI validation, and client probes."""
    return {
        "status": "healthy",
        "service": "emergency-fleet-routing-backend",
        "timestamp": datetime.utcnow().isoformat() + "Z",
        "version": "1.0.0",
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run("backend.main:app", host="0.0.0.0", port=8000, reload=True)
