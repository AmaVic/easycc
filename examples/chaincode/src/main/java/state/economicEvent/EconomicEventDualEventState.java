package state.economicEvent;

import entity.EconomicEvent;

import runtime.StubHelper;
import runtime.BusinessObject;
import runtime.exception.FailedEventHandlingException;
import runtime.exception.BusinessObjectNotFoundException;
import org.bouncycastle.util.encoders.Base64;


import org.hyperledger.fabric.contract.Context;

import java.util.function.Supplier;

public class EconomicEventDualEventState extends EconomicEventState {
  //========== Constructor ===========
  public EconomicEventDualEventState() {
    super("dualEvent", StateType.ONGOING);
  }

  //========= Event Handling =========
  //--- Creating Events ---
  //--- Modifying Events ---
  @Override
  public void handle_endLastDependenctView(EconomicEvent object, Context ctx) throws FailedEventHandlingException {
  	//20230801: added the following 3 lines (i.e. 2 lines of code + comment) according to Victor's instructions:
  	//Manual change (@Wim): added check
    if(object.getCountDependentViews() > 1)
      throw new FailedEventHandlingException("Wrong ending method used: this is not yet the last DepdendentView for this EconomicEvent");
    //Changes the state of the current EconomicEvent (object)
    EconomicEventEconomicEventState newState = new EconomicEventEconomicEventState();
    object.setCurrentState(newState);
    //20230801: added the following 1 lines (i.e. 1 line of code + comment) according to Victor's instructions:
    //Manual Change (@Wim): decrement counter when the last dependent view is removed 
    object.setCountDependentViews(object.getCountDependentViews()-1);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendParticipation(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDependentView(EconomicEvent object, Context ctx) throws FailedEventHandlingException {
  	//20230801: added the following 3 lines (i.e. 2 lines of code + comment) according to Victor's instructions:
  	//Manual Change (@Wim): added check
    if(object.getCountDependentViews() < 2)
      throw new FailedEventHandlingException("Wrong ending method used: this is the last dependent view for this economic event. Please use another ending method");
    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    //20230801: added the following 1 lines (i.e. 1 line of code + comment) according to Victor's instructions:
    //Manual Change (@Wim): decrement counter when the last dependent view is removed 
    object.setCountDependentViews(object.getCountDependentViews()-1);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrDuality(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVcrIncrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
    object.setCurrentState(newState);
    StubHelper.save(ctx, object);
  }
  @Override
  public void handle_EVendDecrement(EconomicEvent object, Context ctx) throws FailedEventHandlingException {

    //Changes the state of the current EconomicEvent (object)
    EconomicEventDualEventState newState = new EconomicEventDualEventState();
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

  //--- Ending Events ---
}
