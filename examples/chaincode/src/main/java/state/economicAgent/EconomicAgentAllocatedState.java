package state.economicAgent;

import entity.EconomicAgent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicAgentAllocatedState extends EconomicAgentState {
  //========== Constructor ===========
  public EconomicAgentAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrEconomicAgent(EconomicAgent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicAgent (object)
    EconomicAgentExistsState newState = new EconomicAgentExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
