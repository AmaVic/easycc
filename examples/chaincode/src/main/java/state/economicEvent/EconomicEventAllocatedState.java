package state.economicEvent;

import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicEventAllocatedState extends EconomicEventState {
  //========== Constructor ===========
  public EconomicEventAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrEconomicEvent(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
