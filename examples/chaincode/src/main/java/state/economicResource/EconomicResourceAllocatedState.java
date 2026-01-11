package state.economicResource;

import entity.EconomicResource;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicResourceAllocatedState extends EconomicResourceState {
  //========== Constructor ===========
  public EconomicResourceAllocatedState() {
    super("allocated", StateType.INITIAL);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  @Override
  public void handle_EVcrEconomicResource(EconomicResource object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicResource (object)
    EconomicResourceExistsState newState = new EconomicResourceExistsState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  //--- Modifying Events ---

  //--- Ending Events ---
}
