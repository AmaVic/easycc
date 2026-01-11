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

public class DualityAllocatedState extends DualityState {
  //========== Constructor ===========
  public DualityAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrDuality(Duality object, Context ctx) throws FailedEventHandlingException {
    //Check if the referenced masters exist
    //EconomicEvent
    if(!StubHelper.exists(ctx, object.getEconomicEventId_Settle_Duality()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicEventId_Settle_Duality() + " does not exist");

    //Retrieve Master (economicEvent_Settle_Duality)
    EconomicEvent economicEvent_Settle_Duality = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Settle_Duality());

    //EconomicEvent
    if(!StubHelper.exists(ctx, object.getEconomicEventId_Claim_Duality()))
      throw new FailedEventHandlingException("Master with id " + object.getEconomicEventId_Claim_Duality() + " does not exist");

    //Retrieve Master (economicEvent_Claim_Duality)
    EconomicEvent economicEvent_Claim_Duality = (EconomicEvent)StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Claim_Duality());


    //Checking Multiple Propagation Constraints
    //Checking Multiple Propagation Constraint 426
    Supplier<BusinessObject> ultimateMPCObjectEnabledPath426Supplier = () -> {
      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Claim_Duality = null;
      try {
        mpc_economicEvent_Claim_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Claim_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }


      return mpc_economicEvent_Claim_Duality;
    };
 
    Supplier<BusinessObject> ultimateMPCObjectDisabledPath426Supplier = () -> {
      //Fetching EconomicEvent
      EconomicEvent mpc_economicEvent_Settle_Duality = null;
      try {
        mpc_economicEvent_Settle_Duality = (EconomicEvent) StubHelper.findBusinessObject(ctx, object.getEconomicEventId_Settle_Duality());
      } catch(BusinessObjectNotFoundException e) { 
        throw new FailedEventHandlingException("Could not check MPC:" + e); 
      }


      return mpc_economicEvent_Settle_Duality;
    };
 
    if(ultimateMPCObjectEnabledPath426Supplier.get().getId().equals(ultimateMPCObjectDisabledPath426Supplier.get().getId()))
      throw new FailedEventHandlingException("One of the multiple propagation constraints is not met");
    //Changes the state of the current Duality (object)
    DualityImageState newState = new DualityImageState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
    //Trigger Event Handling for Each Master
    //EconomicEvent
    economicEvent_Settle_Duality.getCurrentState().handle_EVcrDuality(economicEvent_Settle_Duality, ctx);

    //EconomicEvent
    economicEvent_Claim_Duality.getCurrentState().handle_EVcrDuality(economicEvent_Claim_Duality, ctx);

  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
