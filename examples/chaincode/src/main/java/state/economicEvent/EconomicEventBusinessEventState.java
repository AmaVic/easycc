package state.economicEvent;

import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicEventBusinessEventState extends EconomicEventState {
  //========== Constructor ===========
  public EconomicEventBusinessEventState() {
    super("businessEvent", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVcrStockFlow(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendStockFlow(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventBusinessEventState newState = new EconomicEventBusinessEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }

  //--- Ending Events ---
  @Override
  public void handle_EVendEconomicEvent(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Check if there are living  dependents before ending the object
    if(StubHelper.hasLivingDependents(ctx, object))
      throw new FailedEventHandlingException("EconomicEvent (" + object.getId() + ") has living dependents and therefore cannot be ended");

    //Changes the state of the current EconomicEvent (object)
    EconomicEventArchivedState newState = new EconomicEventArchivedState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
}
