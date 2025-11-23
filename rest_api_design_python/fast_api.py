"""
Design a set of psuedo code for APIs for an AI-driven dispute system that serves millions of  customers, 
integrates with existing billing systems, supports AI agents and human agents, and is production-grade

This is just for reference and not a production code.

The APIs should be able to:
- Create a new dispute
- Get a dispute by ID
- Update a dispute
- Get all disputes
- Get all disputes by customer ID
- Get all disputes by account ID

The APIs should be able to handle:
- Idempency keys
- Concurrent requests
- Error handling
- Logging
- Monitoring
- Security

"""

from fastapi import FastAPI, Header, HTTPException, status, Depends
from pydantic import BaseModel, Field
from typing import Optional, List
from uuid import uuid4
from datetime import datetime, timezone
from enum import Enum
from fastapi.requests import Request

app = FastAPI(title= "dispute service api demo", version= "1.0.0")

#########################################################
# request models
class CreateDisputeRequest(BaseModel):
    customer_id: str = Field(..., min_length=10, max_length=10)
    account_id: str = Field(..., min_length=10, max_length=10)
    invoid_id: str = Field(..., min_length=10, max_length=10)
    line_item_ids: List[str] = Field(..., min_length=1)
    reason_code: str = Field(..., min_length=10, max_length=50)
    channel: str = Field(..., min_length=10, max_length=100)

#########################################################
# Dispute status
class DisputeStatus(str, Enum):
    CREATED = "created"
    UNDER_REVIEW = "under_review"
    RESOLVED = "resolved"
    REJECTED = "rejected"
    PENDING = "pending"

#########################################################
# Dispute model

class Dispute(BaseModel):
    dispute_id: str = Field(..., min_length=10, max_length=10)
    customer_id: str = Field(..., min_length=10, max_length=10)
    account_id: str = Field(..., min_length=10, max_length=10)
    status: DisputeStatus = Field(..., min_length=10, max_length=10)
    ai_status: str = Field(..., min_length=10, max_length=10)
    created_at: datetime = Field(..., min_length=10, max_length=10)


#########################################################
# error response model
class ErrorResponse(BaseModel):
    code: str = Field(..., min_length=10, max_length=10)
    message: str = Field(..., min_length=10, max_length=100)
    details: str = Field(..., min_length=10, max_length=100)


class UpdateDisputeRequest(BaseModel):
    status: DisputeStatus = Field(..., min_length=10, max_length=10)
    ai_status: DisputeStatus = Field(..., min_length=10, max_length=10)
    reason_code: str = Field(..., min_length=10, max_length=50)
    channel: str = Field(..., min_length=10, max_length=100)
    line_item_ids: List[str] = Field(..., min_length=1)

######################################################### IN MEMORY DATA STORAGE FOR DISPUTES #########################################################
disputes = {}
idempency_cache = {} # key -> dispute_id

######################################################### HELPER FUNCTIONS #########################################################
def create_dispute_record(
    payload: CreateDisputeRequest,
    dispute_id: Optional[str] = None,
) -> Dispute:
    dispute = Dispute(
        dispute_id=dispute_id or str(uuid4()),
        customer_id=payload.customer_id,
        account_id=payload.account_id,
        status=DisputeStatus.CREATED,
        ai_status=DisputeStatus.PENDING,
        created_at=datetime.now(timezone.utc),
    )

async def enqueue_ai_workflow(dispute: Dispute):
    # In real system: publish to Kafka / SQS / PubSub
    # For now, just log
    print(f"[AI_WORKFLOW] Enqueued for {dispute.disputeId}")

######################################################### API ENDPOINTS #########################################################

@app.post(
    "/api/v1/disputes",
    response_model = Dispute,
    responses = {400: {"model": ErrorResponse}, 409: {"model": ErrorResponse}, 500: {"model": ErrorResponse}},
)
async def create_dispute(
    request: Request,
    payload: CreateDisputeRequest,
    idempency_key: Optional[str] = Header(..., alias="Idempency-Key", default=None, min_length=10, max_length=10, convert_underscores=True),
):
    if not idempency_key:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail= ErrorResponse(code="IDEMPOTENCY_KEY_REQUIRED", message="Idempency-Key is required", details="Idempency-Key is required").model_dump())
    
    existing_idempency_key = idempency_cache.get(idempency_key)
    if existing_idempency_key:
        return disputes[existing_idempency_key]
    
    dispute = create_dispute_record(payload)
    idempency_cache[idempency_key] = dispute.dispute_id
    enqueue_ai_workflow(dispute)

    return dispute


@app.get(
    "api/v1/disputes/{dispute_id}",
    response_model = Dispute,
    responses = {404: {"model": ErrorResponse}, 500: {"model": ErrorResponse}},
)
async def get_dispute(
    dispute_id: str,
):
    dispute = disputes.get(dispute_id)
    if not dispute:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail= ErrorResponse(code="DISPUTE_NOT_FOUND", message="Dispute not found", details="Dispute not found").model_dump())
    return dispute

@app.put(
    "api/v1/disputes/{dispute_id}",
    response_model = Dispute,
    responses = {404: {"model": ErrorResponse}, 500: {"model": ErrorResponse}},
)
async def update_dispute(
    dispute_id: str,
    payload: UpdateDisputeRequest,
):
    dispute = disputes.get(dispute_id)
    if not dispute:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail= ErrorResponse(code="DISPUTE_NOT_FOUND", message="Dispute not found", details="Dispute not found").model_dump())
    return dispute

@app.get(
    "api/v1/health",
    response_model = dict,
    responses = {200: {"model": dict}, 500: {"model": ErrorResponse}},
)
async def health_check():
    return {"status": "ok"}
