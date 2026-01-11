package state.economicEvent;

import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicEventEconomicEventState extends EconomicEventState {
  //========== Constructor ===========
  public EconomicEventEconomicEventState() {
    super("economicEvent", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_EVendDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
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
  public void handle_EVendDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDependentView(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    //20230801: added the following 1 lines (i.e. 1 line of code + comment) according to Victor's instructions:
    //Manual Change (@Wim): increment counter when new dependent view created
    object.setCountDependentViews(object.getCountDependentViews()+1);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }

  //--- Ending Events ---
}
