"""
This example is a madeup example just for practice. If you find somewhere similar problem that is coincidence.

Any channel (app, chatbot, human agent etc) calls a central Decision API with customer & context, 
and it returns a ranked list of next-best-actions, combining rules + ML/LLM + experimentation, 
with low latency and high reliability

POST /v1/decisions/next-best-action

Latency: p95 < 150–200 ms for synchronous call.
Throughput: tens of thousands of RPS (peak during outages/billing cycles).
Availability: 99.9%+ (multi-region, stateless services).
Scalability: horizontal via K8s; stateless decision service.
Reliability: graceful degradation; no single point of failure.
Security: OAuth2/JWT, scopes like decision.read, decision.execute.
Observability: tracing across feature store, model service, rules engine; decision KPIs.
"""

from sys import version
from typing import List, Optional, Any, Dict
import uuid
from pydantic import BaseModel, Field
from fastapi import FastAPI, HTTPException, Header, Status
from uuid import uuid4
from datetime import datetime

app = FastAPI(tile="Next Best Action—NBA", version="0.0.1")

################################## DATA MODELS #########################################
class CustomerContext(BaseModel):
    customer_id: str = Field(..., min_length=3)
    segment: Optional[str] = None
    plan: Optional[str] = None
    tenure_months: Optional[int] = None
    hasRecentComplaint: Optional[bool] = None
    features: Dict[str, Any] = Field(default_factory=dict)

class DecisionRequest(BaseModel):
    channel: str = Field(..., regex="^(MOBILE_APP|CHATBOT|IVR|AGENT_DESKTOP)$")
    locale: str = "en-US"
    intentText: Optional[str] = None
    intentCode: Optional[str] = None
    customerContext: CustomerContext

class Action(BaseModel):
    actionType: str  # e.g. OFFER_CREDIT, COLLECT_INFO, ESCALATE_AGENT
    displayText: str
    priority: int
    confidence: float
    metadata: Dict[str, Any] = Field(default_factory=dict)

class DecisionResponse(BaseModel):
    requestId: str
    actions: List[Action]
    fallbackUsed: bool
    policyVersion: str
    generatedAt: datetime

########################################### DUMMY SERVICE LAYER ###################################

class FeatureStoreClient:
    async def get_features(self, ctx: CustomerContext) -> Dict[str, Any]:
        # in real time call live feature store

        return {
            "arpu": 65.0,
            "recentRoamingCharges": True,
            "churnRiskScore": 0.78,
        }

class RulesEngineClient:
    async def filter_actions(
        self, candidate_actions: List[Action], features: Dict[str, Any]
    ) -> List[Action]:
        # Apply eligibility rules (dummy example)
        filtered = []
        for a in candidate_actions:
            if a.actionType == "OFFER_CREDIT" and features.get("churnRiskScore", 0) < 0.3:
                continue
            filtered.append(a)
        return filtered

class ModelClient:
    async def rank_actions(
        self,
        req: DecisionRequest,
        features: Dict[str, Any]
    ) -> List[Action]:
        # In real life, call ML/LLM ranking model
        # Here we fake some actions
        base_actions = [
            Action(
                actionType="OFFER_CREDIT",
                displayText="Apologize and offer a $10 credit.",
                priority=1,
                confidence=0.9,
                metadata={"creditAmount": 10},
            ),
            Action(
                actionType="COLLECT_INFO",
                displayText="Ask the customer to confirm roaming dates.",
                priority=2,
                confidence=0.7,
                metadata={},
            ),
        ]
        # Sort by confidence as a simple "ranking"
        return sorted(base_actions, key=lambda a: a.confidence, reverse=True)


feature_store_client = FeatureStoreClient()
rules_client = RulesEngineClient()
model_client = ModelClient()

########################################### END POINTS #############################################
@app.post(
    "api/v1/decisions/nba", response_model = DecisionResponse
)
async def get_next_best_action(
    payload: DecisionRequest,
    x_request_id: Optional[str] = Header(default = None, convert_underscores = False)
):
    request_id = x_request_id or f"req-{uuid4()}"

    # 1) Fetch features (time-bounded in real life)
    features = await feature_store_client.get_features(payload.customerContext)
    
    fallback_used = False
    
    # 2) Call model to get candidate actions
    try:
        candidate_actions = await model_client.rank_actions(payload, features)
    except Exception:
        # Fallback: rules-only decision
        fallback_used = True
        candidate_actions = [
            Action(
                actionType="ESCALATE_AGENT",
                displayText="Route to human care agent.",
                priority=1,
                confidence=0.5,
                metadata={"reason": "MODEL_UNAVAILABLE"},
            )
        ]
    # 3) Rules engine filters / finalizes eligibility
    final_actions = await rules_client.filter_actions(candidate_actions, features)

    if not final_actions:
        final_actions = [
            Action(
                actionType="ESCALATE_AGENT",
                displayText="Route to human care agent.",
                priority=1,
                confidence=0.5,
                metadata={"reason": "NO_ELIGIBLE_ACTION"},
            )
        ]

    # 4) (Async) Log decision to event stream (omitted here)
    # await audit_logger.log_decision(request_id, payload, final_actions, fallback_used)

    return DecisionResponse(
        requestId=request_id,
        actions=final_actions,
        fallbackUsed=fallback_used,
        policyVersion="nba-policy-v1",
        generatedAt=datetime.utcnow(),
    )