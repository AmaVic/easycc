package state.duality;

import entity.Duality;
import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class DualityExchangeState extends DualityState {
  //========== Constructor ===========
  public DualityExchangeState() {
    super("exchange", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDependentView(Duality object, Context ctx) throws FailedEventHandlingException {

    //The Duality object will be saved with its current properties
    //Before changing state and (possibly) attributes, we check there was no change to the masters
    Duality currentLedgerObject = (Duality)StubHelper.findBusinessObject(ctx, object.getId());
    if(!object.getEconomicEventId_Settle_Duality().equals(currentLedgerObject.getEconomicEventId_Settle_Duality()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    if(!object.getEconomicEventId_Claim_Duality().equals(currentLedgerObject.getEconomicEventId_Claim_Duality()))
      throw new FailedEventHandlingException("The master of a Business Object (" + object.getId() + ") cannot be changed");
    //Changes the state of the current Duality (object)
    DualityConversionState newState = new DualityConversionState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    EconomicEvent economicEvent_Settle_Duality = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Settle_Duality());
    economicEvent_Settle_Duality.getCurrentState().handle_EVendDependentView(economicEvent_Settle_Duality, ctx);
    //EconomicEvent
    EconomicEvent economicEvent_Claim_Duality = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Claim_Duality());
    economicEvent_Claim_Duality.getCurrentState().handle_EVendDependentView(economicEvent_Claim_Duality, ctx);
  }

  //--- Ending Events ---
}
